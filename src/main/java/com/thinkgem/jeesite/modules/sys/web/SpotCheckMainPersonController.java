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
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckMainPerson;
import com.thinkgem.jeesite.modules.sys.service.SpotCheckMainPersonService;

/**
 * 点检卡总负责人任务表Controller
 * @author wzy
 * @version 2018-01-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/spotCheckMainPerson")
public class SpotCheckMainPersonController extends BaseController {

	@Autowired
	private SpotCheckMainPersonService spotCheckMainPersonService;
	
	@ModelAttribute
	public SpotCheckMainPerson get(@RequestParam(required=false) String id) {
		SpotCheckMainPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = spotCheckMainPersonService.get(id);
		}
		if (entity == null){
			entity = new SpotCheckMainPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:spotCheckMainPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(SpotCheckMainPerson spotCheckMainPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SpotCheckMainPerson> page = spotCheckMainPersonService.findPage(new Page<SpotCheckMainPerson>(request, response), spotCheckMainPerson); 
		model.addAttribute("page", page);
		return "modules/checkspot/spotCheckMainPersonList";
	}

	@RequiresPermissions("sys:spotCheckMainPerson:view")
	@RequestMapping(value = "form")
	public String form(SpotCheckMainPerson spotCheckMainPerson, Model model) {
		model.addAttribute("spotCheckMainPerson", spotCheckMainPerson);
		return "modules/checkspot/spotCheckMainPersonForm";
	}

	@RequiresPermissions("sys:spotCheckMainPerson:edit")
	@RequestMapping(value = "save")
	public String save(SpotCheckMainPerson spotCheckMainPerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, spotCheckMainPerson)){
			return form(spotCheckMainPerson, model);
		}
		spotCheckMainPersonService.save(spotCheckMainPerson);
		addMessage(redirectAttributes, "保存点检卡总负责人任务表数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/spotCheckMainPerson/?repage";
	}
	
	@RequiresPermissions("sys:spotCheckMainPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(SpotCheckMainPerson spotCheckMainPerson, RedirectAttributes redirectAttributes) {
		spotCheckMainPersonService.delete(spotCheckMainPerson);
		addMessage(redirectAttributes, "删除点检卡总负责人任务表数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/spotCheckMainPerson/?repage";
	}

}