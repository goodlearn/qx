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
import com.thinkgem.jeesite.modules.sys.entity.ItemSf31904KcDgDj;
import com.thinkgem.jeesite.modules.sys.service.ItemSf31904KcDgDjService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * SF31904卡车电工周点检卡（电气部分）Controller
 * @author wzy
 * @version 2018-02-17
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/itemSf31904KcDgDj")
public class ItemSf31904KcDgDjController extends BaseController {

	@Autowired
	private ItemSf31904KcDgDjService itemSf31904KcDgDjService;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		itemSf31904KcDgDjService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@ModelAttribute
	public ItemSf31904KcDgDj get(@RequestParam(required=false) String id) {
		ItemSf31904KcDgDj entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = itemSf31904KcDgDjService.get(id);
		}
		if (entity == null){
			entity = new ItemSf31904KcDgDj();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:itemSf31904KcDgDj:view")
	@RequestMapping(value = {"list", ""})
	public String list(ItemSf31904KcDgDj itemSf31904KcDgDj, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ItemSf31904KcDgDj> page = itemSf31904KcDgDjService.findPage(new Page<ItemSf31904KcDgDj>(request, response), itemSf31904KcDgDj); 
		model.addAttribute("page", page);
		return "modules/iemSf31904KcDgDj/itemSf31904KcDgDjList";
	}

	@RequiresPermissions("sys:itemSf31904KcDgDj:view")
	@RequestMapping(value = "form")
	public String form(ItemSf31904KcDgDj itemSf31904KcDgDj, Model model) {
		model.addAttribute("itemSf31904KcDgDj", itemSf31904KcDgDj);
		return "modules/iemSf31904KcDgDj/itemSf31904KcDgDjForm";
	}

	@RequiresPermissions("sys:itemSf31904KcDgDj:edit")
	@RequestMapping(value = "save")
	public String save(ItemSf31904KcDgDj itemSf31904KcDgDj, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, itemSf31904KcDgDj)){
			return form(itemSf31904KcDgDj, model);
		}
		itemSf31904KcDgDjService.save(itemSf31904KcDgDj);
		addMessage(redirectAttributes, "保存SF31904卡车电工周点检卡（电气部分）成功");
		return "redirect:"+Global.getAdminPath()+"/sys/itemSf31904KcDgDj/?repage";
	}
	
	@RequiresPermissions("sys:itemSf31904KcDgDj:edit")
	@RequestMapping(value = "delete")
	public String delete(ItemSf31904KcDgDj itemSf31904KcDgDj, RedirectAttributes redirectAttributes) {
		itemSf31904KcDgDjService.delete(itemSf31904KcDgDj);
		addMessage(redirectAttributes, "删除SF31904卡车电工周点检卡（电气部分）成功");
		return "redirect:"+Global.getAdminPath()+"/sys/itemSf31904KcDgDj/?repage";
	}

}