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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.service.BusinessAssembleService;

/**
 * 业务集Controller
 * @author wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/businessAssemble")
public class BusinessAssembleController extends BaseController {

	@Autowired
	private BusinessAssembleService businessAssembleService;
	
	@ModelAttribute
	public BusinessAssemble get(@RequestParam(required=false) String id) {
		BusinessAssemble entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessAssembleService.get(id);
		}
		if (entity == null){
			entity = new BusinessAssemble();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:businessAssemble:view")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessAssemble businessAssemble, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessAssemble> page = businessAssembleService.findPage(new Page<BusinessAssemble>(request, response), businessAssemble); 
		model.addAttribute("page", page);
		return "modules/business/businessAssembleList";
	}

	@RequiresPermissions("sys:businessAssemble:view")
	@RequestMapping(value = "form")
	public String form(BusinessAssemble businessAssemble, Model model) {
		model.addAttribute("businessAssemble", businessAssemble);
		return "modules/business/businessAssembleForm";
	}

	@RequiresPermissions("sys:businessAssemble:edit")
	@RequestMapping(value = "save")
	public String save(BusinessAssemble businessAssemble, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, businessAssemble)){
			return form(businessAssemble, model);
		}
		businessAssembleService.save(businessAssemble);
		addMessage(redirectAttributes, "保存业务集数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/businessAssemble/?repage";
	}
	
	@RequiresPermissions("sys:businessAssemble:edit")
	@RequestMapping(value = "delete")
	public String delete(BusinessAssemble businessAssemble, RedirectAttributes redirectAttributes) {
		businessAssembleService.delete(businessAssemble);
		addMessage(redirectAttributes, "删除业务集数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/businessAssemble/?repage";
	}

}