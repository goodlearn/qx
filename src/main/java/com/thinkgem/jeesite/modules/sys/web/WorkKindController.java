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
import com.thinkgem.jeesite.modules.sys.entity.WorkKind;
import com.thinkgem.jeesite.modules.sys.service.WorkKindService;

/**
 * 车间工种Controller
 * @author wzy
 * @version 2018-02-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/workKind")
public class WorkKindController extends BaseController {

	@Autowired
	private WorkKindService workKindService;
	
	@ModelAttribute
	public WorkKind get(@RequestParam(required=false) String id) {
		WorkKind entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = workKindService.get(id);
		}
		if (entity == null){
			entity = new WorkKind();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:workKind:view")
	@RequestMapping(value = {"list", ""})
	public String list(WorkKind workKind, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WorkKind> page = workKindService.findPage(new Page<WorkKind>(request, response), workKind); 
		model.addAttribute("page", page);
		return "modules/workkind/workKindList";
	}

	@RequiresPermissions("sys:workKind:view")
	@RequestMapping(value = "form")
	public String form(WorkKind workKind, Model model) {
		model.addAttribute("workKind", workKind);
		return "modules/workkind/workKindForm";
	}

	@RequiresPermissions("sys:workKind:edit")
	@RequestMapping(value = "save")
	public String save(WorkKind workKind, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, workKind)){
			return form(workKind, model);
		}
		workKindService.save(workKind);
		addMessage(redirectAttributes, "保存车间工种数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workKind/?repage";
	}
	
	@RequiresPermissions("sys:workKind:edit")
	@RequestMapping(value = "delete")
	public String delete(WorkKind workKind, RedirectAttributes redirectAttributes) {
		workKindService.delete(workKind);
		addMessage(redirectAttributes, "删除车间工种数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workKind/?repage";
	}

}