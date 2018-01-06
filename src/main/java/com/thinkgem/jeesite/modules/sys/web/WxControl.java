package com.thinkgem.jeesite.modules.sys.web;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.WxGlobal;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.SysExpress;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.SysWxUser;
import com.thinkgem.jeesite.modules.sys.entity.SysWxUserCheck;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.manager.WxAccessTokenManager;
import com.thinkgem.jeesite.modules.sys.manager.WxMenuManager;
import com.thinkgem.jeesite.modules.sys.service.SysExpressService;
import com.thinkgem.jeesite.modules.sys.service.SysWxUserService;
import com.thinkgem.jeesite.modules.sys.service.WxService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "wx")
public class WxControl extends BaseController {
	
	@Autowired
	private SysWxUserService sysWxUserService;
	
	@Autowired
	private WxMenuManager wxMenuManager;
	
	@Autowired
	private WxService wxService;
	
	@Autowired
	private SysExpressService sysExpressService;
	
	//首页
	private final String WX_PERSON_INDEX = "modules/wxp/wxPersonIndex";
	//个人注册页面
	private final String WX_ID_CARD_USERINFO_ADD = "modules/wxp/wxIdCardUserInfoAdd";
	//修改个人信息
	private final String WX_ID_CARD_USERINFO_MODIFY = "modules/wxp/wxIdCardUserInfoModify";
	//个人中心页面
	private final String WX_USER_HOME = "modules/wxp/wxUserhome";
	//快递助手页面
	private final String WX_EXPRESS_ASSIST = "modules/wxp/expressAssist";
	//录入快递页面
	private final String WX_EXPRESS_ADD = "modules/wxp/addExpress";
	//取快递页面
	private final String WX_EXPRESS_PICK = "modules/wxp/pickExpress";
	//验证手机号页面
	private final String WX_PHONE_MODIFY = "modules/wxp/wxPhoneModify";
	//审核页面
	private final String WX_WAIT_VALIDATE = "modules/wxp/waitValidate";
	//错误页面
	private final String WX_ERROR = "modules/wxp/500";

	/**
	 * 错误信息
	 */
	private final String ERR_OPEN_ID_NOT_GET = "微信号未获取";
	private final String ERR_ID_CARD_NULL = "身份证号不能为空";
	private final String ERR_PHONE_NULL = "电话不能为空";
	private final String ERR_SAME_IDCARD_PHONE_CHECK = "该电话号码和身份证已经注册，请更换其它身份证和电话号码，如被他人绑定，请前往快递点联系快递人员处理";
	private final String ERR_NO_WXUER = "系统异常，不存在操作用户，请前往快递点联系快递人员处理";
	private final String ERR_PHONE_ORIGIN = "请输入原先的电话号码进行验证";

	
	/**
	 * 依据微信号查询是否已经注册并且激活
	 * 用户是新用户，需要跳转到注册页面
	 * 用户已经注册，需要查看是否激活，没有激活跳转到审核页面，激活后跳转到对应页面
	 * @param openId 查询微信号
	 * @return
	 * 如果微信号查询不到，返回注册页面
	 * 如果微信号查询到，但是没有激活，返回未审核页面
	 * 如果用户已注册，也已经激活，返回空值
	 */
	private String validateRegAndActiveByOpenId(String openId) {
		SysWxUserCheck sysWxUserCheck = wxService.findByOpenId(openId);
		if(null == sysWxUserCheck) {
			return WX_ID_CARD_USERINFO_ADD;//用户不存在,返回注册页面
		}
		String state = sysWxUserCheck.getState();
		if("0".equals(state)) {
			return WX_WAIT_VALIDATE;//用户已注册，但未激活，返回审核等待状态
		}
		return null;//用户已注册，也已经激活，返回空值
	}
	
	/**
	 * 验证微信号是否获取，如果微信号未获取，系统后续操作无法进行，提示微信号未获取
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 如果微信号未获取，返回错误页面
	 * 如果微信号获取，返回空值
	 */
	private String validateOpenId(String openId,Model model) {
		if(null == openId) {
			model.addAttribute("message", ERR_OPEN_ID_NOT_GET);
			return WX_ERROR;//返回错误页面
		}
		return null;//微信号 标识微信号已获取
	}
	
	
	/**
	 * 页面跳转
	 * @param request
	 * @param model
	 * @param url 想要跳转的页面
	 * @return
	 */
	private String getRedirectUrl(HttpServletRequest request,Model model,String url) {
		String openId = request.getParameter("openId");//获取微信号
		//是否获取到微信号
		String isGetOpenId = validateOpenId(openId,model);
		if(null!=isGetOpenId) {
			//没有获取到，跳转到错误页面
			return isGetOpenId;
		}
		//是否已经注册并且激活
		String isRegAndActive = validateRegAndActiveByOpenId(openId);
		if(null!=isRegAndActive) {
			//未注册或者未激活 跳转到指定页面
			return isRegAndActive;
		}
	    logger.info("openId is " + openId);
	    model.addAttribute("openId",openId);
		return url;
	}
	
	
	/**
	 * 页面跳转-获取快递助手页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/reqExpressAssist",method=RequestMethod.GET)
	public String reqExpressAssist(HttpServletRequest request, HttpServletResponse response,Model model) {
		return getRedirectUrl(request,model,WX_EXPRESS_ASSIST);
	}
	
	/**
	 * 页面跳转-获取快递添加页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/reqAddExpress",method=RequestMethod.GET)
	public String reqAddExpress(HttpServletRequest request, HttpServletResponse response,Model model) {
		return getRedirectUrl(request,model,WX_EXPRESS_ADD);
	}
	
	/**
	 * 页面跳转-获取取快递页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/reqPickExpress",method=RequestMethod.GET)
	public String reqPickExpress(HttpServletRequest request, HttpServletResponse response,Model model) {
		return getRedirectUrl(request,model,WX_EXPRESS_PICK);
	}
		
	/**
	 * 获取个人首页
	 * 微信点击个人首页后，微信重定向访问地址
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getPersonIndex",method=RequestMethod.GET)
	public String getPersonIndex(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
	        request.setCharacterEncoding("UTF-8");  
	        response.setCharacterEncoding("UTF-8"); 
	        String code=request.getParameter("code");//获取code
	        logger.info("code is " + code);
	        /*Map<String,String> map = wxService.getOpenIdInfo(code);
	        if(null != map) {
	        	String openId = map.get("openid");
	        	model.addAttribute("openId", openId);
	        }*/
	        //本地测试时使用，实体环境删除 将上一句注释的话显示
	        model.addAttribute("openId",WxGlobal.TEST_OPEN_ID);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return WX_PERSON_INDEX;
	}
	
	
	/**
	 * 页面跳转-获取个人中心页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/userHome",method=RequestMethod.GET)
	public String userHome(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		
		String openId = request.getParameter("openId");//获取微信号
		
		//验证微信号、是否注册、是否激活
		String isSendUrl = getRedirectUrl(request,model,null);
		
		//验证未通过
		if(null != isSendUrl) {
			return isSendUrl;
		}
		 
		//依据openId获取个人信息 如果没有个人信息 那么跳转到个人信息注册页面 如果有跳转到个人信息userHome页面
		SysWxUser sysWxUser = wxService.getSysWxUser(openId);
		//判断
		if(null == sysWxUser) {
			//没有个人信息
			model.addAttribute("openId",openId);
			return WX_ID_CARD_USERINFO_ADD;
		}else {
			//已注册个人信息
			model.addAttribute("openId",openId);
			model.addAttribute("sysWxUser",sysWxUser);
			return WX_USER_HOME;
		}
	}
	
	/**
	 * 注册个人信息
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/savePersonUserInfo",method=RequestMethod.POST)
	public String savePersonUserInfo(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String name = request.getParameter("name");
		String idCard = request.getParameter("idCard");
		String phone = request.getParameter("phone");
		String openId = request.getParameter("openId");

		//身份证不能为空
		if(null == idCard) {
			model.addAttribute("message", ERR_ID_CARD_NULL);
			model.addAttribute("idCard", idCard);
			model.addAttribute("phone", phone);
			model.addAttribute("openId", openId);
			return WX_ID_CARD_USERINFO_ADD;
		}
		
		//电话号不能为空
		if(null == phone) {
			model.addAttribute("message", ERR_PHONE_NULL);
			model.addAttribute("idCard", idCard);
			model.addAttribute("phone", phone);
			model.addAttribute("openId", openId);
			return WX_ID_CARD_USERINFO_ADD;
		}
		
		SysWxUser sysWxUser = null;
		//是否已经注册（电话和身份证） 如果依据注册过，身份证和电话相同 那么直接获取
		sysWxUser = wxService.findWxUser(idCard, phone);
		if(null!=sysWxUser) {
			//填写信息
			sysWxUser.setName(name);
			//因为是注册页面 说明用户使用了新的微信，那么更新微信绑定和用户信息
			wxService.saveWxUserInfo(sysWxUser,openId);
			model.addAttribute("openId",openId);
			model.addAttribute("sysWxUser",sysWxUser);
			return WX_USER_HOME;
		}
		
		
		//如果该身份证已经注册，需要提示用户输入之前注册的手机号以进行验证
		sysWxUser = wxService.findByIdCard(idCard);
		if(null != sysWxUser) {
			String originPhone = sysWxUser.getPhone();//原先电话
			if(!originPhone.equals(phone)) {
				model.addAttribute("message", ERR_PHONE_NULL);
				model.addAttribute("idCard", idCard);
				model.addAttribute("phone", phone);
				model.addAttribute("openId", openId);
				return ERR_PHONE_ORIGIN;
			}
		}
		
	
		
		//注册信息填写
		SysWxUserCheck param = new SysWxUserCheck();
		param.setName(name);
		param.setIdCard(idCard);
		param.setPhone(phone);
		param.setOpenId(openId);
		param.setState(DictUtils.getDictValue("未激活", "userCheckState", "0"));

		//如果身份信息不存在 进行保存操作
		SysWxUserCheck result = wxService.saveSysWxUserCheck(param);
		if(null == result) {
			model.addAttribute("message", ERR_NO_WXUER);
			model.addAttribute("idCard", idCard);
			model.addAttribute("phone", phone);
			model.addAttribute("openId", openId);
			return WX_ID_CARD_USERINFO_ADD;
		}
		model.addAttribute("sysWxUser", result);
		model.addAttribute("openId", openId);
		return WX_WAIT_VALIDATE;
	}
	
	//录入快递信息
	@RequestMapping(value="/saveExpress",method=RequestMethod.POST)
	public String saveExpress(HttpServletRequest request, HttpServletResponse response,Model model) {
		String phone=request.getParameter("phone");//获取phone
		String expressId=request.getParameter("expressId");//获取phone
		String openId=request.getParameter("openId");//获取phone
		if(null == phone) {
			model.addAttribute("openId",openId);
			model.addAttribute("message", "请输入电话号码");
			return WX_EXPRESS_ADD;
		} 
		if(null == expressId) {
			model.addAttribute("openId",openId);
			model.addAttribute("message", "请输入快递单号");
			return WX_EXPRESS_ADD;
		}
		
		//查询操作人员
		User user = wxService.findOperator(openId);
		if(null == user) {
			model.addAttribute("openId",openId);
			model.addAttribute("message", "无操作权限");
			return WX_EXPRESS_ADD;
		}
		
		
		SysExpress sysExpress = new SysExpress();
		//默认保存快递状态为已入库
		String state = DictUtils.getDictValue("已入库", "expressState", "0");
		sysExpress.setState(state);
		sysExpress.setExpressId(expressId);
		sysExpress.setPhone(phone);
		sysExpressService.saveExpress(sysExpress,user);
		model.addAttribute("openId",openId);
		return WX_PERSON_INDEX;
	}
	
	//获取页面(测试时使用，运行时可删除)
	@RequestMapping(value="/clickUrl",method=RequestMethod.GET)
	@ResponseBody
	public String clickUrl(HttpServletRequest request, HttpServletResponse response) {
		String urlParam = request.getParameter("url");
		String url = null;
		String jsonResult = null;
		switch(urlParam) {
			case "index":
				//首页
				url = WxGlobal.getUserClick("https://x.xlhtszgh.cn/kd/wx/oAuthRedirectSo",true);
				break;
			case "tiePerson":
				//绑定个人中心
				url = WxGlobal.getUserClick("https://x.xlhtszgh.cn/kd/wx/oAuthRedirectSo",false);
				break;
		}
		if(null != url) {
			jsonResult = JSONObject.toJSONString(url);
			logger.info("url是:"+url);
		}else {
			logger.info("url不存在");
		}
		return jsonResult;
	}
	
	
	
	
	//验证手机号
	@RequestMapping(value="/checkPhone",method=RequestMethod.POST)
	public String checkPhone(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String phone = request.getParameter("phone");
		String idCard = request.getParameter("idCard");
		String openId = request.getParameter("openId");
		
		//身份证不能为空
		if(null == idCard) {
			model.addAttribute("message", "身份证号不能为空");
			model.addAttribute("idCard", idCard);
			model.addAttribute("phone", phone);
			return WX_PERSON_INDEX;
		}
		
		//电话号不能为空
		if(null == phone) {
			model.addAttribute("message", "电话不能为空");
			model.addAttribute("idCard", idCard);
			model.addAttribute("phone", phone);
			return WX_PERSON_INDEX;
		}
		
		SysWxUser repeatUser = wxService.findByIdCard(idCard);
		if(null == repeatUser) {
			//没有该用户
			model.addAttribute("idCard", idCard);
			model.addAttribute("phone", phone);
			return WX_PERSON_INDEX;
		}else {
			String originPhone = repeatUser.getPhone();
			if(phone.equals(originPhone)) {
				//验证成功 修改数据
				repeatUser.setPhone(phone);
				model.addAttribute("sysWxUser", repeatUser);
				model.addAttribute("openId", openId);
				wxService.modifyWxUserInfo(repeatUser,openId);
				model.addAttribute("message", "添加成功!");
				return WX_ID_CARD_USERINFO_MODIFY;
			}else {
				model.addAttribute("idCard", idCard);
				model.addAttribute("phone", phone);
				model.addAttribute("message", "与原号码不匹配!,请重新添加");
				return WX_ID_CARD_USERINFO_ADD;
			}
		}
	}
	
	//修改个人信息
	@RequestMapping(value="/modifyPersonUserInfo",method=RequestMethod.POST)
	public String modifyPersonUserInfo(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String openId = request.getParameter("openId");
		String id = request.getParameter("id");
		
		//电话号不能为空
		if(null == phone) {
			model.addAttribute("message", "电话不能为空");
			model.addAttribute("openId", openId);
			model.addAttribute("phone", phone);
			return WX_ID_CARD_USERINFO_MODIFY;
		}
		
		//电话号码重复查询
		if(null != wxService.findByPhone(phone)) {
			model.addAttribute("message", "该电话号已经注册，请更换其它电话号，如被他人绑定，请前往快递点联系快递人员");
			model.addAttribute("phone", phone);
			model.addAttribute("openId", openId);
			return WX_ID_CARD_USERINFO_ADD;
		}
		
		
		SysWxUser sysWxUser = wxService.getSysWxUser(openId);
		sysWxUser.setId(id);
		sysWxUser.setName(name);
		sysWxUser.setIdCard(idCard);
		sysWxUser.setPhone(phone);
		wxService.modifyWxUserInfo(sysWxUser,openId);
		model.addAttribute("sysWxUser", sysWxUserService.getByIdCard(idCard));
		model.addAttribute("openId", openId);
		return WX_USER_HOME;
	}

	
	/**
	 * 授权回调
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/oAuthRedirectSo",method=RequestMethod.GET)
	public String oAuthRedirectSo(HttpServletRequest request, HttpServletResponse response,Model model) {
		String retPage = null;
		try {
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
	        request.setCharacterEncoding("UTF-8");  
	        response.setCharacterEncoding("UTF-8"); 
	        String code=request.getParameter("code");//获取code
	        logger.info("code is " + code);
	        
	        SysWxInfo sysWxInfo = new SysWxInfo();
	        sysWxInfo = wxService.saveOpenId(code);
	        if(null == sysWxInfo) {
	        	retPage = "/error/500.jsp";
	        	return retPage;
	        }else {
	        	String idCard = sysWxInfo.getIdCard();
	        	if(null == idCard) {
	        		//没有身份信息
	        		model.addAttribute("sysWxInfo", sysWxInfo);
	        		model.addAttribute("openId", sysWxInfo.getOpenId());
	        		retPage = WX_ID_CARD_USERINFO_ADD;
		        	return retPage;
	        	}else {
	        		//有身份信息
	        		model.addAttribute("openId",sysWxInfo.getOpenId());
	        		model.addAttribute("sysWxUser", sysWxUserService.getByIdCard(idCard));
	        		retPage = WX_ID_CARD_USERINFO_MODIFY;
		        	return retPage;
	        	}
	        }
		}catch(Exception ex) {
			 logger.info("request getUserAccessToken error");
			 ex.printStackTrace();
		}
		return retPage;
	}
	
	/**
	 * 微信接口测试-创建菜单
	 * @param model
	 * @return
	 */
	//创建菜单
	@RequestMapping(value = {"createMenu"})
	public String wxCreateMenu(Model model) {
		wxMenuManager.createMenu();
		return null;
	}
	
	/**
	 * 生成access_token
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"getAt"})
	public String wxAccessToken(Model model) {
		WxAccessTokenManager wtUtils = WxAccessTokenManager.getInstance();
		wtUtils.getAccessToken();
		return null;
	}
	
	/**
	 * 接收微信消息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/doGet",method=RequestMethod.POST)
	public void wxAcceptMsg(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("微信消息进入");
	    try {
        	// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        	request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            //解析微信返回的xml  
            String retMsg = wxService.WxPostMsgProcess(request);//返回消息
            PrintWriter out = response.getWriter();
            out.print(retMsg);
            out.close();
        }catch(Exception ex) {
        	System.out.println("接收微信消息出错");
        	ex.printStackTrace();
        }
	    System.out.println("微信消息处理完成");
	}
	
	/**
	 * 认证微信服务器
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/doGet",method=RequestMethod.GET)
	public void wxServerCertification(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("开始签名校验");
		try {
			//提取参数			
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			 
			ArrayList<String> array = new ArrayList<String>();
			array.add(signature);
			array.add(timestamp);
			array.add(nonce);
			//排序
			String sortString = wxService.sort(timestamp, nonce);
			//加密
			String mytoken = wxService.sha1(sortString);
			//比对
			//校验签名
		    if (mytoken != null && mytoken != "" && mytoken.equals(signature)) {
		        System.out.println("签名校验通过。");
		        response.getWriter().println(echostr); //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
		    } else {
		        System.out.println("签名校验失败。");
		    }
		}catch(Exception ex) {
			System.out.println("签名校验失败，出现异常。");
			ex.printStackTrace();
		}
		System.out.println("签名校验结束");
	}
	
	
	
}
