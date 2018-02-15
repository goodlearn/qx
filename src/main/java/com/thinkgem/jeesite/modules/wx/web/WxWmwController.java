package com.thinkgem.jeesite.modules.wx.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * WsMaskWc 任务处理
 * @author wzy
 *
 */
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.maskdispatch.MdControl;
import com.thinkgem.jeesite.modules.sys.service.BusinessAssembleService;
import com.thinkgem.jeesite.modules.sys.service.MaskDispatchService;
import com.thinkgem.jeesite.modules.sys.service.Sf31904cCsItemService;
import com.thinkgem.jeesite.modules.sys.service.WorkPersonService;
import com.thinkgem.jeesite.modules.sys.service.WorkShopMaskService;
import com.thinkgem.jeesite.modules.sys.service.WsMaskWcService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;
@Controller
@RequestMapping(value = "wmw")
public class WxWmwController extends WxBaseController{

	@Autowired
	private WsMaskWcService wsMaskWcService;
	
	@Autowired
	private WorkShopMaskService workShopMaskService;
	
	@Autowired
	private MaskDispatchService maskDispatchService;
	
	@Autowired
	private WorkPersonService workPersonService;
	
	@Autowired
	private BusinessAssembleService businessAssembleService;
	
	@Autowired
	private Sf31904cCsItemService sf31904cCsItemService;
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= RequestMethod.POST)
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		
		String empNo = findEmpNo();
		if(null == empNo) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		if(null == workPersonService.findByEmpNo(empNo)) {
			return backJsonWithCode(errCode,ERR_WP_NULL);
		}
		
		//根据任务获取对应的业务对象
		String maskId = viewMcsi1s[0].getMaskId();
		WsMaskWc wsMaskWc = wsMaskWcService.get(maskId);
		String workShopMaskId = wsMaskWc.getWorkShopMaskId();
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskService.get(workShopMaskId);
		//找到业务集号
		String bussinessAssembleId = workShopMask.getBussinessAssembleId();
		//找到业务集
		BusinessAssemble businessAssemble = businessAssembleService.get(bussinessAssembleId);
		//找到类型
		String type = businessAssemble.getType();
		
		//字典数据检验
		if(type.equals(DictUtils.getDictValue(Global.SF31904C_CS_ITEM, "bussinessType", "1"))) {
			//SF31904C卡车点检卡
			sf31904cCsItemService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}
		return backJsonWithCode(errCode,ERR_NOT_MASK_SERVICE);
	}
	
	/**
	 * 任务发布
	 */
	@RequestMapping(value="/releasePd",method=RequestMethod.GET)
	@ResponseBody
	public String releasePd(HttpServletRequest request, HttpServletResponse response,Model model) {
		//验证任务是否结束
		String wsmId = request.getParameter("wsmId");
		if(null == wsmId) {
			//任务号不存在
			return backJsonWithCode(errCode,ERR_WSM_ID_NULL);
		}
		
		if(null == wsMaskWcService.validateWsmId(wsmId)) {
			//任务不存在
			return backJsonWithCode(errCode,ERR_WSM_NULL);
		}
		
		String empNo = findEmpNo();
		if(null == empNo) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		if(null == workPersonService.findByEmpNo(empNo)) {
			return backJsonWithCode(errCode,ERR_WP_NULL);
		}
		
		String validateMsg = wsMaskWcService.validateReleasePd(wsmId);
		if(null != validateMsg) {
			//任务还未结束
			return backJsonWithCode(errCode,ERR_MASK_NOT_EXPIRED);
		}
		//发布任务
		WsMaskWc wsMaskWc = wsMaskWcService.releasePd(wsmId,UserUtils.findByEmpNo(empNo));
		
		return backJsonWithCode(successCode,wsMaskWc.getId());
	}
	
	/**
	 * 页面跳转-分配页面
	 */
	@RequestMapping(value="/pallocation",method=RequestMethod.GET)
	public String pallocation(HttpServletRequest request, HttpServletResponse response,Model model) {
		String maskId = request.getParameter("maskId");
		if(null == maskId) {
			//任务号不存在
			model.addAttribute("message",ERR_WSM_ID_NULL);
			return WX_ERROR;
		}
		
		//依据任务号找到车间任务号
		WsMaskWc wsMaskWc = wsMaskWcService.get(maskId);
		if(null == wsMaskWc) {
			//任务不存在
			model.addAttribute("message",ERR_WSM_NULL);
			return WX_ERROR;
		}
		
		String empNo = findEmpNo();
		if(null == empNo) {
			model.addAttribute("message",ERR_EMP_NO_NULL);
			return WX_ERROR;
		}
		
		if(null == workPersonService.findByEmpNo(empNo)) {
			model.addAttribute("message",ERR_WP_NULL);
			return WX_ERROR;
		}
		
		MdControl stateParam = maskDispatchService.pcMaskDispatch(maskId,model,true,empNo);
		
		//依据任务号找到车间任务号
		String workShopMaskId = wsMaskWc.getWorkShopMaskId();
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskService.get(workShopMaskId);
		model.addAttribute("wsmName",workShopMask.getName());
		return stateParam.getValue();
	}
	
}
