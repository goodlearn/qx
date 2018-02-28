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
import com.thinkgem.jeesite.modules.sys.entity.ItemMt440Zjfq;
import com.thinkgem.jeesite.modules.sys.service.ItemMt440ZjfqService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * MT4400卡车钳工周检分区Controller
 * @author wzy
 * @version 2018-02-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/itemMt440Zjfq")
public class ItemMt440ZjfqController extends BaseController {

	@Autowired
	private ItemMt440ZjfqService itemMt440ZjfqService;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	@ModelAttribute
	public ItemMt440Zjfq get(@RequestParam(required=false) String id) {
		ItemMt440Zjfq entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = itemMt440ZjfqService.get(id);
		}
		if (entity == null){
			entity = new ItemMt440Zjfq();
		}
		return entity;
	}
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		itemMt440ZjfqService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	
	@RequiresPermissions("sys:itemMt440Zjfq:view")
	@RequestMapping(value = {"list", ""})
	public String list(ItemMt440Zjfq itemMt440Zjfq, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ItemMt440Zjfq> page = itemMt440ZjfqService.findPage(new Page<ItemMt440Zjfq>(request, response), itemMt440Zjfq); 
		model.addAttribute("page", page);
		return "modules/itemMt440Zjfq/itemMt440ZjfqList";
	}

	@RequiresPermissions("sys:itemMt440Zjfq:view")
	@RequestMapping(value = "form")
	public String form(ItemMt440Zjfq itemMt440Zjfq, Model model) {
		model.addAttribute("itemMt440Zjfq", itemMt440Zjfq);
		return "modules/itemMt440Zjfq/itemMt440ZjfqForm";
	}

	@RequiresPermissions("sys:itemMt440Zjfq:edit")
	@RequestMapping(value = "save")
	public String save(ItemMt440Zjfq itemMt440Zjfq, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, itemMt440Zjfq)){
			return form(itemMt440Zjfq, model);
		}
		itemMt440ZjfqService.save(itemMt440Zjfq);
		addMessage(redirectAttributes, "保存MT4400卡车钳工周检分区成功");
		return "redirect:"+Global.getAdminPath()+"/sys/itemMt440Zjfq/?repage";
	}
	
	@RequiresPermissions("sys:itemMt440Zjfq:edit")
	@RequestMapping(value = "delete")
	public String delete(ItemMt440Zjfq itemMt440Zjfq, RedirectAttributes redirectAttributes) {
		itemMt440ZjfqService.delete(itemMt440Zjfq);
		addMessage(redirectAttributes, "删除MT4400卡车钳工周检分区成功");
		return "redirect:"+Global.getAdminPath()+"/sys/itemMt440Zjfq/?repage";
	}

}