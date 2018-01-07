package com.thinkgem.jeesite.modules.sys.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.config.WxGlobal;
import com.thinkgem.jeesite.common.entity.PhoneMsgCache;
import com.thinkgem.jeesite.common.utils.AliyunSendMsgUtils;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.IdcardUtils;
import com.thinkgem.jeesite.common.utils.PhoneUtils;
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
	
	//首页
	private final String WX_PERSON_INDEX = "modules/wxp/wxPersonIndex";
	//认证页面
	private final String WX_USER_CHECK_START = "modules/wxp/userCheckStart";
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
	private final String ERR_EXPREE_ID_NULL = "快递单号不能为空";
	private final String ERR_NAME_NULL = "姓名不能为空";
	private final String ERR_CODE_NULL = "验证码不能为空";
	private final String ERR_CODE_SIZE = "验证码长度错误";
	private final String ERR_CODE = "请获取验证码";
	private final String ERR_CODE_TIME_OUT = "验证码已超时";
	private final String ERR_CODE_INPUT = "验证码错误";
	private final String ERR_PHONE_NULL = "电话不能为空";
	private final String ERR_SAME_IDCARD_PHONE_CHECK = "该电话号码和身份证已经注册，请更换其它身份证和电话号码，如被他人绑定，请前往快递点联系快递人员处理";
	private final String ERR_NO_WXUER = "系统异常，不存在操作用户，请前往快递点联系快递人员处理";
	private final String ERR_PHONE_ORIGIN = "请输入原先的电话号码进行验证";
	private final String ERR_PHONE_PATTERN = "手机号码格式不正确";
	private final String ERR_ID_CARD_PATTERN = "身份证号码格式不正确";
	private final String ERR_SAME_PHONE_NO_ACTIVE = "该号码已经注册，未审核激活，请携带身份证前往快递中心激活";
	private final String ERR_SAME_PHONE = "该号码已经注册，请绑定其它号码";
	private final String ERR_PROMPT_USER_CONFIRE = "您好，短信已发送，如果您没有收到，可能是网络有延迟，请耐心等待片刻";
	private final String ERR_TOO_MONEY = "今日次数已达上限";
	private final String ERR_NO_ACTIVE = "用户未审核激活，请前往快递中心审核激活";
	private final String ERR_INPUT_OLD_PHONE = "用户已绑定，请输入原手机号码";
	private final String ERR_NOT_SAME_OLD_PHONE = "绑定原手机号码输入错误";
	private final String ERR_NO_USER = "未检测到操作用户";
	private final String ERR_OLD_PHONE_PATTERN = "旧手机号码格式不正确";
	private final String ERR_NEW_PHONE_PATTERN = "新手机号码格式不正确";
	private final String ERR_EXPREE_NOT_ID_CARD = "未查询到快递信息";

	
	private final String MSG_PHONE_CODE_MSG = "验证码发送成功";
	private final String MSG_USER_SAVE_SUCCESS = "用户注册成功";
	private final String MSG_EXPRESS_SAVE_SUCCESS = "快递录入成功";
	private final String MSG_EXPRESS_QUERY_SUCCESS = "查找成功";
	
	/**
	 * 测试页面（上线可删除）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response,Model model) {
		 AliyunSendMsgUtils.sendMsg("15904793789", "1234");
		 return null;
	}
	
	
	
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
			return WX_USER_CHECK_START;//用户不存在,返回注册页面
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
	    logger.info("openId is " + openId);
	    model.addAttribute("openId",openId);
		//是否已经注册并且激活
		String isRegAndActive = validateRegAndActiveByOpenId(openId);
		if(null!=isRegAndActive) {
			//未注册或者未激活 跳转到指定页面
			return isRegAndActive;
		}
		return url;
	}
	
	/**
	 * 页面跳转-完善个人信息页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/reqUserInfoEdit",method=RequestMethod.GET)
	public String reqUserInfoEdit(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		String isReirectUrl = getRedirectUrl(request,model,null);//验证是否跳转
		
		//验证出现错误 进行跳转
		if(null!=isReirectUrl) {
			return isReirectUrl;//跳转
		}
		//获取个人信息
		String openId = request.getParameter("openId");//获取微信号
		SysWxUser sysWxUser = wxService.findSysUserByOpenId(openId);
		if(null != sysWxUser) {
			model.addAttribute("sysWxUser",sysWxUser);
		}
		model.addAttribute("openId",openId);
		return WX_ID_CARD_USERINFO_MODIFY;
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
	 * 发送微信验证码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/sendWxPhoneMsgCode",method=RequestMethod.POST)
	@ResponseBody
	public String sendWxPhoneMsgCode(HttpServletRequest request, HttpServletResponse response,Model model) {
		String phoneNumber = request.getParameter("phone").trim();
		String successCode = "0";
		String errorCode = "1";
		String promotCode = "2";//提示用户耐心等待
		//手机号不能为空
		if(StringUtils.isEmpty(phoneNumber)) {
			return backJsonWithCode(errorCode,ERR_PHONE_NULL);
		}
		
		//手机号码格式不正确
		if(!PhoneUtils.validatePhone(phoneNumber)) {
			return backJsonWithCode(errorCode,ERR_PHONE_PATTERN);
		}
		
		//用户已经注册（未激活）
		if(null!=wxService.findUserCheckByPhone(phoneNumber)) {
			return backJsonWithCode(errorCode,ERR_SAME_PHONE_NO_ACTIVE);
		}
		
		//用户已经注册
		if(null!=wxService.findByPhone(phoneNumber)) {
			return backJsonWithCode(errorCode,ERR_SAME_PHONE);
		}
		
		//手机号码前缀 moblie_123456789
		String cacheKey = Global.PREFIX_MOBLIE_CODE + phoneNumber;
		
		PhoneMsgCache phoneMsgCache = (PhoneMsgCache)CacheUtils.get(cacheKey);
		
		//已经发送过验证码
		if(null != phoneMsgCache) {
			
			//如果注册时间和当前不是同一天 注册次数恢复为初始值
			if(!CasUtils.isSameDay(phoneMsgCache.getRegTime(), System.currentTimeMillis())) {
				phoneMsgCache.resetSendTime();
			}
			
			//用户可能没收到短信，会点击多次，需要限制用户点击次数，不能超过规定次数
			
			//是否提示用户，如果没有，需要提示用户耐心等待
			if(!phoneMsgCache.isPrompt()) {
				//提示
				phoneMsgCache.setPrompt(true);//记录已经提示过了
				return backJsonWithCode(promotCode,ERR_PROMPT_USER_CONFIRE);
				
				
				
			}
			
			//提示过后仍然点击获取验证码
			//验证注册次数
			int sendTimes = phoneMsgCache.getSendTimes();
			if(sendTimes > Global.MOBILE_TIMES) {
				return backJsonWithCode(errorCode,ERR_TOO_MONEY);
			}
			
			String code = CasUtils.getRandomDigitalString(4);//验证码
			logger.info("验证码是:"+code);
			phoneMsgCache.setValue(code);
			phoneMsgCache.setSendTimes(++sendTimes);
			//注册时间
			phoneMsgCache.setRegTime(System.currentTimeMillis());
			//过期时间30分钟
			phoneMsgCache.setTimeOut(System.currentTimeMillis()+1000*60*30);
			return backJsonWithCode(successCode,MSG_PHONE_CODE_MSG);
		}else {
			//没发送过验证码，发送验证码
			String code = CasUtils.getRandomString(Global.MOBILE_CODE_SIZE);//验证码
			logger.info("验证码是:"+code);
			phoneMsgCache = new PhoneMsgCache(code);
			CacheUtils.put(cacheKey, phoneMsgCache);//缓存
			CacheUtils.putPhoneMsgCacheKey(cacheKey);//添加缓存key 方便之后移除
			//发送验证码
			//AliyunSendMsgUtils.sendMsg(phoneNumber, code);//测试环境先注释
			return backJsonWithCode(successCode,MSG_PHONE_CODE_MSG);
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
	@ResponseBody
	@RequestMapping(value="/savePersonUserInfo",method=RequestMethod.POST)
	public String savePersonUserInfo(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String name = request.getParameter("name").trim();
		String idCard = request.getParameter("idCard").trim();
		String phone = request.getParameter("phone").trim();
		String msg = request.getParameter("msg").trim();
		String openId = request.getParameter("openId").trim();
		String oldPhone = request.getParameter("oldPhone").trim();//老手机号
		
		final String successCode = "0";//成功码
		final String errCode_1 = "1";//验证码不能为空
		final String errCode_2 = "2";//验证码长度为固定值
		final String errCode_3 = "3";//姓名不能为空
		final String errCode_4 = "4";//身份证不能为空
		final String errCode_5 = "5";//电话号不能为空
		final String errCode_6 = "6";//请获取验证码
		final String errCode_7 = "7";//验证码已超时
		final String errCode_8 = "8";//验证码错误
		final String errCode_9 = "9";//身份处于审核状态
		final String errCode_10 = "10";//请输入原手机号码
		final String errCode_11 = "11";//原手机号码输入错误
		final String errCode_12 = "12";//无操作用户
		final String errCode_13 = "13";//手机号码格式不正确
		
		//微信号为空
		if(StringUtils.isEmpty(openId)) {
			return backJsonWithCode(errCode_1,ERR_OPEN_ID_NOT_GET);
		}

		//验证码不能为空
		if(StringUtils.isEmpty(msg)) {
			return backJsonWithCode(errCode_1,MSG_PHONE_CODE_MSG);
		}
		
		//验证码长度为固定值
		if(msg.length()!=Global.MOBILE_CODE_SIZE) {
			return backJsonWithCode(errCode_2,ERR_CODE_SIZE);
		}
		
		//姓名不能为空
		if(StringUtils.isEmpty(name)) {
			return backJsonWithCode(errCode_3,ERR_NAME_NULL);
		}
		
		//身份证不能为空
		if(StringUtils.isEmpty(idCard)) {
			return backJsonWithCode(errCode_4,ERR_ID_CARD_NULL);
		}
		
		//身份证号格式
		if(IdcardUtils.validateCard(idCard)) {
			return backJsonWithCode(errCode_4,ERR_ID_CARD_PATTERN);
		}
		
		//电话号不能为空
		if(StringUtils.isEmpty(phone)) {
			return backJsonWithCode(errCode_5,ERR_PHONE_NULL);
		}
		
		//手机号码格式不正确
		if(!PhoneUtils.validatePhone(phone)) {
			return backJsonWithCode(errCode_13,ERR_NEW_PHONE_PATTERN);
		}
		
		//验证码缓存
		String cacheKey = Global.PREFIX_MOBLIE_CODE + phone;
		PhoneMsgCache phoneMsgCache = (PhoneMsgCache)CacheUtils.get(cacheKey);
		if(null==phoneMsgCache) {
			return backJsonWithCode(errCode_6,ERR_CODE);
		}
		
		//是否已经超时
		long timeOut = phoneMsgCache.getTimeOut();
		if(System.currentTimeMillis() > timeOut) {
			return backJsonWithCode(errCode_7,ERR_CODE_TIME_OUT);
		}
		
		//验证码错误
		String cacheCode = (String)phoneMsgCache.getValue();
		if(!msg.equalsIgnoreCase(cacheCode)) {
			return backJsonWithCode(errCode_8,ERR_CODE_INPUT);
		}
		
		//该身份证号是否处于审核状态
		SysWxUserCheck sysWxUserCheck = wxService.findUserCheckByIdCard(idCard);
		if(null != sysWxUserCheck) {
			//有信息，处于审核状态
			return backJsonWithCode(errCode_9,ERR_NO_ACTIVE);
		}
		
		SysWxUser sysWxUser = null;
		//如果该身份证已经注册，需要提示用户输入之前注册的手机号以进行验证
		sysWxUser = wxService.findByIdCard(idCard);
		if(null != sysWxUser) {
			//不为空，表示用户存在
			if(StringUtils.isEmpty(oldPhone)) {
				//没有输入了旧手机号码 提示用户输入旧手机
				return backJsonWithCode(errCode_10,ERR_INPUT_OLD_PHONE);
			}else {
				//手机号码格式不正确
				if(!PhoneUtils.validatePhone(oldPhone)) {
					return backJsonWithCode(errCode_13,ERR_OLD_PHONE_PATTERN);
				}
				//输入旧手机号码
				String originPhone = sysWxUser.getPhone();//原先电话
				if(!originPhone.equals(oldPhone)) {
					//旧手机号码验证错误
					return backJsonWithCode(errCode_11,ERR_NOT_SAME_OLD_PHONE);
				}else {
					//号码输入正确，更改信息
					sysWxUser.setName(name);
					sysWxUser.setPhone(phone);
					if(null!=wxService.saveWxUserInfo(sysWxUser,openId)) {
						//移除缓存验证码 已经完成验证了
						CacheUtils.remove(cacheKey);
						CacheUtils.clearPhoneMsgCacheKeies();//清除其余缓存
						return backJsonWithCode(successCode,MSG_USER_SAVE_SUCCESS);
					}else {
						//操作异常
						return backJsonWithCode(errCode_12,ERR_NO_USER);
					}
				}
			}
		}
		
		//接下来是信息不存在 可以直接保存了
		
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
			//操作异常
			return backJsonWithCode(errCode_12,ERR_NO_USER);
		}else {
			return backJsonWithCode(successCode,MSG_USER_SAVE_SUCCESS);
		}
	}
	
	//录入快递信息
	@ResponseBody
	@RequestMapping(value="/saveExpress",method=RequestMethod.POST)
	public String saveExpress(HttpServletRequest request, HttpServletResponse response,Model model) {
		String phone=request.getParameter("phone");//获取phone
		String expressId=request.getParameter("expressId");//获取phone
		String openId=request.getParameter("openId");//获取phone
		
		final String successCode = "0";//成功码
		final String errCode_1 = "1";//快递单号不能为空
		final String errCode_2 = "2";//手机号不能为空
		final String errCode_3 = "3";//手机号格式错误
		final String errCode_4 = "4";//未检测到操作人员
		
		//微信号为空
		if(StringUtils.isEmpty(openId)) {
			return backJsonWithCode(errCode_1,ERR_OPEN_ID_NOT_GET);
		}

		//快递单号不能为空
		if(StringUtils.isEmpty(expressId)) {
			return backJsonWithCode(errCode_1,ERR_EXPREE_ID_NULL);
		}
		
		//电话号不能为空
		if(StringUtils.isEmpty(phone)) {
			return backJsonWithCode(errCode_2,ERR_PHONE_NULL);
		}
		
		//手机号码格式不正确
		if(!PhoneUtils.validatePhone(phone)) {
			return backJsonWithCode(errCode_3,ERR_NEW_PHONE_PATTERN);
		}
		
		//查询操作人员
		User user = wxService.findOperator(openId);
		if(null == user) {
			return backJsonWithCode(errCode_4,ERR_NO_USER);
		}
		
		
		SysExpress sysExpress = new SysExpress();
		//默认保存快递状态为已入库
		String state = DictUtils.getDictValue("已入库", "expressState", "0");
		sysExpress.setState(state);
		sysExpress.setExpressId(expressId);
		sysExpress.setPhone(phone);
		wxService.saveExpress(sysExpress,user);
		model.addAttribute("openId",openId);
		return backJsonWithCode(successCode,MSG_EXPRESS_SAVE_SUCCESS);
	}
	
	//收取快递信息
	@ResponseBody
	@RequestMapping(value="/endExpress",method=RequestMethod.POST)
	public String endExpress(HttpServletRequest request, HttpServletResponse response,Model model) {
		String userId=request.getParameter("userId");//获取userId
		String openId=request.getParameter("openId");//获取openId
		
		final String successCode = "0";//成功码
		final String errCode_1 = "1";//身份证号不能为空
		final String errCode_2 = "2";//身份证号格式错误
		
		//微信号为空
		if(StringUtils.isEmpty(openId)) {
			return backJsonWithCode(errCode_1,ERR_OPEN_ID_NOT_GET);
		}

		//身份证号
		if(StringUtils.isEmpty(userId)) {
			return backJsonWithCode(errCode_1,ERR_ID_CARD_NULL);
		}
		
		//身份证号格式
		if(!IdcardUtils.validateCard(userId)) {
			return backJsonWithCode(errCode_2,ERR_ID_CARD_PATTERN);
		}
		
		//验证快递单号
		List<SysExpress> sysExpresses = wxService.findExpressByIdCard(userId);
		if(null == sysExpresses || sysExpresses.size() == 0) {
			return backJsonWithCode(errCode_1,ERR_EXPREE_NOT_ID_CARD);
		}
		
		//查询操作人员
		User user = wxService.findOperator(openId);
		if(null == user) {
			return backJsonWithCode(errCode_2,ERR_NO_USER);
		}
		
		return backJsonWithCode(successCode,MSG_EXPRESS_QUERY_SUCCESS);
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
		//sysWxUser.setIdCard(idCard);
		sysWxUser.setPhone(phone);
		wxService.modifyWxUserInfo(sysWxUser,openId);
		//model.addAttribute("sysWxUser", sysWxUserService.getByIdCard(idCard));
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
