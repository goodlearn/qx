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
import com.thinkgem.jeesite.modules.sys.entity.Sf31904ByItem;
import com.thinkgem.jeesite.modules.sys.service.Sf31904ByItemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * SF31904卡车保养单（电气部分）Controller
 * @author wzy
 * @version 2018-02-17
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sf31904ByItem")
public class Sf31904ByItemController extends BaseController {

	@Autowired
	private Sf31904ByItemService sf31904ByItemService;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	@ModelAttribute
	public Sf31904ByItem get(@RequestParam(required=false) String id) {
		Sf31904ByItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sf31904ByItemService.get(id);
		}
		if (entity == null){
			entity = new Sf31904ByItem();
		}
		return entity;
	}
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		sf31904ByItemService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@RequiresPermissions("sys:sf31904ByItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(Sf31904ByItem sf31904ByItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Sf31904ByItem> page = sf31904ByItemService.findPage(new Page<Sf31904ByItem>(request, response), sf31904ByItem); 
		model.addAttribute("page", page);
		return "modules/sf31904ByItem/sf31904ByItemList";
	}

	@RequiresPermissions("sys:sf31904ByItem:view")
	@RequestMapping(value = "form")
	public String form(Sf31904ByItem sf31904ByItem, Model model) {
		model.addAttribute("sf31904ByItem", sf31904ByItem);
		return "modules/sf31904ByItem/sf31904ByItemForm";
	}

	@RequiresPermissions("sys:sf31904ByItem:edit")
	@RequestMapping(value = "save")
	public String save(Sf31904ByItem sf31904ByItem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sf31904ByItem)){
			return form(sf31904ByItem, model);
		}
		sf31904ByItemService.save(sf31904ByItem);
		addMessage(redirectAttributes, "保存SF31904卡车保养单（电气部分）成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sf31904ByItem/?repage";
	}
	
	@RequiresPermissions("sys:sf31904ByItem:edit")
	@RequestMapping(value = "delete")
	public String delete(Sf31904ByItem sf31904ByItem, RedirectAttributes redirectAttributes) {
		sf31904ByItemService.delete(sf31904ByItem);
		addMessage(redirectAttributes, "删除SF31904卡车保养单（电气部分）成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sf31904ByItem/?repage";
	}

}