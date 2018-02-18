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
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.service.MaskSinglePersonService;

/**
 * 个人负责人任务表Controller
 * @author wzy
 * @version 2018-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/maskSinglePerson")
public class MaskSinglePersonController extends BaseController {

	@Autowired
	private MaskSinglePersonService maskSinglePersonService;
	
	@ModelAttribute
	public MaskSinglePerson get(@RequestParam(required=false) String id) {
		MaskSinglePerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = maskSinglePersonService.get(id);
		}
		if (entity == null){
			entity = new MaskSinglePerson();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:maskSinglePerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(MaskSinglePerson maskSinglePerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MaskSinglePerson> page = maskSinglePersonService.findPage(new Page<MaskSinglePerson>(request, response), maskSinglePerson); 
		model.addAttribute("page", page);
		return "modules/mask/maskSinglePersonList";
	}

	@RequiresPermissions("sys:maskSinglePerson:view")
	@RequestMapping(value = "form")
	public String form(MaskSinglePerson maskSinglePerson, Model model) {
		model.addAttribute("maskSinglePerson", maskSinglePerson);
		return "modules/mask/maskSinglePersonForm";
	}

	@RequiresPermissions("sys:maskSinglePerson:edit")
	@RequestMapping(value = "save")
	public String save(MaskSinglePerson maskSinglePerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, maskSinglePerson)){
			return form(maskSinglePerson, model);
		}
		maskSinglePersonService.save(maskSinglePerson);
		addMessage(redirectAttributes, "保存个人负责人任务表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/maskSinglePerson/?repage";
	}
	
	@RequiresPermissions("sys:maskSinglePerson:edit")
	@RequestMapping(value = "delete")
	public String delete(MaskSinglePerson maskSinglePerson, RedirectAttributes redirectAttributes) {
		maskSinglePersonService.delete(maskSinglePerson);
		addMessage(redirectAttributes, "删除个人负责人任务表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/maskSinglePerson/?repage";
	}

}