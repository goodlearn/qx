/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.SysExpress;
import com.thinkgem.jeesite.modules.sys.service.SysExpressService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 快递信息表Controller
 * @author wzy
 * @version 2017-12-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysExpress")
public class SysExpressController extends BaseController {

	@Autowired
	private SysExpressService sysExpressService;
	
	@ModelAttribute
	public SysExpress get(@RequestParam(required=false) String id) {
		SysExpress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysExpressService.get(id);
		}
		if (entity == null){
			entity = new SysExpress();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysExpress:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysExpress sysExpress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysExpress> page = sysExpressService.findPage(new Page<SysExpress>(request, response), sysExpress); 
		model.addAttribute("page", page);
		return "modules/sys/sysExpressList";
	}
	
	@RequiresPermissions("sys:sysExpress:view")
	@RequestMapping(value = "detailForm")
	public String detailForm(SysExpress sysExpress, Model model) {
		sysExpress = sysExpressService.findDetailExpressInfo(sysExpress);
		model.addAttribute("sysExpress", sysExpress);
		return "modules/sys/sysExpressDetailForm";
	}

	@RequiresPermissions("sys:sysExpress:view")
	@RequestMapping(value = "editForm")
	public String editForm(SysExpress sysExpress, Model model) {
		model.addAttribute("sysExpress", sysExpress);
		return "modules/sys/sysExpressEditForm";
	}
	
	
	
	@RequiresPermissions("sys:sysExpress:edit")
	@RequestMapping(value = "addForm")
	public String addForm(SysExpress sysExpress, Model model) {
		model.addAttribute("sysExpress", sysExpress);
		return "modules/sys/sysExpressAddForm";
	}
	
	//取货
	@RequiresPermissions("sys:sysExpress:edit")
	@RequestMapping(value = "endExpress")
	public String endExpress(SysExpress sysExpress, Model model) {
		if (!beanValidator(model, sysExpress)){
			return editForm(sysExpress, model);
		}
		String state = DictUtils.getDictValue("已完结", "expressState", "0");
		sysExpress.setState(state);
		sysExpressService.save(sysExpress,UserUtils.getUser());
		
		return "redirect:"+Global.getAdminPath()+"/sys/sysExpress/endFormList";
	}
	
	/**
	 * 获取已取货页面
	 * @param sysExpress
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:sysExpress:view")
	@RequestMapping(value = {"endForm", "endFormList"})
	public String endForm(SysExpress sysExpress, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		//获取搜索值
		String searchValue = request.getParameter("searchValue");
		sysExpress.setSearchUnEndValue(searchValue);
		//如果是已经完结取货的不再查询
		String state = DictUtils.getDictValue("已完结", "expressState", "0");
		sysExpress.setState(state);
		Page<SysExpress> page = sysExpressService.findUnEndPage(new Page<SysExpress>(request, response), sysExpress); 
		model.addAttribute("page", page);
		if(null!=searchValue) {
			model.addAttribute("searchValue", searchValue);
		}
		return "modules/sys/sysExpressEndForm";
	}
	
	@RequiresPermissions("sys:sysExpress:edit")
	@RequestMapping(value = "endBatchForm")
	public String endBatchForm(HttpServletRequest request, HttpServletResponse response, Model model) {
		String[] sysExpressIds = request.getParameterValues("sysExpressIds");
		if(null!=sysExpressIds&&sysExpressIds.length>0) {
			sysExpressService.saveBatchEnd(sysExpressIds);
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysExpress/endFormList";
	}

	
	//入库
	@RequiresPermissions("sys:sysExpress:edit")
	@RequestMapping(value = "save")
	public String save(SysExpress sysExpress, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysExpress)){
			return addForm(sysExpress, model);
		}
		//默认保存快递状态为已入库
		String state = DictUtils.getDictValue("已入库", "expressState", "0");
		sysExpress.setState(state);
		sysExpressService.save(sysExpress,UserUtils.getUser());
		addMessage(redirectAttributes, "快递已入库");
		return "redirect:"+Global.getAdminPath()+"/sys/sysExpress/?repage";
	}
	
	@RequiresPermissions("sys:sysExpress:edit")
	@RequestMapping(value = "edit")
	public String edit(SysExpress sysExpress, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysExpress)){
			return editForm(sysExpress, model);
		}
		sysExpressService.save(sysExpress,UserUtils.getUser());
		addMessage(redirectAttributes, "保存快递表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysExpress/?repage";
	}
	
	@RequiresPermissions("sys:sysExpress:edit")
	@RequestMapping(value = "delete")
	public String delete(SysExpress sysExpress, RedirectAttributes redirectAttributes) {
		sysExpressService.delete(sysExpress);
		addMessage(redirectAttributes, "删除快递表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysExpress/?repage";
	}

}