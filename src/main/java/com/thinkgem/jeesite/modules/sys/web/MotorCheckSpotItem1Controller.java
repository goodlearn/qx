/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.MotorCheckSpotItem1;
import com.thinkgem.jeesite.modules.sys.service.MotorCheckSpotItem1Service;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * 发动机点检单一Controller
 * @author wzy
 * @version 2018-02-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/motorCheckSpotItem1")
public class MotorCheckSpotItem1Controller extends BaseController {

	@Autowired
	private MotorCheckSpotItem1Service motorCheckSpotItem1Service;
	
	@ModelAttribute
	public MotorCheckSpotItem1 get(@RequestParam(required=false) String id) {
		MotorCheckSpotItem1 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = motorCheckSpotItem1Service.get(id);
		}
		if (entity == null){
			entity = new MotorCheckSpotItem1();
		}
		return entity;
	}

	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] ViewMcsi1) {
		
		System.out.println("data "+ ViewMcsi1.length);
		return "modules/motorcheckspotitem1/motorCheckSpotItem1List";
	}
	
	@RequiresPermissions("sys:motorCheckSpotItem1:view")
	@RequestMapping(value = {"list", ""})
	public String list(MotorCheckSpotItem1 motorCheckSpotItem1, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MotorCheckSpotItem1> page = motorCheckSpotItem1Service.findPage(new Page<MotorCheckSpotItem1>(request, response), motorCheckSpotItem1); 
		model.addAttribute("page", page);
		return "modules/motorcheckspotitem1/motorCheckSpotItem1List";
	}

	@RequiresPermissions("sys:motorCheckSpotItem1:view")
	@RequestMapping(value = "form")
	public String form(MotorCheckSpotItem1 motorCheckSpotItem1, Model model) {
		model.addAttribute("motorCheckSpotItem1", motorCheckSpotItem1);
		return "modules/motorcheckspotitem1/motorCheckSpotItem1Form";
	}

	@RequiresPermissions("sys:motorCheckSpotItem1:edit")
	@RequestMapping(value = "save")
	public String save(MotorCheckSpotItem1 motorCheckSpotItem1, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, motorCheckSpotItem1)){
			return form(motorCheckSpotItem1, model);
		}
		motorCheckSpotItem1Service.save(motorCheckSpotItem1);
		addMessage(redirectAttributes, "保存发动机点检单一成功");
		return "redirect:"+Global.getAdminPath()+"/sys/motorCheckSpotItem1/?repage";
	}
	
	@RequiresPermissions("sys:motorCheckSpotItem1:edit")
	@RequestMapping(value = "delete")
	public String delete(MotorCheckSpotItem1 motorCheckSpotItem1, RedirectAttributes redirectAttributes) {
		motorCheckSpotItem1Service.delete(motorCheckSpotItem1);
		addMessage(redirectAttributes, "删除发动机点检单一成功");
		return "redirect:"+Global.getAdminPath()+"/sys/motorCheckSpotItem1/?repage";
	}

}