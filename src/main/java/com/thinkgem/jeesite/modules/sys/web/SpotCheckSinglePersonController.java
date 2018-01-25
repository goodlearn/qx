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
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckSinglePerson;
import com.thinkgem.jeesite.modules.sys.service.SpotCheckSinglePersonService;

/**
 * 点检卡个人负责人任务表Controller
 * @author wzy
 * @version 2018-01-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/spotCheckSinglePerson")
public class SpotCheckSinglePersonController extends BaseController {

	@Autowired
	private SpotCheckSinglePersonService spotCheckSinglePersonService;
	
	@ModelAttribute
	public SpotCheckSinglePerson get(@RequestParam(required=false) String id) {
		SpotCheckSinglePerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = spotCheckSinglePersonService.get(id);
		}
		if (entity == null){
			entity = new SpotCheckSinglePerson();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:spotCheckSinglePerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(SpotCheckSinglePerson spotCheckSinglePerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SpotCheckSinglePerson> page = spotCheckSinglePersonService.findPage(new Page<SpotCheckSinglePerson>(request, response), spotCheckSinglePerson); 
		model.addAttribute("page", page);
		return "modules/checkspot/spotCheckSinglePersonList";
	}

	@RequiresPermissions("sys:spotCheckSinglePerson:view")
	@RequestMapping(value = "form")
	public String form(SpotCheckSinglePerson spotCheckSinglePerson, Model model) {
		model.addAttribute("spotCheckSinglePerson", spotCheckSinglePerson);
		return "modules/checkspot/spotCheckSinglePersonForm";
	}

	@RequiresPermissions("sys:spotCheckSinglePerson:edit")
	@RequestMapping(value = "save")
	public String save(SpotCheckSinglePerson spotCheckSinglePerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, spotCheckSinglePerson)){
			return form(spotCheckSinglePerson, model);
		}
		spotCheckSinglePersonService.save(spotCheckSinglePerson);
		addMessage(redirectAttributes, "保存点检卡个人负责人任务表数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/spotCheckSinglePerson/?repage";
	}
	
	@RequiresPermissions("sys:spotCheckSinglePerson:edit")
	@RequestMapping(value = "delete")
	public String delete(SpotCheckSinglePerson spotCheckSinglePerson, RedirectAttributes redirectAttributes) {
		spotCheckSinglePersonService.delete(spotCheckSinglePerson);
		addMessage(redirectAttributes, "删除点检卡个人负责人任务表数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/spotCheckSinglePerson/?repage";
	}

}