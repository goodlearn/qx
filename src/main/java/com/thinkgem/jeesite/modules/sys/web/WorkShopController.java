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
import com.thinkgem.jeesite.modules.sys.entity.WorkShop;
import com.thinkgem.jeesite.modules.sys.service.WorkShopService;

/**
 * 车间Controller
 * @author wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/workShop")
public class WorkShopController extends BaseController {

	@Autowired
	private WorkShopService workShopService;
	
	@ModelAttribute
	public WorkShop get(@RequestParam(required=false) String id) {
		WorkShop entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = workShopService.get(id);
		}
		if (entity == null){
			entity = new WorkShop();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:workShop:view")
	@RequestMapping(value = {"list", ""})
	public String list(WorkShop workShop, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WorkShop> page = workShopService.findPage(new Page<WorkShop>(request, response), workShop); 
		model.addAttribute("page", page);
		return "modules/workshop/workShopList";
	}

	@RequiresPermissions("sys:workShop:view")
	@RequestMapping(value = "form")
	public String form(WorkShop workShop, Model model) {
		model.addAttribute("workShop", workShop);
		return "modules/workshop/workShopForm";
	}

	@RequiresPermissions("sys:workShop:edit")
	@RequestMapping(value = "save")
	public String save(WorkShop workShop, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, workShop)){
			return form(workShop, model);
		}
		workShopService.save(workShop);
		addMessage(redirectAttributes, "保存车间数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workShop/?repage";
	}
	
	@RequiresPermissions("sys:workShop:edit")
	@RequestMapping(value = "delete")
	public String delete(WorkShop workShop, RedirectAttributes redirectAttributes) {
		workShopService.delete(workShop);
		addMessage(redirectAttributes, "删除车间数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workShop/?repage";
	}

}