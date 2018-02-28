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
import com.thinkgem.jeesite.modules.sys.entity.Item220tQgDj;
import com.thinkgem.jeesite.modules.sys.service.Item220tQgDjService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * 220T卡车钳工点检分区Controller
 * @author wzy
 * @version 2018-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/item220tQgDj")
public class Item220tQgDjController extends BaseController {

	@Autowired
	private Item220tQgDjService item220tQgDjService;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		item220tQgDjService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@ModelAttribute
	public Item220tQgDj get(@RequestParam(required=false) String id) {
		Item220tQgDj entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = item220tQgDjService.get(id);
		}
		if (entity == null){
			entity = new Item220tQgDj();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:item220tQgDj:view")
	@RequestMapping(value = {"list", ""})
	public String list(Item220tQgDj item220tQgDj, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Item220tQgDj> page = item220tQgDjService.findPage(new Page<Item220tQgDj>(request, response), item220tQgDj); 
		model.addAttribute("page", page);
		return "modules/item220tQgDj/item220tQgDjList";
	}

	@RequiresPermissions("sys:item220tQgDj:view")
	@RequestMapping(value = "form")
	public String form(Item220tQgDj item220tQgDj, Model model) {
		model.addAttribute("item220tQgDj", item220tQgDj);
		return "modules/item220tQgDj/item220tQgDjForm";
	}

	@RequiresPermissions("sys:item220tQgDj:edit")
	@RequestMapping(value = "save")
	public String save(Item220tQgDj item220tQgDj, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, item220tQgDj)){
			return form(item220tQgDj, model);
		}
		item220tQgDjService.save(item220tQgDj);
		addMessage(redirectAttributes, "保存220T卡车钳工点检分区成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item220tQgDj/?repage";
	}
	
	@RequiresPermissions("sys:item220tQgDj:edit")
	@RequestMapping(value = "delete")
	public String delete(Item220tQgDj item220tQgDj, RedirectAttributes redirectAttributes) {
		item220tQgDjService.delete(item220tQgDj);
		addMessage(redirectAttributes, "删除220T卡车钳工点检分区成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item220tQgDj/?repage";
	}

}