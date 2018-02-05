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
import com.thinkgem.jeesite.modules.sys.entity.CarMotorCycle;
import com.thinkgem.jeesite.modules.sys.service.CarMotorCycleService;

/**
 * 车间车型Controller
 * @author wzy
 * @version 2018-02-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/carMotorCycle")
public class CarMotorCycleController extends BaseController {

	@Autowired
	private CarMotorCycleService carMotorCycleService;
	
	@ModelAttribute
	public CarMotorCycle get(@RequestParam(required=false) String id) {
		CarMotorCycle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carMotorCycleService.get(id);
		}
		if (entity == null){
			entity = new CarMotorCycle();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:carMotorCycle:view")
	@RequestMapping(value = {"list", ""})
	public String list(CarMotorCycle carMotorCycle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CarMotorCycle> page = carMotorCycleService.findPage(new Page<CarMotorCycle>(request, response), carMotorCycle); 
		model.addAttribute("page", page);
		return "modules/carmotorcycle/carMotorCycleList";
	}

	@RequiresPermissions("sys:carMotorCycle:view")
	@RequestMapping(value = "form")
	public String form(CarMotorCycle carMotorCycle, Model model) {
		model.addAttribute("carMotorCycle", carMotorCycle);
		return "modules/carmotorcycle/carMotorCycleForm";
	}

	@RequiresPermissions("sys:carMotorCycle:edit")
	@RequestMapping(value = "save")
	public String save(CarMotorCycle carMotorCycle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, carMotorCycle)){
			return form(carMotorCycle, model);
		}
		carMotorCycleService.save(carMotorCycle);
		addMessage(redirectAttributes, "保存车间车型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/carMotorCycle/?repage";
	}
	
	@RequiresPermissions("sys:carMotorCycle:edit")
	@RequestMapping(value = "delete")
	public String delete(CarMotorCycle carMotorCycle, RedirectAttributes redirectAttributes) {
		carMotorCycleService.delete(carMotorCycle);
		addMessage(redirectAttributes, "删除车间车型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/carMotorCycle/?repage";
	}

}