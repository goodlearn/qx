package com.thinkgem.jeesite.modules.sys.web;

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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Item220DgDj;
import com.thinkgem.jeesite.modules.sys.service.Item220DgDjService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * 220T卡车电工周点检卡（电气部分）Controller
 * @author wzy
 * @version 2018-02-17
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/item220DgDj")
public class Item220DgDjController extends BaseController {

	@Autowired
	private Item220DgDjService item220DgDjService;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		item220DgDjService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@ModelAttribute
	public Item220DgDj get(@RequestParam(required=false) String id) {
		Item220DgDj entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = item220DgDjService.get(id);
		}
		if (entity == null){
			entity = new Item220DgDj();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:item220DgDj:view")
	@RequestMapping(value = {"list", ""})
	public String list(Item220DgDj item220DgDj, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Item220DgDj> page = item220DgDjService.findPage(new Page<Item220DgDj>(request, response), item220DgDj); 
		model.addAttribute("page", page);
		return "modules/item220DgDj/item220DgDjList";
	}

	@RequiresPermissions("sys:item220DgDj:view")
	@RequestMapping(value = "form")
	public String form(Item220DgDj item220DgDj, Model model) {
		model.addAttribute("item220DgDj", item220DgDj);
		return "modules/item220DgDj/item220DgDjForm";
	}

	@RequiresPermissions("sys:item220DgDj:edit")
	@RequestMapping(value = "save")
	public String save(Item220DgDj item220DgDj, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, item220DgDj)){
			return form(item220DgDj, model);
		}
		item220DgDjService.save(item220DgDj);
		addMessage(redirectAttributes, "保存220T卡车电工周点检卡（电气部分）成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item220DgDj/?repage";
	}
	
	@RequiresPermissions("sys:item220DgDj:edit")
	@RequestMapping(value = "delete")
	public String delete(Item220DgDj item220DgDj, RedirectAttributes redirectAttributes) {
		item220DgDjService.delete(item220DgDj);
		addMessage(redirectAttributes, "删除220T卡车电工周点检卡（电气部分）成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item220DgDj/?repage";
	}

}