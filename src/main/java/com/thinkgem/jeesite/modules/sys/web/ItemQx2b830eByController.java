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
import com.thinkgem.jeesite.modules.sys.entity.ItemQx2b830eBy;
import com.thinkgem.jeesite.modules.sys.service.ItemQx2b830eByService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * 汽修二班830E保养责任分区Controller
 * @author wzy
 * @version 2018-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/itemQx2b830eBy")
public class ItemQx2b830eByController extends BaseController {

	@Autowired
	private ItemQx2b830eByService itemQx2b830eByService;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		itemQx2b830eByService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@ModelAttribute
	public ItemQx2b830eBy get(@RequestParam(required=false) String id) {
		ItemQx2b830eBy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = itemQx2b830eByService.get(id);
		}
		if (entity == null){
			entity = new ItemQx2b830eBy();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:itemQx2b830eBy:view")
	@RequestMapping(value = {"list", ""})
	public String list(ItemQx2b830eBy itemQx2b830eBy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ItemQx2b830eBy> page = itemQx2b830eByService.findPage(new Page<ItemQx2b830eBy>(request, response), itemQx2b830eBy); 
		model.addAttribute("page", page);
		return "modules/itemQx2b830eBy/itemQx2b830eByList";
	}

	@RequiresPermissions("sys:itemQx2b830eBy:view")
	@RequestMapping(value = "form")
	public String form(ItemQx2b830eBy itemQx2b830eBy, Model model) {
		model.addAttribute("itemQx2b830eBy", itemQx2b830eBy);
		return "modules/itemQx2b830eBy/itemQx2b830eByForm";
	}

	@RequiresPermissions("sys:itemQx2b830eBy:edit")
	@RequestMapping(value = "save")
	public String save(ItemQx2b830eBy itemQx2b830eBy, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, itemQx2b830eBy)){
			return form(itemQx2b830eBy, model);
		}
		itemQx2b830eByService.save(itemQx2b830eBy);
		addMessage(redirectAttributes, "保存汽修二班830E保养责任分区成功");
		return "redirect:"+Global.getAdminPath()+"/sys/itemQx2b830eBy/?repage";
	}
	
	@RequiresPermissions("sys:itemQx2b830eBy:edit")
	@RequestMapping(value = "delete")
	public String delete(ItemQx2b830eBy itemQx2b830eBy, RedirectAttributes redirectAttributes) {
		itemQx2b830eByService.delete(itemQx2b830eBy);
		addMessage(redirectAttributes, "删除汽修二班830E保养责任分区成功");
		return "redirect:"+Global.getAdminPath()+"/sys/itemQx2b830eBy/?repage";
	}

}