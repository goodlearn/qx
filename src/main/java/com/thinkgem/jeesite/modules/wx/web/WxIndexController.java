package com.thinkgem.jeesite.modules.wx.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

/**
 * 首页
 * @author Wzy
 *
 */
@Controller
@RequestMapping(value = "wi")
public class WxIndexController extends WxBaseController{

	
	/**
	 * 页面跳转 -- 获取首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/indexInfo",method=RequestMethod.GET)
	public String indexInfo(HttpServletRequest request, HttpServletResponse response,Model model) {
		return INDEX_INFO;
	}
	
}
