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
import com.thinkgem.jeesite.modules.sys.entity.FitterCheckSpotItem1;
import com.thinkgem.jeesite.modules.sys.service.FitterCheckSpotItem1Service;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * 钳工周检点检卡Controller
 * @author wzy
 * @version 2018-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/fitterCheckSpotItem1")
public class FitterCheckSpotItem1Controller extends BaseController {

	@Autowired
	private FitterCheckSpotItem1Service fitterCheckSpotItem1Service;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	//失败
	private final String ERR_CODE = "1";
	
	@ModelAttribute
	public FitterCheckSpotItem1 get(@RequestParam(required=false) String id) {
		FitterCheckSpotItem1 entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fitterCheckSpotItem1Service.get(id);
		}
		if (entity == null){
			entity = new FitterCheckSpotItem1();
		}
		return entity;
	}
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		
		fitterCheckSpotItem1Service.createMask(viewMcsi1s);
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@RequiresPermissions("sys:fitterCheckSpotItem1:view")
	@RequestMapping(value = {"list", ""})
	public String list(FitterCheckSpotItem1 fitterCheckSpotItem1, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FitterCheckSpotItem1> page = fitterCheckSpotItem1Service.findPage(new Page<FitterCheckSpotItem1>(request, response), fitterCheckSpotItem1); 
		model.addAttribute("page", page);
		return "modules/fitterCheckSpotItem1/fitterCheckSpotItem1List";
	}

	@RequiresPermissions("sys:fitterCheckSpotItem1:view")
	@RequestMapping(value = "form")
	public String form(FitterCheckSpotItem1 fitterCheckSpotItem1, Model model) {
		model.addAttribute("fitterCheckSpotItem1", fitterCheckSpotItem1);
		return "modules/fitterCheckSpotItem1/fitterCheckSpotItem1Form";
	}

	@RequiresPermissions("sys:fitterCheckSpotItem1:edit")
	@RequestMapping(value = "save")
	public String save(FitterCheckSpotItem1 fitterCheckSpotItem1, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fitterCheckSpotItem1)){
			return form(fitterCheckSpotItem1, model);
		}
		fitterCheckSpotItem1Service.save(fitterCheckSpotItem1);
		addMessage(redirectAttributes, "保存钳工周检点检卡成功");
		return "redirect:"+Global.getAdminPath()+"/sys/fitterCheckSpotItem1/?repage";
	}
	
	@RequiresPermissions("sys:fitterCheckSpotItem1:edit")
	@RequestMapping(value = "delete")
	public String delete(FitterCheckSpotItem1 fitterCheckSpotItem1, RedirectAttributes redirectAttributes) {
		fitterCheckSpotItem1Service.delete(fitterCheckSpotItem1);
		addMessage(redirectAttributes, "删除钳工周检点检卡成功");
		return "redirect:"+Global.getAdminPath()+"/sys/fitterCheckSpotItem1/?repage";
	}

}