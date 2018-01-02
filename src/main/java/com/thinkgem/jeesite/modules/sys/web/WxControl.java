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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.WxGlobal;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.SysWxUser;
import com.thinkgem.jeesite.modules.sys.manager.WxAccessTokenManager;
import com.thinkgem.jeesite.modules.sys.manager.WxMenuManager;
import com.thinkgem.jeesite.modules.sys.service.SysWxUserService;
import com.thinkgem.jeesite.modules.sys.service.WxService;

@Controller
@RequestMapping(value = "wx")
public class WxControl extends BaseController {
	
	@Autowired
	private SysWxUserService sysWxUserService;
	@Autowired
	private WxService wxService;
	@Autowired
	private WxMenuManager wxMenuManager;
	
	
	@RequestMapping(value = {"index"})
	public String index(Model model) {
		System.out.println("Test net is success");
		return null;
	}
	
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
	
	//获取保存个人信息页面
	@RequestMapping(value="/reqPersonUserInfo",method=RequestMethod.GET)
	public String reqPersonUserInfo() {
		return "modules/wxp/wxIdCardUserInfoAdd";
	}
	
	//获取更新个人信息页面
	@RequestMapping(value="/reqUPersonUserInfo",method=RequestMethod.GET)
	public String reqUPersonUserInfo() {
		return "modules/wxp/wxIdCardUserInfoAdd";
	}
	
	//保存个人信息
	@RequestMapping(value="/savePersonUserInfo",method=RequestMethod.POST)
	public String savePersonUserInfo(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String name = request.getParameter("name");
		String idCard = request.getParameter("idCard");
		String phone = request.getParameter("phone");
		SysWxUser sysWxUser = new SysWxUser();
		sysWxUser.setName(name);
		sysWxUser.setIdCard(idCard);
		sysWxUser.setPhone(phone);
		wxService.saveWxUserInfo(sysWxUser);
		return "modules/wxp/wxIdCardUserInfo";
	}
	
	//修改个人信息
	@RequestMapping(value="/modifyPersonUserInfo",method=RequestMethod.POST)
	public String modifyPersonUserInfo(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String name = request.getParameter("name");
		String idCard = request.getParameter("idCard");
		String phone = request.getParameter("phone");
		SysWxUser sysWxUser = new SysWxUser();
		sysWxUser.setName(name);
		sysWxUser.setIdCard(idCard);
		sysWxUser.setPhone(phone);
		wxService.modifyWxUserInfo(sysWxUser);
		return "modules/wxp/wxIdCardUserInfoModify";
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
	        		retPage = "modules/wxp/wxIdCardUserInfoAdd";
		        	return retPage;
	        	}else {
	        		//有身份信息
	        		model.addAttribute("sysWxUser", sysWxUserService.getByIdCard(idCard));
	        		retPage = "modules/wxp/wxIdCardUserInfoModify";
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
