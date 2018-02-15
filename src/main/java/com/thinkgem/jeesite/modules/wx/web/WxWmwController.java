package com.thinkgem.jeesite.modules.wx.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * WsMaskWc 任务处理
 * @author wzy
 *
 */
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.modules.sys.service.WsMaskWcService;
@Controller
@RequestMapping(value = "wmw")
public class WxWmwController extends WxBaseController{

	@Autowired
	private WsMaskWcService wsMaskWcService;
	
	/**
	 * 任务发布
	 */
	@RequestMapping(value="/releasePd",method=RequestMethod.GET)
	@ResponseBody
	public String releasePd(HttpServletRequest request, HttpServletResponse response,Model model) {
		//验证任务是否结束
		String wsmId = request.getParameter("wsmId");
		if(null == wsmId) {
			//任务号不存在
			return backJsonWithCode(errCode,ERR_WSM_ID_NULL);
		}
		String validateMsg = wsMaskWcService.validateReleasePd(wsmId);
		if(null != validateMsg) {
			//任务还未结束
			return backJsonWithCode(errCode,ERR_MASK_NOT_EXPIRED);
		}
		//发布任务
		wsMaskWcService.releasePd(wsmId);
		return backJsonWithCode(successCode,null);
	}
	
}
