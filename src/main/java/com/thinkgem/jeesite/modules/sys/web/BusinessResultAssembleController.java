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
import com.thinkgem.jeesite.modules.sys.entity.BusinessResultAssemble;
import com.thinkgem.jeesite.modules.sys.service.BusinessResultAssembleService;

/**
 * 业务结果集表Controller
 * @author wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/businessResultAssemble")
public class BusinessResultAssembleController extends BaseController {

	@Autowired
	private BusinessResultAssembleService businessResultAssembleService;
	
	@ModelAttribute
	public BusinessResultAssemble get(@RequestParam(required=false) String id) {
		BusinessResultAssemble entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessResultAssembleService.get(id);
		}
		if (entity == null){
			entity = new BusinessResultAssemble();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:businessResultAssemble:view")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessResultAssemble businessResultAssemble, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessResultAssemble> page = businessResultAssembleService.findPage(new Page<BusinessResultAssemble>(request, response), businessResultAssemble); 
		model.addAttribute("page", page);
		return "modules/businessresult/businessResultAssembleList";
	}

	@RequiresPermissions("sys:businessResultAssemble:view")
	@RequestMapping(value = "form")
	public String form(BusinessResultAssemble businessResultAssemble, Model model) {
		model.addAttribute("businessResultAssemble", businessResultAssemble);
		return "modules/businessresult/businessResultAssembleForm";
	}

	@RequiresPermissions("sys:businessResultAssemble:edit")
	@RequestMapping(value = "save")
	public String save(BusinessResultAssemble businessResultAssemble, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, businessResultAssemble)){
			return form(businessResultAssemble, model);
		}
		businessResultAssembleService.save(businessResultAssemble);
		addMessage(redirectAttributes, "保存业务结果集表数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/businessResultAssemble/?repage";
	}
	
	@RequiresPermissions("sys:businessResultAssemble:edit")
	@RequestMapping(value = "delete")
	public String delete(BusinessResultAssemble businessResultAssemble, RedirectAttributes redirectAttributes) {
		businessResultAssembleService.delete(businessResultAssemble);
		addMessage(redirectAttributes, "删除业务结果集表数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/businessResultAssemble/?repage";
	}

}