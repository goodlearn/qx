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
import com.thinkgem.jeesite.modules.sys.entity.CarInfo;
import com.thinkgem.jeesite.modules.sys.service.CarInfoService;

/**
 * 车辆信息Controller
 * @author wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/carInfo")
public class CarInfoController extends BaseController {

	@Autowired
	private CarInfoService carInfoService;
	
	@ModelAttribute
	public CarInfo get(@RequestParam(required=false) String id) {
		CarInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carInfoService.get(id);
		}
		if (entity == null){
			entity = new CarInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:carInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CarInfo carInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CarInfo> page = carInfoService.findPage(new Page<CarInfo>(request, response), carInfo); 
		model.addAttribute("page", page);
		return "modules/carinfo/carInfoList";
	}

	@RequiresPermissions("sys:carInfo:view")
	@RequestMapping(value = "form")
	public String form(CarInfo carInfo, Model model) {
		model.addAttribute("carInfo", carInfo);
		return "modules/carinfo/carInfoForm";
	}

	@RequiresPermissions("sys:carInfo:edit")
	@RequestMapping(value = "save")
	public String save(CarInfo carInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, carInfo)){
			return form(carInfo, model);
		}
		carInfoService.save(carInfo);
		addMessage(redirectAttributes, "保存车辆信息数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/carInfo/?repage";
	}
	
	@RequiresPermissions("sys:carInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CarInfo carInfo, RedirectAttributes redirectAttributes) {
		carInfoService.delete(carInfo);
		addMessage(redirectAttributes, "删除车辆信息数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/carInfo/?repage";
	}

}