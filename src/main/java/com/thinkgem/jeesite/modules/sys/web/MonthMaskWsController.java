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
import com.thinkgem.jeesite.common.utils.Date2Utils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWc;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWs;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.entity.WorkKind;
import com.thinkgem.jeesite.modules.sys.service.MonthMaskWcService;
import com.thinkgem.jeesite.modules.sys.service.MonthMaskWsService;
import com.thinkgem.jeesite.modules.sys.service.WorkClassService;
import com.thinkgem.jeesite.modules.sys.service.WorkKindService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 月度计划车间任务Controller
 * @author wzy
 * @version 2018-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/monthMaskWs")
public class MonthMaskWsController extends BaseController {

	@Autowired
	private MonthMaskWsService monthMaskWsService;
	@Autowired
	private WorkKindService workKindService;
	@Autowired
	private WorkClassService workClassService;
	@Autowired
	private MonthMaskWcService monthMaskWcService;
	
	@ModelAttribute
	public MonthMaskWs get(@RequestParam(required=false) String id) {
		MonthMaskWs entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = monthMaskWsService.get(id);
		}
		if (entity == null){
			entity = new MonthMaskWs();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:monthMaskWs:view")
	@RequestMapping(value = {"listws"})
	public String listws(MonthMaskWs monthMaskWs, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MonthMaskWs> page = monthMaskWsService.findPageByEmpNo(new Page<MonthMaskWs>(request, response), monthMaskWs); 
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
	
	@RequiresPermissions("sys:monthMaskWs:view")
	@RequestMapping(value = {"list", ""})
	public String list(MonthMaskWs monthMaskWs, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MonthMaskWs> page = monthMaskWsService.findPage(new Page<MonthMaskWs>(request, response), monthMaskWs); 
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
		
		return "modules/monthMaskWs/monthMaskWsList";
	}

	@RequiresPermissions("sys:monthMaskWs:view")
	@RequestMapping(value = "form")
	public String form(MonthMaskWs monthMaskWs, Model model) {
		model.addAttribute("monthMaskWs", monthMaskWs);
		return "modules/monthMaskWs/monthMaskWsForm";
	}
	
	@RequiresPermissions("sys:monthMaskWs:edit")
	@RequestMapping(value = "endBatchForm")
	public String endBatchForm(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		String[] ids = request.getParameterValues("ids");
		if(null!=ids && ids.length>0) {
			monthMaskWsService.saveBatchEnd(ids);
		}
		addMessage(redirectAttributes, "发布成功");
		return "redirect:"+Global.getAdminPath()+"/sys/monthMaskWs/?repage";
	}
	
	/**
	 * 发布车间任务
	 * @param monthMaskWs
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:monthMaskWs:edit")
	@RequestMapping(value = "release")
	public String release(MonthMaskWs monthMaskWs, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, monthMaskWs)){
			return form(monthMaskWs, model);
		}
		
		/**
		 * 如果是发布的车间任务是所有工种，那么生成所有班组任务，只不过没有对应的责任人，责任人是在后面分配的
		 */
		String wkId = monthMaskWs.getWorkKindId();
		if(Global.ALL_WK.equals(wkId)) {
			List<WorkKind> workKinds = workKindService.findList(new WorkKind());//查询所有工种
			if(null!=workKinds && workKinds.size() > 0) {
				for(WorkKind forEntity : workKinds) {
					//查询该工种下的所有班级
					WorkClass workClass  = new WorkClass();
					workClass.setWorkKindId(forEntity.getId());
					List<WorkClass> workClasses = workClassService.findList(workClass);//查询所有班级
					//保存所有月度班组任务
					if(null!=workClasses && workClasses.size() > 0) {
						for(WorkClass forEntityWc : workClasses) {
							MonthMaskWc monthMaskWc = new MonthMaskWc();
							monthMaskWc.setMonthMaskWsId(monthMaskWs.getId());
							monthMaskWc.setWorkClassId(forEntityWc.getId());
							monthMaskWcService.save(monthMaskWc);//保存任务
						}
					}
					
				}
			}
		}else {
			//查询该工种下的所有班级
			WorkClass workClass  = new WorkClass();
			workClass.setId(wkId);
			List<WorkClass> workClasses = workClassService.findList(workClass);//查询所有班级
			//保存所有月度班组任务
			if(null!=workClasses && workClasses.size() > 0) {
				for(WorkClass forEntityWc : workClasses) {
					MonthMaskWc monthMaskWc = new MonthMaskWc();
					monthMaskWc.setMonthMaskWsId(monthMaskWs.getId());
					monthMaskWc.setWorkClassId(forEntityWc.getId());
					monthMaskWcService.save(monthMaskWc);//保存任务
				}
			}
		}
		
		
		monthMaskWs.setEndDate(Date2Utils.getEndDayOfMonth());//本月结束
		String value = DictUtils.getDictValue("是", "yes_no", "是");
		monthMaskWs.setSubmitState(value);//设置发布状态
		monthMaskWsService.save(monthMaskWs);
		addMessage(redirectAttributes, "保存月度计划车间任务成功");
		return "redirect:"+Global.getAdminPath()+"/sys/monthMaskWs/?repage";
	}

	@RequiresPermissions("sys:monthMaskWs:edit")
	@RequestMapping(value = "save")
	public String save(MonthMaskWs monthMaskWs, Model model, RedirectAttributes redirectAttributes) {
		monthMaskWs.setEndDate(Date2Utils.getEndDayOfMonth());//本月结束
		if (!beanValidator(model, monthMaskWs)){
			return form(monthMaskWs, model);
		}
		
		monthMaskWsService.save(monthMaskWs);
		addMessage(redirectAttributes, "保存月度计划车间任务成功");
		return "redirect:"+Global.getAdminPath()+"/sys/monthMaskWs/?repage";
	}
	
	@RequiresPermissions("sys:monthMaskWs:edit")
	@RequestMapping(value = "delete")
	public String delete(MonthMaskWs monthMaskWs, RedirectAttributes redirectAttributes) {
		monthMaskWsService.delete(monthMaskWs);
		addMessage(redirectAttributes, "删除月度计划车间任务成功");
		return "redirect:"+Global.getAdminPath()+"/sys/monthMaskWs/?repage";
	}

}