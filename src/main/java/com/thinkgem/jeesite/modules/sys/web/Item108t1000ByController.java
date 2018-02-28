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
import com.thinkgem.jeesite.modules.sys.entity.Item108t1000By;
import com.thinkgem.jeesite.modules.sys.service.Item108t1000ByService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * 108T卡车1000小时保养单(机械部分)Controller
 * @author wzy
 * @version 2018-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/item108t1000By")
public class Item108t1000ByController extends BaseController {

	@Autowired
	private Item108t1000ByService item108t1000ByService;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		item108t1000ByService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@ModelAttribute
	public Item108t1000By get(@RequestParam(required=false) String id) {
		Item108t1000By entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = item108t1000ByService.get(id);
		}
		if (entity == null){
			entity = new Item108t1000By();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:item108t1000By:view")
	@RequestMapping(value = {"list", ""})
	public String list(Item108t1000By item108t1000By, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Item108t1000By> page = item108t1000ByService.findPage(new Page<Item108t1000By>(request, response), item108t1000By); 
		model.addAttribute("page", page);
		return "modules/item108t1000By/item108t1000ByList";
	}

	@RequiresPermissions("sys:item108t1000By:view")
	@RequestMapping(value = "form")
	public String form(Item108t1000By item108t1000By, Model model) {
		model.addAttribute("item108t1000By", item108t1000By);
		return "modules/item108t1000By/item108t1000ByForm";
	}

	@RequiresPermissions("sys:item108t1000By:edit")
	@RequestMapping(value = "save")
	public String save(Item108t1000By item108t1000By, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, item108t1000By)){
			return form(item108t1000By, model);
		}
		item108t1000ByService.save(item108t1000By);
		addMessage(redirectAttributes, "保存108T卡车1000小时保养单(机械部分)成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item108t1000By/?repage";
	}
	
	@RequiresPermissions("sys:item108t1000By:edit")
	@RequestMapping(value = "delete")
	public String delete(Item108t1000By item108t1000By, RedirectAttributes redirectAttributes) {
		item108t1000ByService.delete(item108t1000By);
		addMessage(redirectAttributes, "删除108T卡车1000小时保养单(机械部分)成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item108t1000By/?repage";
	}

}