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
import com.thinkgem.jeesite.modules.sys.entity.Item220tZxBy;
import com.thinkgem.jeesite.modules.sys.service.Item220tZxByService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * 220T自卸卡车保养单（电气部分）Controller
 * @author wzy
 * @version 2018-02-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/item220tZxBy")
public class Item220tZxByController extends BaseController {

	@Autowired
	private Item220tZxByService item220tZxByService;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	@ModelAttribute
	public Item220tZxBy get(@RequestParam(required=false) String id) {
		Item220tZxBy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = item220tZxByService.get(id);
		}
		if (entity == null){
			entity = new Item220tZxBy();
		}
		return entity;
	}
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		item220tZxByService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@RequiresPermissions("sys:item220tZxBy:view")
	@RequestMapping(value = {"list", ""})
	public String list(Item220tZxBy item220tZxBy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Item220tZxBy> page = item220tZxByService.findPage(new Page<Item220tZxBy>(request, response), item220tZxBy); 
		model.addAttribute("page", page);
		return "modules/item220tZxBy/item220tZxByList";
	}

	@RequiresPermissions("sys:item220tZxBy:view")
	@RequestMapping(value = "form")
	public String form(Item220tZxBy item220tZxBy, Model model) {
		model.addAttribute("item220tZxBy", item220tZxBy);
		return "modules/item220tZxBy/item220tZxByForm";
	}

	@RequiresPermissions("sys:item220tZxBy:edit")
	@RequestMapping(value = "save")
	public String save(Item220tZxBy item220tZxBy, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, item220tZxBy)){
			return form(item220tZxBy, model);
		}
		item220tZxByService.save(item220tZxBy);
		addMessage(redirectAttributes, "保存220T自卸卡车保养单（电气部分）成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item220tZxBy/?repage";
	}
	
	@RequiresPermissions("sys:item220tZxBy:edit")
	@RequestMapping(value = "delete")
	public String delete(Item220tZxBy item220tZxBy, RedirectAttributes redirectAttributes) {
		item220tZxByService.delete(item220tZxBy);
		addMessage(redirectAttributes, "删除220T自卸卡车保养单（电气部分）成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item220tZxBy/?repage";
	}

}