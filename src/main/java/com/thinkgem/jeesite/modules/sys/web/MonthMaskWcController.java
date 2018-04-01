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
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWc;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWs;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.entity.WorkKind;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.service.MonthMaskWcService;
import com.thinkgem.jeesite.modules.sys.service.MonthMaskWsService;
import com.thinkgem.jeesite.modules.sys.service.WorkClassService;
import com.thinkgem.jeesite.modules.sys.service.WorkPersonService;

/**
 * 班组任务月度表Controller
 * @author wzy
 * @version 2018-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/monthMaskWc")
public class MonthMaskWcController extends BaseController {

	@Autowired
	private MonthMaskWcService monthMaskWcService;
	
	@Autowired
	private WorkPersonService workPersonService;
	@Autowired
	private WorkClassService workClassService;
	@Autowired
	private MonthMaskWsService monthMaskWsService;
	
	@ModelAttribute
	public MonthMaskWc get(@RequestParam(required=false) String id) {
		MonthMaskWc entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = monthMaskWcService.get(id);
		}
		if (entity == null){
			entity = new MonthMaskWc();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:monthMaskWc:view")
	@RequestMapping(value = {"list",""})
	public String list(MonthMaskWc monthMaskWc, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MonthMaskWc> page = monthMaskWcService.findPage(new Page<MonthMaskWc>(request, response), monthMaskWc); 
		model.addAttribute("page", page);
		return "modules/monthMaskWc/monthMaskWcList";
	}
	
	@RequiresPermissions("sys:monthMaskWc:view")
	@RequestMapping(value = {"listws"})
	public String listws(MonthMaskWc monthMaskWc, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MonthMaskWs> page = monthMaskWsService.findPageByEmpNo(new Page<MonthMaskWs>(request, response), new MonthMaskWs()); 
		model.addAttribute("page", page);
		
		//将所有工种显示
		List<MonthMaskWs> list = page.getList();
		if(null!=list && list.size()>0) {
			for(MonthMaskWs forEntity : list) {
				String wkId = forEntity.getWorkKindId();
				if(Global.ALL_WK.equals(wkId)) {
					//所有工种
					WorkKind allWk = new WorkKind();
					allWk.setName(Global.ALL_WK_NAME);
					allWk.setId(Global.ALL_WK);
					forEntity.setWorkKind(allWk);
				}
			}
		}
		
		return "modules/monthMaskWs/monthMaskWsListWs";
	}


	@RequiresPermissions("sys:monthMaskWc:view")
	@RequestMapping(value = "form")
	public String form(MonthMaskWc monthMaskWc, Model model) {
		String mmwsId = monthMaskWc.getMonthMaskWsId();
		monthMaskWc.setMonthMaskWsId(mmwsId);
		model.addAttribute("wps", workPersonService.findWpsByUser());
		model.addAttribute("monthMaskWc", monthMaskWc);
		MonthMaskWs mmws = monthMaskWsService.get(mmwsId);
		if(null!=mmws) {
			monthMaskWc.setMmws(mmws);
			model.addAttribute("monthMaskWs", mmws);
		}
		return "modules/monthMaskWc/monthMaskWcForm";
	}
	
	@RequiresPermissions("sys:monthMaskWc:view")
	@RequestMapping(value = "allocation")
	public String allocation(MonthMaskWc monthMaskWc, Model model,HttpServletRequest request) {
		String mmwsId = request.getParameter("mmwsId");
		monthMaskWc.setMonthMaskWsId(mmwsId);
		
		MonthMaskWs mmws = monthMaskWsService.get(mmwsId);
		if (null != mmws) {
			model.addAttribute("monthMaskWs", mmws);//加入任务
		}
		List<MonthMaskWc> mmwcList = monthMaskWcService.findListAllByMmc(monthMaskWc,mmwsId);//查询
		
		
		if(null != mmwcList && mmwcList.size() > 0) {
			model.addAttribute("monthMaskWc", mmwcList.get(0));//应该只有一条 如果有多条也只取一条
			if(null!=mmws) {
				monthMaskWc.setMmws(mmws);
			}
		}
		
		model.addAttribute("wps", workPersonService.findWpsByUser());
		
		return "modules/monthMaskWc/monthMaskWcAllocation";
	}
	
	@RequiresPermissions("sys:monthMaskWc:edit")
	@RequestMapping(value = "saveAllocation")
	public String saveAllocation(MonthMaskWc monthMaskWc, HttpServletRequest request,Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, monthMaskWc)){
			String mmwsId = monthMaskWc.getMonthMaskWsId();
			monthMaskWc.setMonthMaskWsId(mmwsId);
			model.addAttribute("monthMaskWc", monthMaskWc);
			model.addAttribute("wps", workPersonService.findWpsByUser());
			MonthMaskWs mmws = monthMaskWsService.get(mmwsId);
			if(null!=mmws) {
				monthMaskWc.setMmws(mmws);
				model.addAttribute("monthMaskWs", mmws);
			}
			return "modules/monthMaskWc/monthMaskWcAllocation";
		}
		
		String mmwsId = monthMaskWc.getMonthMaskWsId();
		
		if(null == mmwsId) {
			addMessage(redirectAttributes, "未关联任务");
			return "redirect:"+Global.getAdminPath()+"/sys/monthMaskWc/listws/?repage";
		}
		
		String wpId = monthMaskWc.getWorkPersonId();
		
		if(null == wpId) {
			addMessage(redirectAttributes, "未分配人员");
			return "redirect:"+Global.getAdminPath()+"/sys/monthMaskWc/listws/?repage";
		}
		
		WorkPerson wp = workPersonService.get(wpId);
		WorkClass wc = workClassService.get(wp.getWorkClassId());
		monthMaskWc.setWorkClassId(wc.getId());
		monthMaskWcService.save(monthMaskWc);
		addMessage(redirectAttributes, "保存班组任务月度表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/monthMaskWc/listws/?repage";
	}

	@RequiresPermissions("sys:monthMaskWc:edit")
	@RequestMapping(value = "save")
	public String save(MonthMaskWc monthMaskWc, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, monthMaskWc)){
			return form(monthMaskWc, model);
		}
		monthMaskWcService.save(monthMaskWc);
		addMessage(redirectAttributes, "保存班组任务月度表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/monthMaskWc/?repage";
	}
	
	@RequiresPermissions("sys:monthMaskWc:edit")
	@RequestMapping(value = "delete")
	public String delete(MonthMaskWc monthMaskWc, RedirectAttributes redirectAttributes) {
		monthMaskWcService.delete(monthMaskWc);
		addMessage(redirectAttributes, "删除班组任务月度表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/monthMaskWc/?repage";
	}

}