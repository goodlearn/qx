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
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.service.MaskMainPersonService;

/**
 * 总负责人任务表Controller
 * @author wzy
 * @version 2018-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/maskMainPerson")
public class MaskMainPersonController extends BaseController {

	@Autowired
	private MaskMainPersonService maskMainPersonService;
	
	@ModelAttribute
	public MaskMainPerson get(@RequestParam(required=false) String id) {
		MaskMainPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = maskMainPersonService.get(id);
		}
		if (entity == null){
			entity = new MaskMainPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:maskMainPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(MaskMainPerson maskMainPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MaskMainPerson> page = maskMainPersonService.findPage(new Page<MaskMainPerson>(request, response), maskMainPerson); 
		model.addAttribute("page", page);
		return "modules/mask/maskMainPersonList";
	}

	@RequiresPermissions("sys:maskMainPerson:view")
	@RequestMapping(value = "form")
	public String form(MaskMainPerson maskMainPerson, Model model) {
		model.addAttribute("maskMainPerson", maskMainPerson);
		return "modules/mask/maskMainPersonForm";
	}

	@RequiresPermissions("sys:maskMainPerson:edit")
	@RequestMapping(value = "save")
	public String save(MaskMainPerson maskMainPerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, maskMainPerson)){
			return form(maskMainPerson, model);
		}
		maskMainPersonService.save(maskMainPerson);
		addMessage(redirectAttributes, "保存总负责人任务表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/maskMainPerson/?repage";
	}
	
	@RequiresPermissions("sys:maskMainPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(MaskMainPerson maskMainPerson, RedirectAttributes redirectAttributes) {
		maskMainPersonService.delete(maskMainPerson);
		addMessage(redirectAttributes, "删除总负责人任务表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/maskMainPerson/?repage";
	}

}