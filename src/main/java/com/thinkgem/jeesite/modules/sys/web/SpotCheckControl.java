package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckContentDao;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckContent;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.service.SpotCheckContentService;
import com.thinkgem.jeesite.modules.sys.service.WsmScStateService;
import com.thinkgem.jeesite.modules.sys.service.WxBaseService;
import com.thinkgem.jeesite.modules.sys.state.ScStateParam;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import org.springframework.ui.Model;
/**
 * 点检任务
 * @author Administrator
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/sys/sc")
public class SpotCheckControl extends BaseController {
	
	@Autowired
	private WxBaseService wxBaseService;
	
	@Autowired
	private WsmScStateService wsmStateService;
	
	@Autowired
	private SpotCheckContentService spotCheckContentService;
	
	public final String ERROR_URL = "modules/sys/sysError";
	
	//修改结果
	@RequiresPermissions("sys:sc:edit")
	@RequestMapping(value = {"modifyFormScc"})
	public String modifyFormScc(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		SpotCheckContent scc = wxBaseService.findSccWithBriById(id);
		model.addAttribute("scc",scc);
		model.addAttribute("brls",scc.getBriList());
		return "modules/checkspot/sccmr";
		
	}
	
	//修改结果
	@RequiresPermissions("sys:sc:edit")
	@RequestMapping(value = {"modifyResult"})
	public String modifyResult(SpotCheckContent spotCheckContent,HttpServletRequest request, HttpServletResponse response, Model model) {
		spotCheckContentService.save(spotCheckContent);
		return "redirect:"+Global.getAdminPath()+"/sys/spotCheckContent/?repage";
		
	}
	
	//添加新项
	@RequiresPermissions("sys:sc:edit")
	@RequestMapping(value = {"addFormScc"})
	public String addFormScc(HttpServletRequest request, HttpServletResponse response, Model model) {
		String scspId = request.getParameter("scspId");
		SpotCheckContent scc = new SpotCheckContent();
		scc.setScspId(scspId);
		model.addAttribute("scc",scc);
		return "modules/checkspot/addFormScc";
		
	}
	
	//添加新项
	@RequiresPermissions("sys:sc:edit")
	@RequestMapping(value = {"add"})
	public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
		String scspId = request.getParameter("scspId");
		String part = request.getParameter("part");
		String context = request.getParameter("context");
		SpotCheckContent entity = new SpotCheckContent();
		entity.setScspId(scspId);
		entity.setPart(part);
		entity.setContext(context);
		entity.setResultContent("");
		entity.setOldNew(DictUtils.getDictValue("是", "yes_no", "1"));
		spotCheckContentService.save(entity);
		return "redirect:"+Global.getAdminPath()+"/sys/spotCheckContent/?repage";

	}
	
	//车前
	@RequiresPermissions("sys:sc:view")
	@RequestMapping(value = {"front"})
	public String front(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		ScStateParam stateParam = wsmStateService.frontProcess(UserUtils.getUser().getEmpNo(),model,true);
		return stateParam.getValue();
		
	}
	
	//车中
	@RequiresPermissions("sys:sc:view")
	@RequestMapping(value = {"central"})
	public String central(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		ScStateParam stateParam = wsmStateService.centralProcess(UserUtils.getUser().getEmpNo(),model,true);
		return stateParam.getValue();
		
	}
	
	//车后
	@RequiresPermissions("sys:sc:view")
	@RequestMapping(value = {"heel"})
	public String heel(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		ScStateParam stateParam = wsmStateService.heelProcess(UserUtils.getUser().getEmpNo(),model,true);
		return stateParam.getValue();
		
	}
	
	
}
