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
import com.thinkgem.jeesite.modules.sys.entity.CheckSpotItem;
import com.thinkgem.jeesite.modules.sys.service.CheckSpotItemService;

/**
 * 点检项Controller
 * @author wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/checkSpotItem")
public class CheckSpotItemController extends BaseController {

	@Autowired
	private CheckSpotItemService checkSpotItemService;
	
	@ModelAttribute
	public CheckSpotItem get(@RequestParam(required=false) String id) {
		CheckSpotItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkSpotItemService.get(id);
		}
		if (entity == null){
			entity = new CheckSpotItem();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:checkSpotItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckSpotItem checkSpotItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckSpotItem> page = checkSpotItemService.findPage(new Page<CheckSpotItem>(request, response), checkSpotItem); 
		model.addAttribute("page", page);
		return "modules/checkspotitem/checkSpotItemList";
	}

	@RequiresPermissions("sys:checkSpotItem:view")
	@RequestMapping(value = "form")
	public String form(CheckSpotItem checkSpotItem, Model model) {
		model.addAttribute("checkSpotItem", checkSpotItem);
		return "modules/checkspotitem/checkSpotItemForm";
	}

	@RequiresPermissions("sys:checkSpotItem:edit")
	@RequestMapping(value = "save")
	public String save(CheckSpotItem checkSpotItem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkSpotItem)){
			return form(checkSpotItem, model);
		}
		checkSpotItemService.save(checkSpotItem);
		addMessage(redirectAttributes, "保存点检项数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/checkSpotItem/?repage";
	}
	
	@RequiresPermissions("sys:checkSpotItem:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckSpotItem checkSpotItem, RedirectAttributes redirectAttributes) {
		checkSpotItemService.delete(checkSpotItem);
		addMessage(redirectAttributes, "删除点检项数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/checkSpotItem/?repage";
	}

}