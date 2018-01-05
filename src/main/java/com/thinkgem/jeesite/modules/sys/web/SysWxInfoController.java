/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.IdcardUtils;
import com.thinkgem.jeesite.common.utils.PhoneUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.service.SysWxInfoService;

/**
 * 微信信息表Controller
 * @author wzy
 * @version 2017-12-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysWxInfo")
public class SysWxInfoController extends BaseController {

	@Autowired
	private SysWxInfoService sysWxInfoService;
	
	@ModelAttribute
	public SysWxInfo get(@RequestParam(required=false) String id) {
		SysWxInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysWxInfoService.get(id);
		}
		if (entity == null){
			entity = new SysWxInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysWxInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysWxInfo sysWxInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysWxInfo> page = sysWxInfoService.findPage(new Page<SysWxInfo>(request, response), sysWxInfo); 
		model.addAttribute("page", page);
		return "modules/sys/sysWxInfoList";
	}

	@RequiresPermissions("sys:sysWxInfo:view")
	@RequestMapping(value = "form")
	public String form(SysWxInfo sysWxInfo, Model model) {
		model.addAttribute("sysWxInfo", sysWxInfo);
		return "modules/sys/sysWxInfoForm";
	}

	@RequiresPermissions("sys:sysWxInfo:edit")
	@RequestMapping(value = "save")
	public String save(SysWxInfo sysWxInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysWxInfo)){
			return form(sysWxInfo, model);
		}
		
		
		//验证微信号
		String openId = sysWxInfo.getOpenId();
		if(null!=openId) {
			addMessage(model, "微信号不能为空");
			return form(sysWxInfo, model);
		}
		
		//验证快递单号
		String idCard = sysWxInfo.getIdCard();
		if(null!=idCard) {
			addMessage(model, "身份证不能为空");
			return form(sysWxInfo, model);
		}
		if(!IdcardUtils.validateIdCard18(idCard)) {
			addMessage(model, "身份证格式错误");
			return form(sysWxInfo, model);
		}
				
				
		sysWxInfoService.save(sysWxInfo);
		addMessage(redirectAttributes, "保存微信表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysWxInfo/?repage";
	}
	
	@RequiresPermissions("sys:sysWxInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SysWxInfo sysWxInfo, RedirectAttributes redirectAttributes) {
		sysWxInfoService.delete(sysWxInfo);
		addMessage(redirectAttributes, "删除微信表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysWxInfo/?repage";
	}

}