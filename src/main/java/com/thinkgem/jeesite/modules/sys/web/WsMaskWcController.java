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
import com.thinkgem.jeesite.modules.sys.entity.MaskContent;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.service.MaskContentService;
import com.thinkgem.jeesite.modules.sys.service.MaskMainPersonService;
import com.thinkgem.jeesite.modules.sys.service.MaskSinglePersonService;
import com.thinkgem.jeesite.modules.sys.service.WorkShopMaskService;
import com.thinkgem.jeesite.modules.sys.service.WsMaskWcService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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
	
	@Autowired
	private WorkShopMaskService workShopMaskService;
	
	@Autowired
	private MaskContentService maskContentService;
	
	@Autowired
	private MaskMainPersonService maskMainPersonService;
	
	@Autowired
	private MaskSinglePersonService maskSinglePersonService;
	
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
	@RequestMapping(value = {"detail"})
	public String detail(WsMaskWc wsMaskWc, HttpServletRequest request, HttpServletResponse response, Model model) {
		wsMaskWc = wsMaskWcService.findDetailInfo(wsMaskWc.getId());//查询详细信息
		
		if(null!=wsMaskWc) {
			List<MaskMainPerson> mmpList = wsMaskWc.getMmpList();
			for(MaskMainPerson mmp : mmpList) {
				List<MaskSinglePerson> mspList = mmp.getMspList();
				for(MaskSinglePerson msp : mspList) {
					maskSinglePersonService.setPartName(msp, wsMaskWc.getId());
					MaskContent queryMc = new MaskContent();
					queryMc.setMspId(msp.getId());;
					List<MaskContent> mcList = msp.getMcList();
					for(MaskContent mc : mcList) {
						maskContentService.setTemplateContent(wsMaskWc.getId(),mc);
					}
				}
			}
		}
		
		model.addAttribute("wsMaskWc",wsMaskWc);
		//设置部位信息
		return "modules/wsmaskwc/wsMaskWcDetailForm";
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
	 * 查找车间任务
	 * @param workShopMask
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:wsMaskWc:edit")
	@RequestMapping(value = {"wsmlist"})
	public String wsmlist(WorkShopMask workShopMask,HttpServletRequest request, HttpServletResponse response, Model model) {
		String no = DictUtils.getDictValue("是", "yes_no", "1");
		workShopMask.setReleaseState(no);//设置查询条件 只查询未发布数据
		Page<WorkShopMask> page = workShopMaskService.findPage(new Page<WorkShopMask>(request, response), workShopMask); 
		model.addAttribute("page", page);
		return "modules/wsmaskwc/wsmInwsmList";
	}
	
	/**
	 * 每日发布
	 */
	@RequiresPermissions("sys:wsMaskWc:view")
	@RequestMapping(value = {"releasePd"})
	public String releasePd(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String wsmId = request.getParameter("wsmId");
		String validateMsg = wsMaskWcService.validateReleasePd(wsmId);
		if(null != validateMsg) {
			addMessage(redirectAttributes, validateMsg);
			return "redirect:"+Global.getAdminPath()+"/sys/wsMaskWc/wsmlist/?repage";
		}
		wsMaskWcService.releasePd(wsmId,UserUtils.getUser());
		addMessage(redirectAttributes, "今日任务发布成功");
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
	
	

}