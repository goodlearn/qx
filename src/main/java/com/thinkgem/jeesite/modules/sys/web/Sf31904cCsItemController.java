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
import com.thinkgem.jeesite.modules.sys.entity.Sf31904cCsItem;
import com.thinkgem.jeesite.modules.sys.service.Sf31904cCsItemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * SF31904C卡车点检卡Controller
 * @author wzy
 * @version 2018-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sf31904cCsItem")
public class Sf31904cCsItemController extends BaseController {

	@Autowired
	private Sf31904cCsItemService sf31904cCsItemService;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	@ModelAttribute
	public Sf31904cCsItem get(@RequestParam(required=false) String id) {
		Sf31904cCsItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sf31904cCsItemService.get(id);
		}
		if (entity == null){
			entity = new Sf31904cCsItem();
		}
		return entity;
	}
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		sf31904cCsItemService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@RequiresPermissions("sys:sf31904cCsItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(Sf31904cCsItem sf31904cCsItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Sf31904cCsItem> page = sf31904cCsItemService.findPage(new Page<Sf31904cCsItem>(request, response), sf31904cCsItem); 
		model.addAttribute("page", page);
		return "modules/sf31904ccs/sf31904cCsItemList";
	}

	@RequiresPermissions("sys:sf31904cCsItem:view")
	@RequestMapping(value = "form")
	public String form(Sf31904cCsItem sf31904cCsItem, Model model) {
		model.addAttribute("sf31904cCsItem", sf31904cCsItem);
		return "modules/sf31904ccs/sf31904cCsItemForm";
	}

	@RequiresPermissions("sys:sf31904cCsItem:edit")
	@RequestMapping(value = "save")
	public String save(Sf31904cCsItem sf31904cCsItem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sf31904cCsItem)){
			return form(sf31904cCsItem, model);
		}
		sf31904cCsItemService.save(sf31904cCsItem);
		addMessage(redirectAttributes, "保存SF31904C卡车点检卡成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sf31904cCsItem/?repage";
	}
	
	@RequiresPermissions("sys:sf31904cCsItem:edit")
	@RequestMapping(value = "delete")
	public String delete(Sf31904cCsItem sf31904cCsItem, RedirectAttributes redirectAttributes) {
		sf31904cCsItemService.delete(sf31904cCsItem);
		addMessage(redirectAttributes, "删除SF31904C卡车点检卡成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sf31904cCsItem/?repage";
	}

}