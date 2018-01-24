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
import com.thinkgem.jeesite.modules.sys.entity.BusinessResultItem;
import com.thinkgem.jeesite.modules.sys.service.BusinessResultItemService;

/**
 * 业务结果项表Controller
 * @author wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/businessResultItem")
public class BusinessResultItemController extends BaseController {

	@Autowired
	private BusinessResultItemService businessResultItemService;
	
	@ModelAttribute
	public BusinessResultItem get(@RequestParam(required=false) String id) {
		BusinessResultItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessResultItemService.get(id);
		}
		if (entity == null){
			entity = new BusinessResultItem();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:businessResultItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessResultItem businessResultItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessResultItem> page = businessResultItemService.findPage(new Page<BusinessResultItem>(request, response), businessResultItem); 
		model.addAttribute("page", page);
		return "modules/businessresult/businessResultItemList";
	}
	
	@RequiresPermissions("sys:businessResultItem:view")
	@RequestMapping(value = {"listbra"})
	public String listbra(BusinessResultItem businessResultItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		String assembleId = request.getParameter("assembleId");
		if(StringUtils.isNotEmpty(assembleId)) {
			businessResultItem.setAssembleId(assembleId);
		}
		Page<BusinessResultItem> page = businessResultItemService.findPage(new Page<BusinessResultItem>(request, response), businessResultItem); 
		model.addAttribute("page", page);
		return "modules/businessresult/businessResultItemList";
	}

	@RequiresPermissions("sys:businessResultItem:view")
	@RequestMapping(value = "form")
	public String form(BusinessResultItem businessResultItem, Model model) {
		model.addAttribute("businessResultItem", businessResultItem);
		return "modules/businessresult/businessResultItemForm";
	}

	@RequiresPermissions("sys:businessResultItem:edit")
	@RequestMapping(value = "save")
	public String save(BusinessResultItem businessResultItem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, businessResultItem)){
			return form(businessResultItem, model);
		}
		businessResultItemService.save(businessResultItem);
		addMessage(redirectAttributes, "保存业务结果项表数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/businessResultItem/?repage";
	}
	
	@RequiresPermissions("sys:businessResultItem:edit")
	@RequestMapping(value = "delete")
	public String delete(BusinessResultItem businessResultItem, RedirectAttributes redirectAttributes) {
		businessResultItemService.delete(businessResultItem);
		addMessage(redirectAttributes, "删除业务结果项表数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/businessResultItem/?repage";
	}

}