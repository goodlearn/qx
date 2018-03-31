package com.thinkgem.jeesite.modules.wx.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.Date2Utils;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.service.MaskMainPersonService;
import com.thinkgem.jeesite.modules.sys.service.MaskSinglePersonService;
import com.thinkgem.jeesite.modules.sys.service.SysWxInfoService;
import com.thinkgem.jeesite.modules.sys.service.WorkPersonService;
import com.thinkgem.jeesite.modules.sys.service.WorkShopMaskService;
import com.thinkgem.jeesite.modules.sys.service.WsMaskWcService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.wx.view.ViewUnFinishMask;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

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
	private SysWxInfoService sysWxInfoService;
	
	
	
	//导航
	private final String NAVIGAION_1 = "任务执行";
	private final String NAVIGAION_2 = "任务发布";
	//private final String NAVIGAION_3 = "任务情况";
	private final String NAVIGAION_4 = "暂未开发";
	
	/**
	 * 用户绑定
	 */
	@RequestMapping(value="/tieInfo",method=RequestMethod.POST)
	@ResponseBody
	public String tieInfo(HttpServletRequest request, HttpServletResponse response,Model model) {
		String name = request.getParameter("desc");
		String no = request.getParameter("no");
		if(StringUtils.isEmpty(name)) {
			return backJsonWithCode(errCode,ERR_NAME_NO_NULL);
		}
		
		if(StringUtils.isEmpty(no)) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		WorkPerson workPerson = workPersonService.findByEmpNo(no);
		if(null == workPerson) {
			return backJsonWithCode(errCode,ERR_WP_NULL);
		}
		
		String wpName = workPerson.getName();
		if(!name.equals(wpName)) {
			return backJsonWithCode(errCode,ERR_NAME_NO_MATCH_NAME);
		}
		
		SysWxInfo sysWxInfo = sysWxInfoService.findWxInfoByNo(no);
		if(null != sysWxInfo) {
			Date endDate = sysWxInfo.getTieEndDate();//结束绑定日期 如果结束 需要重新绑定
			if(endDate.after(new Date())) {
				return backJsonWithCode(errCode,ERR_EXIST_WX_INFO);
			}
		}
		
		String openId = (String)model.asMap().get("openId");
		if(null == openId) {
			return backJsonWithCode(errCode,ERR_OPEN_ID_NOT_GET);
		}
		
		//保存用户
		sysWxInfoService.tieInfo(openId, no);
		return backJsonWithCode(successCode,null);
	}
	
	/**
	 * 页面跳转 -- 获取首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/indexInfo",method=RequestMethod.GET)
	public String indexInfo(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
		    openId = (String)model.asMap().get("openId");
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
		}
		/**
		 * 需要获取员工号 查询员工信息后，获得任务，因为没有连接微信，所以暂时不写
		 */
		String empNo = findEmpNo(openId);
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
			addFindDateMask(model,empNo,request);//任务列表
			return monitorProcess(model,loginPerson);
		}else if(DictUtils.getDictValue("工作人员", "workPersonLevel", "0").equals(level)) {
			addUnFinishMask(empNo,model);//添加需要处理的任务
			addFindDateMask(model,empNo,request);//任务列表
			return workPersonLevelProcess(model,loginPerson);
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}
	
	//查找时间范围内的任务
	private void addFindDateMask(Model model,String empNo,HttpServletRequest request) {
		try {
			String dateParam = request.getParameter("dateQuery");
			if(null == dateParam) {
				dateParam = CasUtils.convertDate2YMDString(new Date());
			}
			Date date = CasUtils.convertString2YMDDate(dateParam);
			String beginDate = CasUtils.convertDate2HMSString(Date2Utils.getDayStartTime(date));
			String endDate = CasUtils.convertDate2HMSString(Date2Utils.getDayEndTime(date));
			List<WsMaskWc> wmwList = wsMaskWcService.findMaskInClass(empNo, beginDate, endDate);
			if(null == wmwList || wmwList.size() == 0) {
				model.addAttribute("dateWmw","no");
			}else {
				model.addAttribute("dateWmw","yes");
				model.addAttribute("dateWmwList",wmwList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//查询未完成的任务列表
	private void addUnFinishMask(String empNo,Model model){
		
		//查看人员所在班组是否有任务
		List<WsMaskWc> wmwList = wsMaskWcService.findUnFinishMaskInClass(empNo);
		if(null == wmwList) {
			model.addAttribute("isUfMasks","no");//没有未完成的任务
			return;
		}
		
		/**
		 * 有任务的话，需要查看班组任务是否分配到个人
		 * 查询是否有MaskSinglePerson
		 */
		List<ViewUnFinishMask> viewUfmList = new ArrayList<ViewUnFinishMask>();
 		
		//遍历任务列表 找到个人任务列表
		for(WsMaskWc wsMaskWc : wmwList) {
			String wmwId = wsMaskWc.getId();
			List<MaskSinglePerson> tempMspList = findSinglePersonMasks(wmwId,empNo);//该员工分配的任务列表
			if(null!=tempMspList) {
				//如果有任务添加到列表中
				ViewUnFinishMask vufm = new ViewUnFinishMask();
				vufm.setWorkShopMaskId(wsMaskWc.getWsm().getId());//车间任务ID
				vufm.setWorkShopMaskName(wsMaskWc.getWsm().getName());//车间任务名称
				vufm.setWsMaskWcId(wmwId);//班组发布任务
				vufm.setMspList(tempMspList);//存放个人列表
				maskSinglePersonService.setPartNameForList(tempMspList, wmwId);
				viewUfmList.add(vufm);
			}
		}
		
		//是否有任务 返回到界面中
		if(viewUfmList.size() > 0) {
			model.addAttribute("isUfMasks","yes");//有未完成的任务
			model.addAttribute("processMasks",viewUfmList);//任务列表
		}else {
			model.addAttribute("isUfMasks","no");//没有未完成的任务
		}
	}
	
	
	//是否有任务信息
	private List<MaskSinglePerson> findSinglePersonMasks(String wmwId,String empNo) {
		
		List<MaskSinglePerson> retList = new ArrayList<MaskSinglePerson>();
		
		//查询审核人
		MaskMainPerson query = new MaskMainPerson();
		query.setWsMaskWcId(wmwId);//该任务中审核人任务列表条件
		
		List<MaskMainPerson> mmpList = maskMainPersonService.findList(query);
		
		//没有审核任务
		if(null == mmpList) {
			return null;//返回空值
		}
		
		//看有没有任务
		for(MaskMainPerson forEntity:mmpList) {
			String mmpId = forEntity.getId();
			retList.addAll(findSinglePersons(empNo,mmpId));//添加该员工在改审核任务下的任务列表
		}
		
		return retList;//没有审核任务
	}
	
	
	/**
	 * 该员工是否有分配任务，返回分配的任务列表
	 * @param empNo
	 * @param mmpId
	 * @return
	 */
	private List<MaskSinglePerson> findSinglePersons(String empNo,String mmpId) {
		WorkPerson wp = workPersonService.findByEmpNo(empNo);
		String wpId = wp.getId();
		//查询个人信息
		MaskSinglePerson query = new MaskSinglePerson();
		query.setMmpId(mmpId);
		query.setWorkPersonId(wpId);
		return maskSinglePersonService.findList(query);
	}
	//工作人员级别信息处理
	private String workPersonLevelProcess(Model model,WorkPerson loginPerson) {
		model.addAttribute("fullName",loginPerson.getFullName());
		model.addAttribute("userName",loginPerson.getName());
		model.addAttribute("isMonitor","no");
		/**
		 * 导航
		 */
		List<String> navigaionList = new ArrayList<String>();
		navigaionList.add(NAVIGAION_1);
		navigaionList.add(NAVIGAION_4);
		model.addAttribute("navigaionList",navigaionList);
		
		return INDEX_INFO;
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
	//	navigaionList.add(NAVIGAION_3);
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
