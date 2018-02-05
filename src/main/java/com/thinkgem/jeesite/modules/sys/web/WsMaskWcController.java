/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;

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
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.service.WsMaskWcService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 车间任务班级关联Controller
 * @author wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/wsMaskWc")
public class WsMaskWcController extends BaseController {

	@Autowired
	private WsMaskWcService wsMaskWcService;
	
	@ModelAttribute
	public WsMaskWc get(@RequestParam(required=false) String id) {
		WsMaskWc entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wsMaskWcService.get(id);
		}
		if (entity == null){
			entity = new WsMaskWc();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:wsMaskWc:view")
	@RequestMapping(value = {"list", ""})
	public String list(WsMaskWc wsMaskWc, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMaskWc> page = wsMaskWcService.findPage(new Page<WsMaskWc>(request, response), wsMaskWc); 
		model.addAttribute("page", page);
		return "modules/wsmaskwc/wsMaskWcList";
	}
	

	@RequiresPermissions("sys:wsMaskWc:view")
	@RequestMapping(value = "form")
	public String form(WsMaskWc wsMaskWc, Model model) {
		model.addAttribute("wsMaskWc", wsMaskWc);
		return "modules/wsmaskwc/wsMaskWcForm";
	}

	@RequiresPermissions("sys:wsMaskWc:edit")
	@RequestMapping(value = "save")
	public String save(WsMaskWc wsMaskWc, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMaskWc)){
			return form(wsMaskWc, model);
		}
		wsMaskWcService.save(wsMaskWc);
		addMessage(redirectAttributes, "保存车间任务班级关联数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/wsMaskWc/?repage";
	}
	
	@RequiresPermissions("sys:wsMaskWc:edit")
	@RequestMapping(value = "delete")
	public String delete(WsMaskWc wsMaskWc, RedirectAttributes redirectAttributes) {
		wsMaskWcService.delete(wsMaskWc);
		addMessage(redirectAttributes, "删除车间任务班级关联数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/wsMaskWc/?repage";
	}
	
	/**
	 * 未提交数据页面
	 * @param wsMaskWc
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:wsMaskWc:view")
	@RequestMapping(value = {"unSubmitList"})
	public String unSubmitList(WsMaskWc wsMaskWc, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WsMaskWc> page = wsMaskWcService.findUnSubmitList(new Page<WsMaskWc>(request, response), wsMaskWc); 
		model.addAttribute("page", page);
		return "modules/wsmaskwc/wmsUnSubmitList";
	}
	
	//结束任务
	@RequiresPermissions("sys:wsMaskWc:edit")
	@RequestMapping(value = "endMask")
	public String endMask(WsMaskWc wsMaskWc, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wsMaskWc)){
			return form(wsMaskWc, model);
		}
		wsMaskWcService.endMask(wsMaskWc);
		addMessage(redirectAttributes, "任务结束");
		return "redirect:"+Global.getAdminPath()+"/sys/wsMaskWc/unSubmitList?repage";
	}
	
	//保存点检任务
	@RequiresPermissions("sys:wsMaskWc:edit")
	@RequestMapping(value = "saveScMask")
	public String saveScMask(WsMaskWc wsMaskWc, RedirectAttributes redirectAttributes) {
		String redirectUrl = "redirect:"+Global.getAdminPath()+"/sys/wsMaskWc/?repage";
		wsMaskWcService.saveScMask(wsMaskWc);
		addMessage(redirectAttributes, "任务分配成功");
		return redirectUrl;
	}
	
	
	//跳转到分配页面 携带参数
	@RequiresPermissions("sys:wsMaskWc:view")
	@RequestMapping(value = "allocationPage")
	public String allocationPage(WsMaskWc wsMaskWc, HttpServletRequest request,Model model) {
		
		/**
		 * workShopMaskControl-allocation中已经检测是否有未提交的数据，此处不检查
		 */
		
		String wsmid = request.getParameter("wsmid");
		wsMaskWc = wsMaskWcService.save(wsmid);//生成任务数据
		//获取班级所有人
		List<WorkPerson> workPersons = wsMaskWcService.findWpByWsmId(wsmid);
		model.addAttribute("wp", workPersons);
		model.addAttribute("wsMaskWc",wsMaskWc);
		return "modules/wsMaskWc/wsmAllocationForm";
	}

}