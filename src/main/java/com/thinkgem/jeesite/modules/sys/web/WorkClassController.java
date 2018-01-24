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
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.service.WorkClassService;

/**
 * 车间班组Controller
 * @author wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/workClass")
public class WorkClassController extends BaseController {

	@Autowired
	private WorkClassService workClassService;
	
	@ModelAttribute
	public WorkClass get(@RequestParam(required=false) String id) {
		WorkClass entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = workClassService.get(id);
		}
		if (entity == null){
			entity = new WorkClass();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:workClass:view")
	@RequestMapping(value = {"list", ""})
	public String list(WorkClass workClass, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WorkClass> page = workClassService.findPage(new Page<WorkClass>(request, response), workClass); 
		model.addAttribute("page", page);
		return "modules/workclass/workClassList";
	}

	@RequiresPermissions("sys:workClass:view")
	@RequestMapping(value = "form")
	public String form(WorkClass workClass, Model model) {
		model.addAttribute("workClass", workClass);
		return "modules/workclass/workClassForm";
	}

	@RequiresPermissions("sys:workClass:edit")
	@RequestMapping(value = "save")
	public String save(WorkClass workClass, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, workClass)){
			return form(workClass, model);
		}
		workClassService.save(workClass);
		addMessage(redirectAttributes, "保存班组数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workClass/?repage";
	}
	
	@RequiresPermissions("sys:workClass:edit")
	@RequestMapping(value = "delete")
	public String delete(WorkClass workClass, RedirectAttributes redirectAttributes) {
		workClassService.delete(workClass);
		addMessage(redirectAttributes, "删除班组数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workClass/?repage";
	}

}