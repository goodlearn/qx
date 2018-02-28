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
import com.thinkgem.jeesite.modules.sys.entity.ItemQx2bMt4400Dj;
import com.thinkgem.jeesite.modules.sys.service.ItemQx2bMt4400DjService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * 汽修二班MT4400保养责任分区Controller
 * @author wzy
 * @version 2018-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/itemQx2bMt4400Dj")
public class ItemQx2bMt4400DjController extends BaseController {

	@Autowired
	private ItemQx2bMt4400DjService itemQx2bMt4400DjService;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		itemQx2bMt4400DjService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@ModelAttribute
	public ItemQx2bMt4400Dj get(@RequestParam(required=false) String id) {
		ItemQx2bMt4400Dj entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = itemQx2bMt4400DjService.get(id);
		}
		if (entity == null){
			entity = new ItemQx2bMt4400Dj();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:itemQx2bMt4400Dj:view")
	@RequestMapping(value = {"list", ""})
	public String list(ItemQx2bMt4400Dj itemQx2bMt4400Dj, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ItemQx2bMt4400Dj> page = itemQx2bMt4400DjService.findPage(new Page<ItemQx2bMt4400Dj>(request, response), itemQx2bMt4400Dj); 
		model.addAttribute("page", page);
		return "modules/itemQx2bMt4400Dj/itemQx2bMt4400DjList";
	}

	@RequiresPermissions("sys:itemQx2bMt4400Dj:view")
	@RequestMapping(value = "form")
	public String form(ItemQx2bMt4400Dj itemQx2bMt4400Dj, Model model) {
		model.addAttribute("itemQx2bMt4400Dj", itemQx2bMt4400Dj);
		return "modules/itemQx2bMt4400Dj/itemQx2bMt4400DjForm";
	}

	@RequiresPermissions("sys:itemQx2bMt4400Dj:edit")
	@RequestMapping(value = "save")
	public String save(ItemQx2bMt4400Dj itemQx2bMt4400Dj, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, itemQx2bMt4400Dj)){
			return form(itemQx2bMt4400Dj, model);
		}
		itemQx2bMt4400DjService.save(itemQx2bMt4400Dj);
		addMessage(redirectAttributes, "保存汽修二班MT4400保养责任分区成功");
		return "redirect:"+Global.getAdminPath()+"/sys/itemQx2bMt4400Dj/?repage";
	}
	
	@RequiresPermissions("sys:itemQx2bMt4400Dj:edit")
	@RequestMapping(value = "delete")
	public String delete(ItemQx2bMt4400Dj itemQx2bMt4400Dj, RedirectAttributes redirectAttributes) {
		itemQx2bMt4400DjService.delete(itemQx2bMt4400Dj);
		addMessage(redirectAttributes, "删除汽修二班MT4400保养责任分区成功");
		return "redirect:"+Global.getAdminPath()+"/sys/itemQx2bMt4400Dj/?repage";
	}

}