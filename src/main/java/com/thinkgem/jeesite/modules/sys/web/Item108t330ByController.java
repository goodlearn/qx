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
import com.thinkgem.jeesite.modules.sys.entity.Item108t330By;
import com.thinkgem.jeesite.modules.sys.service.Item108t330ByService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * 108T卡车330小时保养单(机械部分)Controller
 * @author wzy
 * @version 2018-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/item108t330By")
public class Item108t330ByController extends BaseController {

	@Autowired
	private Item108t330ByService item108t330ByService;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		item108t330ByService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@ModelAttribute
	public Item108t330By get(@RequestParam(required=false) String id) {
		Item108t330By entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = item108t330ByService.get(id);
		}
		if (entity == null){
			entity = new Item108t330By();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:item108t330By:view")
	@RequestMapping(value = {"list", ""})
	public String list(Item108t330By item108t330By, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Item108t330By> page = item108t330ByService.findPage(new Page<Item108t330By>(request, response), item108t330By); 
		model.addAttribute("page", page);
		return "modules/item108t330By/item108t330ByList";
	}

	@RequiresPermissions("sys:item108t330By:view")
	@RequestMapping(value = "form")
	public String form(Item108t330By item108t330By, Model model) {
		model.addAttribute("item108t330By", item108t330By);
		return "modules/item108t330By/item108t330ByForm";
	}

	@RequiresPermissions("sys:item108t330By:edit")
	@RequestMapping(value = "save")
	public String save(Item108t330By item108t330By, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, item108t330By)){
			return form(item108t330By, model);
		}
		item108t330ByService.save(item108t330By);
		addMessage(redirectAttributes, "保存108T卡车330小时保养单(机械部分)成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item108t330By/?repage";
	}
	
	@RequiresPermissions("sys:item108t330By:edit")
	@RequestMapping(value = "delete")
	public String delete(Item108t330By item108t330By, RedirectAttributes redirectAttributes) {
		item108t330ByService.delete(item108t330By);
		addMessage(redirectAttributes, "删除108T卡车330小时保养单(机械部分)成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item108t330By/?repage";
	}

}