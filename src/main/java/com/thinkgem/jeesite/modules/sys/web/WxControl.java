package com.thinkgem.jeesite.modules.sys.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
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
	
	@RequestMapping(value = {"index"})
	public String index(Model model) {
		System.out.println("Test net is success");
		return null;
	}
	
	/**
	 * 微信接口测试
	 * @param model
	 * @return
	 */
	//创建菜单
	@RequestMapping(value = {"createMenu"})
	public String wxCreateMenu(Model model) {
		wxMenuManager.createMenu();
		return null;
	}
	
	//生成access_token
	@RequestMapping(value = {"getAt"})
	public String wxAccessToken(Model model) {
		WxAccessTokenManager wtUtils = WxAccessTokenManager.getInstance();
		wtUtils.getAccessToken();
		return null;
	}
	
	
	/**
	 * 页面跳转
	 * @param model
	 * @return
	 */
	//获取保存个人信息页面
	@RequestMapping(value="/reqPersonUserInfo",method=RequestMethod.GET)
	public String reqPersonUserInfo(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = request.getParameter("openId");//获取code
	    logger.info("openId is " + openId);
	    if(null!=openId) {
	    	model.addAttribute("openId",openId);
	    }
		return WX_ID_CARD_USERINFO_ADD;
	}
	
	/**
	 * 获取快递助手页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/reqExpressAssist",method=RequestMethod.GET)
	public String reqExpressAssist(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = request.getParameter("openId");//获取code
	    logger.info("openId is " + openId);
	    if(null!=openId) {
	    	model.addAttribute("openId",openId);
	    }
		return WX_EXPRESS_ASSIST;
	}
	
	/**
	 * 获取快递添加页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/reqAddExpress",method=RequestMethod.GET)
	public String reqAddExpress(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = request.getParameter("openId");//获取code
	    logger.info("openId is " + openId);
	    if(null!=openId) {
	    	model.addAttribute("openId",openId);
	    }
		return WX_EXPRESS_ADD;
	}
		
	//获取个人首页
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
		sysExpressService.saveExpress(sysExpress,user);
		model.addAttribute("openId",openId);
		return WX_PERSON_INDEX;
	}
	//进入个人中心
	@RequestMapping(value="/userHome",method=RequestMethod.GET)
	public String userHome(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		 String openId=request.getParameter("openId");//获取code
		 if(null == openId) {
			 throw new Exception("未关联个人标识符");
		 }
		 /**
		  * 依据openId获取个人信息 如果没有跟人信息 那么跳转到个人信息注册页面 如果有跳转到个人信息userHome页面
		  */
		 SysWxUser sysWxUser = wxService.getSysWxUser(openId);
		 //获取
		 if(null == sysWxUser) {
			 model.addAttribute("openId",openId);
			 return WX_ID_CARD_USERINFO_ADD;
		 }else {
			 model.addAttribute("openId",openId);
			 model.addAttribute("sysWxUser",sysWxUser);
			 return WX_USER_HOME;
		 }
	}
	
	//进入个人中心修改页面
	@RequestMapping(value="/wxIdCardModify",method=RequestMethod.GET)
	public String wxIdCardModify(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		 String openId=request.getParameter("openId");//获取code
		 if(null == openId) {
			 throw new Exception("未关联个人标识符");
		 }
		 /**
		  * 依据openId获取个人信息 如果没有跟人信息 那么跳转到个人信息注册页面 如果有跳转到个人信息WX_ID_CARD_USERINFO_MODIFY页面
		  */
		 SysWxUser sysWxUser = wxService.getSysWxUser(openId);
		 //获取
		 if(null == sysWxUser) {
			 model.addAttribute("openId",openId);
			 return WX_ID_CARD_USERINFO_ADD;
		 }else {
			 model.addAttribute("sysWxUser",sysWxUser);
			 model.addAttribute("openId",openId);
			 return WX_ID_CARD_USERINFO_MODIFY;
		 }
	}
	
	//获取页面
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
	
	
	//保存个人信息
	@RequestMapping(value="/savePersonUserInfo",method=RequestMethod.POST)
	public String savePersonUserInfo(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String name = request.getParameter("name");
		String idCard = request.getParameter("idCard");
		String phone = request.getParameter("phone");
		String openId = request.getParameter("openId");
		SysWxUser param = new SysWxUser();
		param.setName(name);
		param.setIdCard(idCard);
		param.setPhone(phone);
		
		//如果绑定身份信息依据存在,需要用户重新确定之前绑定的手机号是否正确 跳转到手机号确认页面
		SysWxUser repeatUser = wxService.findByIdCard(idCard);
		if(null!=repeatUser) {
			model.addAttribute("openId", openId);
			model.addAttribute("sysWxUser", repeatUser);
			model.addAttribute("message", "有重复数据");
			return "modules/wxp/wxPhoneModify";//验证手机号页面
		}
		//如果身份信息不存在 进行保存操作
		wxService.saveWxUserInfo(param,openId);
		model.addAttribute("sysWxUser", sysWxUserService.getByIdCard(idCard));
		model.addAttribute("openId", openId);
		return WX_ID_CARD_USERINFO_MODIFY;
	}
	
	//验证手机号
	@RequestMapping(value="/checkPhone",method=RequestMethod.POST)
	public String checkPhone(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String phone = request.getParameter("phone");
		String idCard = request.getParameter("idCard");
		String openId = request.getParameter("openId");
		SysWxUser repeatUser = wxService.findByIdCard(idCard);
		if(null == repeatUser) {
			//没有该用户
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
				model.addAttribute("message", "与原号码不匹配!,请重新添加");
				return WX_ID_CARD_USERINFO_ADD;
			}
		}
	}
	
	//修改个人信息
	@RequestMapping(value="/modifyPersonUserInfo",method=RequestMethod.POST)
	public String modifyPersonUserInfo(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String name = request.getParameter("name");
		String idCard = request.getParameter("idCard");
		String phone = request.getParameter("phone");
		String openId = request.getParameter("openId");
		String id = request.getParameter("id");
		SysWxUser sysWxUser = new SysWxUser();
		sysWxUser.setId(id);
		sysWxUser.setName(name);
		sysWxUser.setIdCard(idCard);
		sysWxUser.setPhone(phone);
		wxService.modifyWxUserInfo(sysWxUser,openId);
		model.addAttribute("sysWxUser", sysWxUserService.getByIdCard(idCard));
		model.addAttribute("openId", openId);
		return WX_USER_HOME;
	}
	/*
	 * 授权回调
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
