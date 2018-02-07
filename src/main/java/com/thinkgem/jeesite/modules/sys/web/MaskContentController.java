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
import com.thinkgem.jeesite.modules.sys.entity.MaskContent;
import com.thinkgem.jeesite.modules.sys.service.MaskContentService;

/**
 * 任务内容表Controller
 * @author wzy
 * @version 2018-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/maskContent")
public class MaskContentController extends BaseController {

	@Autowired
	private MaskContentService maskContentService;
	
	@ModelAttribute
	public MaskContent get(@RequestParam(required=false) String id) {
		MaskContent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = maskContentService.get(id);
		}
		if (entity == null){
			entity = new MaskContent();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:maskContent:view")
	@RequestMapping(value = {"list", ""})
	public String list(MaskContent maskContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MaskContent> page = maskContentService.findPage(new Page<MaskContent>(request, response), maskContent); 
		model.addAttribute("page", page);
		return "modules/mask/maskContentList";
	}

	@RequiresPermissions("sys:maskContent:view")
	@RequestMapping(value = "form")
	public String form(MaskContent maskContent, Model model) {
		model.addAttribute("maskContent", maskContent);
		return "modules/mask/maskContentForm";
	}

	@RequiresPermissions("sys:maskContent:edit")
	@RequestMapping(value = "save")
	public String save(MaskContent maskContent, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, maskContent)){
			return form(maskContent, model);
		}
		maskContentService.save(maskContent);
		addMessage(redirectAttributes, "保存任务内容表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/maskContent/?repage";
	}
	
	@RequiresPermissions("sys:maskContent:edit")
	@RequestMapping(value = "delete")
	public String delete(MaskContent maskContent, RedirectAttributes redirectAttributes) {
		maskContentService.delete(maskContent);
		addMessage(redirectAttributes, "删除任务内容表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/maskContent/?repage";
	}

}