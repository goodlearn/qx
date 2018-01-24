/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.WorkDepartment;
import com.thinkgem.jeesite.modules.sys.entity.WorkShop;
import com.thinkgem.jeesite.modules.sys.service.WorkDepartmentService;

/**
 * 车间部门Controller
 * @author wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/workDepartment")
public class WorkDepartmentController extends BaseController {

	@Autowired
	private WorkDepartmentService workDepartmentService;
	
	private final String ERR_WORK_SHOP_NULL = "车间信息不存在";
	
	//返回码
	private final String SUCCESS_CODE = "0";
	private final String ERR_CODE = "1";
	
	
	@ModelAttribute
	public WorkDepartment get(@RequestParam(required=false) String id) {
		WorkDepartment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = workDepartmentService.get(id);
		}
		if (entity == null){
			entity = new WorkDepartment();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:workDepartment:view")
	@RequestMapping(value = {"list", ""})
	public String list(WorkDepartment workDepartment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WorkDepartment> page = workDepartmentService.findPage(new Page<WorkDepartment>(request, response), workDepartment); 
		model.addAttribute("page", page);
		return "modules/workdepartment/workDepartmentList";
	}

	@RequiresPermissions("sys:workDepartment:view")
	@RequestMapping(value = "form")
	public String form(WorkDepartment workDepartment, Model model) {
		model.addAttribute("workDepartment", workDepartment);
		return "modules/workdepartment/workDepartmentForm";
	}

	@RequiresPermissions("sys:workDepartment:edit")
	@RequestMapping(value = "save")
	public String save(WorkDepartment workDepartment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, workDepartment)){
			return form(workDepartment, model);
		}
		workDepartmentService.save(workDepartment);
		addMessage(redirectAttributes, "保存车间部门数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workDepartment/?repage";
	}
	
	@RequiresPermissions("sys:workDepartment:edit")
	@RequestMapping(value = "delete")
	public String delete(WorkDepartment workDepartment, RedirectAttributes redirectAttributes) {
		workDepartmentService.delete(workDepartment);
		addMessage(redirectAttributes, "删除车间部门数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workDepartment/?repage";
	}
	

}