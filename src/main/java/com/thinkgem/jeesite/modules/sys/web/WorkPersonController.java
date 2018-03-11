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
import com.thinkgem.jeesite.modules.sys.service.WorkPersonService;

/**
 * 车间人员Controller
 * @author wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/workPerson")
public class WorkPersonController extends BaseController {

	@Autowired
	private WorkPersonService workPersonService;
	
	@ModelAttribute
	public WorkPerson get(@RequestParam(required=false) String id) {
		WorkPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = workPersonService.get(id);
		}
		if (entity == null){
			entity = new WorkPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:workPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(WorkPerson workPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WorkPerson> page = workPersonService.findPage(new Page<WorkPerson>(request, response), workPerson); 
		
		/**
		 * 查看是否绑定微信
		 */
		workPersonService.setWxInfoTie(page.getList());
		
		model.addAttribute("page", page);
		return "modules/workperson/workPersonList";
	}

	@RequiresPermissions("sys:workPerson:view")
	@RequestMapping(value = "form")
	public String form(WorkPerson workPerson, Model model) {
		model.addAttribute("workPerson", workPerson);
		return "modules/workperson/workPersonForm";
	}

	@RequiresPermissions("sys:workPerson:edit")
	@RequestMapping(value = "save")
	public String save(WorkPerson workPerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, workPerson)){
			return form(workPerson, model);
		}
		
		String no = workPerson.getNo();
		if(StringUtils.isEmpty(no)) {
			addMessage(redirectAttributes, "员工号不能为空");
			return "redirect:"+Global.getAdminPath()+"/sys/workPerson/?repage";
		}
		
		if(null != workPersonService.findByEmpNo(no)) {
			addMessage(redirectAttributes, "员工编号已经存在");
			return "redirect:"+Global.getAdminPath()+"/sys/workPerson/?repage";
		}
		
		workPersonService.save(workPerson);
		addMessage(redirectAttributes, "保存车间人员信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workPerson/?repage";
	}
	
	@RequiresPermissions("sys:workPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(WorkPerson workPerson, RedirectAttributes redirectAttributes) {
		workPersonService.delete(workPerson);
		addMessage(redirectAttributes, "删除车间人员信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/workPerson/?repage";
	}

}