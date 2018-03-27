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
import com.thinkgem.jeesite.modules.sys.entity.MonthMask;
import com.thinkgem.jeesite.modules.sys.service.MonthMaskService;
import com.thinkgem.jeesite.modules.sys.service.MonthMaskWcService;
import com.thinkgem.jeesite.modules.sys.service.WorkPersonService;

/**
 * 月度任务表Controller
 * @author wzy
 * @version 2018-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/monthMask")
public class MonthMaskController extends BaseController {

	@Autowired
	private MonthMaskService monthMaskService;
	
	@Autowired
	private MonthMaskWcService monthMaskWcService;
	
	@Autowired
	private WorkPersonService workPersonService;
	
	@ModelAttribute
	public MonthMask get(@RequestParam(required=false) String id) {
		MonthMask entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = monthMaskService.get(id);
		}
		if (entity == null){
			entity = new MonthMask();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:monthMask:view")
	@RequestMapping(value = {"list", ""})
	public String list(MonthMask monthMask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MonthMask> page = monthMaskService.findPage(new Page<MonthMask>(request, response), monthMask); 
		model.addAttribute("page", page);
		model.addAttribute("wps",workPersonService.findWpsByUser());
		return "modules/monthMask/monthMaskList";
	}

	@RequiresPermissions("sys:monthMask:view")
	@RequestMapping(value = "form")
	public String form(MonthMask monthMask, Model model) {
		model.addAttribute("wps",workPersonService.findWpsByUser());
		model.addAttribute("monthMask", monthMask);
		model.addAttribute("mmwcList", monthMaskWcService.findMaskMmwcs());
		return "modules/monthMask/monthMaskForm";
	}

	@RequiresPermissions("sys:monthMask:edit")
	@RequestMapping(value = "save")
	public String save(MonthMask monthMask, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, monthMask)){
			return form(monthMask, model);
		}
		//如果ID是空的说明是保存数据，我们置空数据
		if(StringUtils.isBlank(monthMask.getId())) {
			monthMask.setId(null);
		}
		//验证数量
		if(!monthMaskService.validateNum(monthMask)) {
			addMessage(redirectAttributes, "任务数量已达上限");
			return "redirect:"+Global.getAdminPath()+"/sys/monthMask/?repage";
		}
		monthMaskService.saveForm(monthMask);
		addMessage(redirectAttributes, "保存月度任务表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/monthMask/?repage";
	}
	
	@RequiresPermissions("sys:monthMask:edit")
	@RequestMapping(value = "delete")
	public String delete(MonthMask monthMask, RedirectAttributes redirectAttributes) {
		monthMaskService.delete(monthMask);
		addMessage(redirectAttributes, "删除月度任务表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/monthMask/?repage";
	}

}