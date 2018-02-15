package com.thinkgem.jeesite.modules.wx.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.service.BusinessAssembleService;
import com.thinkgem.jeesite.modules.sys.service.MaskMainPersonService;
import com.thinkgem.jeesite.modules.sys.service.MaskSinglePersonService;
import com.thinkgem.jeesite.modules.sys.service.WorkPersonService;
import com.thinkgem.jeesite.modules.sys.service.WorkShopMaskService;
import com.thinkgem.jeesite.modules.sys.service.WsMaskWcService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.wx.view.ViewUnFinishMask;

import org.springframework.ui.Model;

/**
 * 首页
 * @author Wzy
 *
 */
@Controller
@RequestMapping(value = "wi")
public class WxIndexController extends WxBaseController{

	
	@Autowired
	private WorkShopMaskService workShopMaskService;

	@Autowired
	private WorkPersonService workPersonService;
	
	@Autowired
	private WsMaskWcService wsMaskWcService;
	
	@Autowired
	private MaskMainPersonService maskMainPersonService;
	
	@Autowired
	private MaskSinglePersonService maskSinglePersonService;
	
	@Autowired
	private BusinessAssembleService businessAssembleService;
	
	//导航
	private final String NAVIGAION_1 = "任务执行";
	private final String NAVIGAION_2 = "任务发布";
	private final String NAVIGAION_3 = "任务情况";
	private final String NAVIGAION_4 = "暂未开发";
	/**
	 * 页面跳转 -- 获取首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/indexInfo",method=RequestMethod.GET)
	public String indexInfo(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		/**
		 * 需要获取员工号 查询员工信息后，获得任务，因为没有连接微信，所以暂时不写
		 */
		String empNo = findEmpNo();
		if(null == empNo) {
			model.addAttribute("message",ERR_EMP_NO_NULL);
			return WX_ERROR;
		}
		
		if(null == workPersonService.findByEmpNo(empNo)) {
			model.addAttribute("message",ERR_WP_NULL);
			return WX_ERROR;
		}
		
		//查询员工
		WorkPerson loginPerson = workPersonService.findByEmpNo(empNo);
		if(null == loginPerson) {
			model.addAttribute("message",ERR_WP_NULL);
			return WX_ERROR;
		}
		
		//员工级别
		String level = loginPerson.getLevel();
		
		//按照级别处理
		if(DictUtils.getDictValue("班长", "workPersonLevel", "2").equals(level)) {
			addUnFinishMask(empNo,model);//添加需要处理的任务
			return monitorProcess(model,loginPerson);
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}
	
	
	//查询未完成的任务列表
	private void addUnFinishMask(String empNo,Model model){
		List<WsMaskWc> wmwList = wsMaskWcService.findUnFinishMaskInClass(empNo);
		if(null == wmwList) {
			model.addAttribute("isUfMasks","no");//没有未完成的任务
			return;
		}
		
		List<ViewUnFinishMask> viewUfmList = new ArrayList<ViewUnFinishMask>();
		
		for(WsMaskWc wsMaskWc : wmwList) {
			String wmwId = wsMaskWc.getId();
			//查询审核人Id
			if(findMasks(wmwId,empNo)) {
				ViewUnFinishMask vufm = new ViewUnFinishMask();
				vufm.setWorkShopMaskId(wsMaskWc.getWsm().getId());
				vufm.setWorkShopMaskName(wsMaskWc.getWsm().getName());
				vufm.setParts(findParts(wsMaskWc));
				viewUfmList.add(vufm);
			}
		}
		
		if(viewUfmList.size() > 0) {
			model.addAttribute("isUfMasks","yes");//有未完成的任务
			model.addAttribute("processMasks",viewUfmList);
		}else {
			model.addAttribute("isUfMasks","no");//没有未完成的任务
		}
	}
	
	//获取部位信息
	private List<Dict> findParts(WsMaskWc wsMaskWc){
		//依据任务号找到车间任务号
		String workShopMaskId = wsMaskWc.getWorkShopMaskId();
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskService.get(workShopMaskId);
		//找到业务集号
		String bussinessAssembleId = workShopMask.getBussinessAssembleId();
		//找到业务集
		BusinessAssemble businessAssemble = businessAssembleService.get(bussinessAssembleId);
		//找到类型
		String type = businessAssemble.getType();
		
		if(type.equals(DictUtils.getDictValue(Global.SF31904C_CS_ITEM, "bussinessType", "1"))) {
			return DictUtils.getDictList("sf31904cCsItem");
		}	
		return null;
	}
	
	//是否有任务信息
	private boolean findMasks(String wmwId,String empNo) {
		WorkPerson wp = workPersonService.findByEmpNo(empNo);
		String wpId = wp.getId();
		
		//查询审核人
		MaskMainPerson query = new MaskMainPerson();
		query.setWorkPersonId(wpId);
		query.setWsMaskWcId(wmwId);
		
		List<MaskMainPerson> mmpList = maskMainPersonService.findList(query);
		
		//没有审核任务
		if(null == mmpList) {
			return false;//没有审核任务
		}
		
		//看有没有任务
		for(MaskMainPerson forEntity:mmpList) {
			String mmpId = forEntity.getId();
			return findSinglePersons(empNo,mmpId);
		}
		
		return false;//没有审核任务
	}
	
	//查询有审核信息
	private boolean findMainPersons(String empNo,String wmwId){

		WorkPerson wp = workPersonService.findByEmpNo(empNo);
		String wpId = wp.getId();
		
		//查询审核人
		MaskMainPerson query = new MaskMainPerson();
		query.setWorkPersonId(wpId);
		query.setWsMaskWcId(wmwId);
		
		if(null == maskMainPersonService.findList(query)) {
			return false;//没有审核任务
		}
		
		return true;//有审核任务
	}
	
	private boolean findSinglePersons(String empNo,String mmpId) {
		WorkPerson wp = workPersonService.findByEmpNo(empNo);
		String wpId = wp.getId();
		//查询个人信息
		MaskSinglePerson query = new MaskSinglePerson();
		query.setMmpId(mmpId);
		query.setWorkPersonId(wpId);
		if(null == maskSinglePersonService.findList(query)) {
			return false;
		}
		return true;
	}
	
	//班长级别信息处理
	private String monitorProcess(Model model,WorkPerson loginPerson) {
		model.addAttribute("fullName",loginPerson.getFullName());
		model.addAttribute("userName",loginPerson.getName());
		model.addAttribute("isMonitor","yes");
		/**
		 * 导航
		 */
		List<String> navigaionList = new ArrayList<String>();
		navigaionList.add(NAVIGAION_1);
		navigaionList.add(NAVIGAION_2);
		navigaionList.add(NAVIGAION_3);
		navigaionList.add(NAVIGAION_4);
		model.addAttribute("navigaionList",navigaionList);
		
		/**
		 * 任务发布列表
		 */
		//查询对象
		WorkShopMask queryWsm = new WorkShopMask();
		//发布状态
		String no = DictUtils.getDictValue("是", "yes_no", "1");
		queryWsm.setReleaseState(no);
		//班级
		queryWsm.setWorkClassId(loginPerson.getWorkClassId());
		//查询车间任务
		List<WorkShopMask> wsmList = workShopMaskService.findList(queryWsm);
		//结果
		model.addAttribute("wsmList",wsmList);
		
		return INDEX_INFO;
	}
	
}
