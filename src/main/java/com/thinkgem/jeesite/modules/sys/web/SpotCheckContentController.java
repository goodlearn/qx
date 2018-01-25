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
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckContent;
import com.thinkgem.jeesite.modules.sys.service.SpotCheckContentService;

/**
 * 点检卡内容表Controller
 * @author wzy
 * @version 2018-01-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/spotCheckContent")
public class SpotCheckContentController extends BaseController {

	@Autowired
	private SpotCheckContentService spotCheckContentService;
	
	@ModelAttribute
	public SpotCheckContent get(@RequestParam(required=false) String id) {
		SpotCheckContent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = spotCheckContentService.get(id);
		}
		if (entity == null){
			entity = new SpotCheckContent();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:spotCheckContent:view")
	@RequestMapping(value = {"list", ""})
	public String list(SpotCheckContent spotCheckContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SpotCheckContent> page = spotCheckContentService.findPage(new Page<SpotCheckContent>(request, response), spotCheckContent); 
		model.addAttribute("page", page);
		return "modules/checkspot/spotCheckContentList";
	}

	@RequiresPermissions("sys:spotCheckContent:view")
	@RequestMapping(value = "form")
	public String form(SpotCheckContent spotCheckContent, Model model) {
		model.addAttribute("spotCheckContent", spotCheckContent);
		return "modules/checkspot/spotCheckContentForm";
	}

	@RequiresPermissions("sys:spotCheckContent:edit")
	@RequestMapping(value = "save")
	public String save(SpotCheckContent spotCheckContent, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, spotCheckContent)){
			return form(spotCheckContent, model);
		}
		spotCheckContentService.save(spotCheckContent);
		addMessage(redirectAttributes, "保存点检卡内容表数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/spotCheckContent/?repage";
	}
	
	@RequiresPermissions("sys:spotCheckContent:edit")
	@RequestMapping(value = "delete")
	public String delete(SpotCheckContent spotCheckContent, RedirectAttributes redirectAttributes) {
		spotCheckContentService.delete(spotCheckContent);
		addMessage(redirectAttributes, "删除点检卡内容表数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/spotCheckContent/?repage";
	}

}