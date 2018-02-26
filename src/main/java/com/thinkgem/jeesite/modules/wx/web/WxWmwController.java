package com.thinkgem.jeesite.modules.wx.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.Item108t2000hBy;
import com.thinkgem.jeesite.modules.sys.entity.Item220DgDj;
import com.thinkgem.jeesite.modules.sys.entity.Item220tZxBy;
import com.thinkgem.jeesite.modules.sys.entity.ItemSf31904KcDgDj;
import com.thinkgem.jeesite.modules.sys.entity.MaskContent;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.Sf31904ByItem;
import com.thinkgem.jeesite.modules.sys.entity.Sf31904cCsItem;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.maskdispatch.MdControl;
import com.thinkgem.jeesite.modules.sys.service.BusinessAssembleService;
import com.thinkgem.jeesite.modules.sys.service.Item108t2000hByService;
import com.thinkgem.jeesite.modules.sys.service.Item220DgDjService;
import com.thinkgem.jeesite.modules.sys.service.Item220tZxByService;
import com.thinkgem.jeesite.modules.sys.service.ItemSf31904KcDgDjService;
import com.thinkgem.jeesite.modules.sys.service.MaskContentService;
import com.thinkgem.jeesite.modules.sys.service.MaskDispatchService;
import com.thinkgem.jeesite.modules.sys.service.MaskMainPersonService;
import com.thinkgem.jeesite.modules.sys.service.MaskSinglePersonService;
import com.thinkgem.jeesite.modules.sys.service.Sf31904ByItemService;
import com.thinkgem.jeesite.modules.sys.service.Sf31904cCsItemService;
import com.thinkgem.jeesite.modules.sys.service.WorkPersonService;
import com.thinkgem.jeesite.modules.sys.service.WorkShopMaskService;
import com.thinkgem.jeesite.modules.sys.service.WsMaskWcService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.TemplateContent;
import com.thinkgem.jeesite.modules.sys.view.ViewMaskDesc;
import com.thinkgem.jeesite.modules.sys.view.ViewMaskSubmit;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;
import com.thinkgem.jeesite.modules.wx.view.ViewUnFinishMask;

import net.sf.json.JSONArray;
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
	private MaskContentService maskContentService;
	
	@Autowired
	private MaskMainPersonService maskMainPersonService;
	
	@Autowired
	private MaskSinglePersonService maskSinglePersonService;
	
	
	@Autowired
	private Sf31904cCsItemService sf31904cCsItemService;
	@Autowired
	private Item220tZxByService item220tZxByService;
	@Autowired
	private Sf31904ByItemService sf31904ByItemService;
	@Autowired
	private Item220DgDjService item220DgDjService;
	@Autowired
	private ItemSf31904KcDgDjService itemSf31904KcDgDjService;
	@Autowired
	private Item108t2000hByService item108t2000hByService;
	//提交任务
	@RequestMapping(value = "utSubmit",method = RequestMethod.POST)
	@ResponseBody
	public String utSubmit(@RequestBody ViewMaskSubmit viewMaskSubmit, HttpServletRequest request, HttpServletResponse response,Model model) {
		//是否已经注册并且激活
	    String openId = (String)model.asMap().get("openId");
	    String empNo = findEmpNo(openId);
	    User user = UserUtils.findByEmpNo(empNo);
		JSONArray jsonOther = JSONArray.fromObject(viewMaskSubmit.getOtherdata());
		List<String> otherData = (List<String>)JSONArray.toCollection(jsonOther, String.class);
		System.out.println(otherData);
		JSONArray jsonCheck = JSONArray.fromObject(viewMaskSubmit.getCheckdata());
		List<ViewMaskDesc> checkData = (List<ViewMaskDesc>)JSONArray.toCollection(jsonCheck, ViewMaskDesc.class);
		System.out.println(checkData.size());
		maskSinglePersonService.submitSingleMask(user, viewMaskSubmit.getSubmitMspId(), jsonCheck, otherData);
		return backJsonWithCode(successCode,"");
	}
	
	//查询任务
	@RequestMapping(value = "wmwMask",method = RequestMethod.GET)
	public String wmwMask(HttpServletRequest request, HttpServletResponse response,Model model) {
		//是否已经注册并且激活
	    String openId = (String)model.asMap().get("openId");
		String regUrl = validateRegByOpenId(openId,model);
		if(null!=regUrl) {
			//有错误信息
			String errUrl = (String)model.asMap().get("errUrl");
			if(null != errUrl) {
				//看是否有错误
				return errUrl;
			}else {
				return regUrl;
			}
		}
		String wmwId = request.getParameter("wmwId");
		if(null == wmwId) {
			//任务不存在
			model.addAttribute("message",ERR_WSM_ID_NULL);
			return WX_ERROR;
		}
		
		WsMaskWc wsMaskWc = wsMaskWcService.get(wmwId);
		if(null == wsMaskWc) {
			//任务不存在
			model.addAttribute("message",ERR_WSM_NULL);
			return WX_ERROR;
		}
		
		MaskMainPerson query = new MaskMainPerson();
		query.setWsMaskWcId(wmwId);//该任务中审核人任务列表条件
		
		List<MaskMainPerson> mmpList = maskMainPersonService.findList(query);
		//没有审核任务
		if(null == mmpList) {
			model.addAttribute("message",ERR_MSP_LIST_NULL);
			return WX_ERROR;
		}
		List<MaskSinglePerson> retList = new ArrayList<MaskSinglePerson>();
		//看有没有任务
		for(MaskMainPerson forEntity:mmpList) {
			String mmpId = forEntity.getId();
			MaskSinglePerson queryMsp = new MaskSinglePerson();
			queryMsp.setMmpId(mmpId);
			List<MaskSinglePerson> mspList = maskSinglePersonService.findList(queryMsp);
			if(null != mspList) {
				for(MaskSinglePerson msp : mspList) {
					String mspId = msp.getId();
					MaskContent mcQuery = new MaskContent();
					mcQuery.setMspId(mspId);
					List<MaskContent> mcList = maskContentService.findList(mcQuery);
					for(MaskContent mc : mcList) {
						setTemplateContent(wmwId,mc);
					}
					msp.setMcList(mcList);
				}
				retList.addAll(mspList);//添加该员工在改审核任务下的任务列表
			}
		}
		
		if(retList.size() == 0) {
			model.addAttribute("message",ERR_NOT_MASK_LIST);
			return WX_ERROR;
		}
		
		ViewUnFinishMask vufm = new ViewUnFinishMask();
		vufm.setWorkShopMaskId(wsMaskWc.getWsm().getId());//车间任务ID
		vufm.setWorkShopMaskName(wsMaskWc.getWsm().getName());//车间任务名称
		vufm.setWsMaskWcId(wmwId);//班组发布任务
		vufm.setMspList(retList);//存放个人列表
		maskSinglePersonService.setPartNameForList(retList, wmwId);
		model.addAttribute("maskInfo",vufm);//任务列表
		return TASK_INFO;
	}
	
	//查询任务
	@RequestMapping(value = "csp",method = RequestMethod.GET)
	public String csp(HttpServletRequest request, HttpServletResponse response,Model model) {
		//是否已经注册并且激活
	    String openId = (String)model.asMap().get("openId");
		String regUrl = validateRegByOpenId(openId,model);
		if(null!=regUrl) {
			//有错误信息
			String errUrl = (String)model.asMap().get("errUrl");
			if(null != errUrl) {
				//看是否有错误
				return errUrl;
			}else {
				return regUrl;
			}
		}
		String wmwId = request.getParameter("wmwId");
		if(null == wmwId) {
			//任务不存在
			model.addAttribute("message",ERR_WSM_ID_NULL);
			return WX_ERROR;
		}
		
		WsMaskWc wsMaskWc = wsMaskWcService.get(wmwId);
		if(null == wsMaskWc) {
			//任务不存在
			model.addAttribute("message",ERR_WSM_NULL);
			return WX_ERROR;
		}
		
		MaskMainPerson query = new MaskMainPerson();
		query.setWsMaskWcId(wmwId);//该任务中审核人任务列表条件
		
		List<MaskMainPerson> mmpList = maskMainPersonService.findList(query);
		//没有审核任务
		if(null == mmpList) {
			model.addAttribute("message",ERR_MSP_LIST_NULL);
			return WX_ERROR;
		}
		List<MaskSinglePerson> retList = new ArrayList<MaskSinglePerson>();
		//看有没有任务
		for(MaskMainPerson forEntity:mmpList) {
			String mmpId = forEntity.getId();
			MaskSinglePerson queryMsp = new MaskSinglePerson();
			queryMsp.setMmpId(mmpId);
			List<MaskSinglePerson> mspList = maskSinglePersonService.findList(queryMsp);
			if(null != mspList) {
				for(MaskSinglePerson msp : mspList) {
					String mspId = msp.getId();
					MaskContent mcQuery = new MaskContent();
					mcQuery.setMspId(mspId);
					List<MaskContent> mcList = maskContentService.findList(mcQuery);
					for(MaskContent mc : mcList) {
						setTemplateContent(wmwId,mc);
					}
					msp.setMcList(mcList);
				}
				retList.addAll(mspList);//添加该员工在改审核任务下的任务列表
			}
		}
		
		if(retList.size() == 0) {
			model.addAttribute("message",ERR_NOT_MASK_LIST);
			return WX_ERROR;
		}
		
		ViewUnFinishMask vufm = new ViewUnFinishMask();
		vufm.setWorkShopMaskId(wsMaskWc.getWsm().getId());//车间任务ID
		vufm.setWorkShopMaskName(wsMaskWc.getWsm().getName());//车间任务名称
		vufm.setWsMaskWcId(wmwId);//班组发布任务
		vufm.setMspList(retList);//存放个人列表
		maskSinglePersonService.setPartNameForList(retList, wmwId);
		model.addAttribute("maskInfo",vufm);//任务列表
		return CHECK_SUBMIT;
	}
	
	/**
	 * 根据任务号填充模板信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	private void setTemplateContent(String wsMaskWcId,MaskContent maskContent) {
		String type = businessAssembleService.findBaType(wsMaskWcId);
		if(type.equals(DictUtils.getDictValue(Global.SF31904C_CS_ITEM, "bussinessType", "1"))) {
			String templateId = maskContent.getTemplateId();
			Sf31904cCsItem sf31904cCsItem = sf31904cCsItemService.get(templateId);
			TemplateContent templateContent = new TemplateContent();
			templateContent.setItem(sf31904cCsItem.getItem());
			maskContent.setTc(templateContent);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_ZX_BY, "bussinessType", "1"))) {
			String templateId = maskContent.getTemplateId();
			Item220tZxBy item220tZxBy = item220tZxByService.get(templateId);
			TemplateContent templateContent = new TemplateContent();
			templateContent.setItem(item220tZxBy.getItem());
			maskContent.setTc(templateContent);
		}else if(type.equals(DictUtils.getDictValue(Global.SF31904C_BY_ITEM, "bussinessType", "1"))) {
			String templateId = maskContent.getTemplateId();
			Sf31904ByItem sf31904ByItem = sf31904ByItemService.get(templateId);
			TemplateContent templateContent = new TemplateContent();
			templateContent.setItem(sf31904ByItem.getItem());
			maskContent.setTc(templateContent);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_DG_DJ_BY, "bussinessType", "1"))) {
			String templateId = maskContent.getTemplateId();
			Item220DgDj item220DgDj = item220DgDjService.get(templateId);
			TemplateContent templateContent = new TemplateContent();
			templateContent.setItem(item220DgDj.getCheckStandard());
			maskContent.setTc(templateContent);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_SF31904_KC_DG_DJ, "bussinessType", "1"))) {
			String templateId = maskContent.getTemplateId();
			ItemSf31904KcDgDj itemSf31904KcDgDj = itemSf31904KcDgDjService.get(templateId);
			TemplateContent templateContent = new TemplateContent();
			templateContent.setItem(itemSf31904KcDgDj.getCheckStandard());
			maskContent.setTc(templateContent);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_2000H_BY, "bussinessType", "1"))) {
			String templateId = maskContent.getTemplateId();
			Item108t2000hBy item108t2000hBy = item108t2000hByService.get(templateId);
			TemplateContent templateContent = new TemplateContent();
			templateContent.setItem(item108t2000hBy.getByItem());
			maskContent.setTc(templateContent);
		}
		
	}
	
	//查询任务
	@RequestMapping(value = "mcList",method = RequestMethod.GET)
	public String mcList(HttpServletRequest request, HttpServletResponse response,Model model) {
		//是否已经注册并且激活
	   /* String openId = (String)model.asMap().get("openId");
		String regUrl = validateRegByOpenId(openId,model);
		if(null!=regUrl) {
			//有错误信息
			String errUrl = (String)model.asMap().get("errUrl");
			if(null != errUrl) {
				//看是否有错误也爱你
				return errUrl;
			}else {
				return regUrl;
			}
		}*/
		String mspId = request.getParameter("mspId");
		if(null == mspId) {
			//任务不存在
			model.addAttribute("message",ERR_MSP_ID_NULL);
			return WX_ERROR;
		}
		
		List<MaskContent> mcList = maskContentService.findMsListByMspId(mspId);
		if(null == mcList || mcList.size() == 0) {
			model.addAttribute("message",ERR_MSP_LIST_NULL);
			return WX_ERROR;
		}
		MaskSinglePerson msp = maskSinglePersonService.get(mspId);
		MaskMainPerson mmp = maskMainPersonService.get(msp.getMmpId());
		WsMaskWc wmw = wsMaskWcService.get(mmp.getWsMaskWcId());
		WorkShopMask wsm = workShopMaskService.get(wmw.getWorkShopMaskId());
		maskSinglePersonService.setPartName(msp, wmw.getId());
		for(MaskContent mc : mcList) {
			setTemplateContent(wmw.getId(),mc);
		}
		model.addAttribute("maskName",wsm.getName());
		model.addAttribute("msp",msp);
		model.addAttribute("mcList",mcList);
		return USER_TASK;
	}
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= RequestMethod.POST)
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s,Model model) {
		String openId = (String)model.asMap().get("openId");
		String empNo = findEmpNo(openId);
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
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_ZX_BY, "bussinessType", "1"))) {
			//220T自卸卡车保养单（电气部分）
			item220tZxByService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.SF31904C_BY_ITEM, "bussinessType", "1"))) {
			//SF31904卡车保养单（电气部分）
			sf31904ByItemService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_DG_DJ_BY, "bussinessType", "1"))) {
			//220T卡车电工周点检卡（电气部分）
			item220DgDjService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_SF31904_KC_DG_DJ, "bussinessType", "1"))) {
			//SF31904卡车电工周点检卡（电气部分）
			itemSf31904KcDgDjService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_2000H_BY, "bussinessType", "1"))) {
			//108T卡车2000H及以上级别保养单(机械部分)
			item108t2000hByService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
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
		//是否已经注册并且激活
	    String openId = (String)model.asMap().get("openId");
		String regUrl = validateRegByOpenId(openId,model);
		if(null!=regUrl) {
			//有错误信息
			String errUrl = (String)model.asMap().get("errUrl");
			if(null != errUrl) {
				//看是否有错误也爱你
				return errUrl;
			}else {
				return regUrl;
			}
		}
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
		
		String empNo = findEmpNo(openId);
		if(null == empNo) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		if(null == workPersonService.findByEmpNo(empNo)) {
			return backJsonWithCode(errCode,ERR_WP_NULL);
		}
		
		String validateMsg = wsMaskWcService.validateReleasePd(wsmId);
		if(null != validateMsg) {
			//任务还未结束
			return backJsonWithCode(errCode,validateMsg);
		}
		//发布任务
		WsMaskWc wsMaskWc = wsMaskWcService.releasePd(wsmId,UserUtils.findByEmpNo(empNo));
		
		return backJsonWithCode(successCode,wsMaskWc.getId());
	}
	
	/**
	 * 任务发布
	 */
	@RequestMapping(value="/submitMask",method=RequestMethod.POST)
	@ResponseBody
	public String submitMask(HttpServletRequest request, HttpServletResponse response,Model model) {
		//是否已经注册并且激活
	    String openId = (String)model.asMap().get("openId");
		String regUrl = validateRegByOpenId(openId,model);
		if(null!=regUrl) {
			//有错误信息
			String errUrl = (String)model.asMap().get("errUrl");
			if(null != errUrl) {
				//看是否有错误也爱你
				return errUrl;
			}else {
				return regUrl;
			}
		}
		//验证任务是否结束
		String wmwId = request.getParameter("wmwId");
		if(null == wmwId) {
			//任务号不存在
			return backJsonWithCode(errCode,ERR_WSM_ID_NULL);
		}
		
		if(null == wsMaskWcService.validateWsmId(wmwId)) {
			//任务不存在
			return backJsonWithCode(errCode,ERR_WSM_NULL);
		}
		
		String empNo = findEmpNo(openId);
		if(null == empNo) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		if(null == workPersonService.findByEmpNo(empNo)) {
			return backJsonWithCode(errCode,ERR_WP_NULL);
		}
		
		String validateMsg = wsMaskWcService.validateSubmitState(wmwId);
		if(null == validateMsg) {
			//任务还未结束
			return backJsonWithCode(errCode,ERR_MASK_SUBMIT_NULL);
		}
		//发布任务
		WsMaskWc dealWmw = wsMaskWcService.get(wmwId);
		wsMaskWcService.submitMask(dealWmw,UserUtils.findByEmpNo(empNo));
		return backJsonWithCode(successCode,wmwId);
	}
	
	
	/**
	 * 页面跳转-分配页面
	 */
	@RequestMapping(value="/pallocation",method=RequestMethod.GET)
	public String pallocation(HttpServletRequest request, HttpServletResponse response,Model model) {
		//是否已经注册并且激活
	    String openId = (String)model.asMap().get("openId");
		String regUrl = validateRegByOpenId(openId,model);
		if(null!=regUrl) {
			//有错误信息
			String errUrl = (String)model.asMap().get("errUrl");
			if(null != errUrl) {
				//看是否有错误也爱你
				return errUrl;
			}else {
				return regUrl;
			}
		}
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
		
		String empNo = findEmpNo(openId);
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
