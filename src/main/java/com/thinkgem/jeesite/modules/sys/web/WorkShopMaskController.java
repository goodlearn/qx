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
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.service.WorkShopMaskService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 车间任务Controller
 * @author Wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/workShopMask")
public class WorkShopMaskController extends BaseController {

	@Autowired
	private WorkShopMaskService workShopMaskService;
	
	@ModelAttribute
	public WorkShopMask get(@RequestParam(required=false) String id) {
		WorkShopMask entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = workShopMaskService.get(id);
		}
		if (entity == null){
			entity = new WorkShopMask();
		}
		return entity;
	}
	
	//发布页面
	@RequiresPermissions("sys:workShopMask:view")
	@RequestMapping(value = {"releaseList"})
	public String releaseList(WorkShopMask workShopMask, HttpServletRequest request, HttpServletResponse response, Model model) {
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		workShopMask.setReleaseState(no);//设置查询条件 只查询未发布数据
		Page<WorkShopMask> page = workShopMaskService.findPage(new Page<WorkShopMask>(request, response), workShopMask); 
		model.addAttribute("page", page);
		return "modules/workshopmask/wsmReleaseList";
	}
	
	//撤销发布页面
	@RequiresPermissions("sys:workShopMask:view")
	@RequestMapping(value = {"unreleaseList"})
	public String unreleaseList(WorkShopMask workShopMask, HttpServletRequest request, HttpServletResponse response, Model model) {
		String yes = DictUtils.getDictValue("是", "yes_no", "1");
		workShopMask.setReleaseState(yes);//设置查询条件 只查询发布数据
		Page<WorkShopMask> page = workShopMaskService.findPage(new Page<WorkShopMask>(request, response), workShopMask); 
		model.addAttribute("page", page);
		return "modules/workshopmask/wsmUnReleaseList";
	}
	
	@RequiresPermissions("sys:workShopMask:view")
	@RequestMapping(value = {"list", ""})
	public String list(WorkShopMask workShopMask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WorkShopMask> page = workShopMaskService.findPage(new Page<WorkShopMask>(request, response), workShopMask); 
		model.addAttribute("page", page);
		return "modules/workshopmask/workShopMaskList";
	}

	@RequiresPermissions("sys:workShopMask:view")
	@RequestMapping(value = "form")
	public String form(WorkShopMask workShopMask, Model model) {
		model.addAttribute("workShopMask", workShopMask);
		return "modules/workshopmask/workShopMaskForm";
	}
	
	//查看详细数据
	@RequiresPermissions("sys:workShopMask:view")
	@RequestMapping(value = "detalils")
	public String detalils(WorkShopMask workShopMask, Model model) {
		model.addAttribute("workShopMask", workShopMask);
		return "modules/workshopmask/wsmDetailsForm";
	}

	@RequiresPermissions("sys:workShopMask:edit")
	@RequestMapping(value = "save")
	public String save(WorkShopMask workShopMask, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, workShopMask)){
			return form(workShopMask, model);
		}
		
		//如果没有状态数据 ，采用默认值
		String releaseState = workShopMask.getReleaseState();
		if(StringUtils.isEmpty(releaseState)) {
			workShopMask.setReleaseState("0");
		}
		
		workShopMaskService.save(workShopMask);
		addMessage(redirectAttributes, "保存车间任务数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workShopMask/?repage";
	}
	
	@RequiresPermissions("sys:workShopMask:edit")
	@RequestMapping(value = "delete")
	public String delete(WorkShopMask workShopMask, RedirectAttributes redirectAttributes) {
		workShopMaskService.delete(workShopMask);
		addMessage(redirectAttributes, "删除车间任务数据成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workShopMask/?repage";
	}
	
	//发布任务
	@RequiresPermissions("sys:workShopMask:edit")
	@RequestMapping(value = "release")
	public String release(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String rediectUrl =  "redirect:"+Global.getAdminPath()+"/sys/workShopMask/releaseList?repage";
		String paramId = request.getParameter("id");
		if(StringUtils.isEmpty(paramId)) {
			addMessage(redirectAttributes, "参数为空");
			return rediectUrl;
		}
		String result = workShopMaskService.release(paramId);
		if(null == result) {
			addMessage(redirectAttributes, "保存数据失败，请检查");
			return rediectUrl;
		}
		addMessage(redirectAttributes, "发布成功");
		return rediectUrl;
	}
	
	//撤销发布任务
	@RequiresPermissions("sys:workShopMask:edit")
	@RequestMapping(value = "unrelease")
	public String unrelease(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String rediectUrl =  "redirect:"+Global.getAdminPath()+"/sys/workShopMask/unreleaseList?repage";
		String paramId = request.getParameter("id");
		if(StringUtils.isEmpty(paramId)) {
			addMessage(redirectAttributes, "参数为空");
			return rediectUrl;
		}
		String result = workShopMaskService.unrelease(paramId);
		if(null == result) {
			addMessage(redirectAttributes, "保存数据失败，请检查");
			return rediectUrl;
		}
		addMessage(redirectAttributes, "撤销成功");
		return rediectUrl;
	}
	
	
}