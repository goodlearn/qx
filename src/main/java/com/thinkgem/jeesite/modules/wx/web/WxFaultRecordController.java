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
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.FaultRecord;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWc;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWs;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.service.FaultRecordService;
import com.thinkgem.jeesite.modules.sys.service.MaskMainPersonService;
import com.thinkgem.jeesite.modules.sys.service.MaskSinglePersonService;
import com.thinkgem.jeesite.modules.sys.service.MonthMaskWcService;
import com.thinkgem.jeesite.modules.sys.service.MonthMaskWsService;
import com.thinkgem.jeesite.modules.sys.service.SysWxInfoService;
import com.thinkgem.jeesite.modules.sys.service.WorkPersonService;
import com.thinkgem.jeesite.modules.sys.service.WorkShopMaskService;
import com.thinkgem.jeesite.modules.sys.service.WsMaskWcService;
import com.thinkgem.jeesite.modules.sys.utils.BaseInfoUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.wx.view.ViewUnFinishMask;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

/**
 * 首页
 * 
 * @author Wzy
 *
 */
@Controller
@RequestMapping(value = "wxfr")
public class WxFaultRecordController extends WxBaseController {

	@Autowired
	private WorkPersonService workPersonService;

	@Autowired
	private FaultRecordService faultRecordService;

	// 导航
	//private final String NAVIGAION_1 = "任务执行";
	//private final String NAVIGAION_2 = "任务分配";

	/**
	 * 页面跳转 -- 获取首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/indexInfo", method = RequestMethod.GET)
	public String indexInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		String openId = null;
		if (null != Global.TEST_WX_OPEN_ID) {
			// 微信测试
			openId = Global.TEST_WX_OPEN_ID;
		} else {
			// 是否已经注册并且激活
			openId = (String) model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId, model);
			if (null != regUrl) {
				// 有错误信息
				String errUrl = (String) model.asMap().get("errUrl");
				if (null != errUrl) {
					// 看是否有错误
					return errUrl;
				} else {
					return regUrl;
				}
			}
		}
		/**
		 * 需要获取员工号 查询员工信息后，获得任务，因为没有连接微信，所以暂时不写
		 */
		String empNo = findEmpNo(openId);
		if (null == empNo) {
			model.addAttribute("message", ERR_EMP_NO_NULL);
			return WX_ERROR;
		}

		if (null == workPersonService.findByEmpNo(empNo)) {
			model.addAttribute("message", ERR_WP_NULL);
			return WX_ERROR;
		}

		// 查询员工
		WorkPerson loginPerson = workPersonService.findByEmpNo(empNo);
		if (null == loginPerson) {
			model.addAttribute("message", ERR_WP_NULL);
			return WX_ERROR;
		}

		// 员工级别
		String level = loginPerson.getLevel();

		// 按照级别处理（级别暂时未划分）
		if (DictUtils.getDictValue("班长", "workPersonLevel", "2").equals(level)) {
			return frAdd(response,model,request);
		} else if (DictUtils.getDictValue("工作人员", "workPersonLevel", "0").equals(level)) {
			return frAdd(response,model,request);
		} else {
			model.addAttribute("message", ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}
	
	//保存
	@RequestMapping(value = "save",method=RequestMethod.POST)
	public String save(FaultRecord faultRecord,HttpServletResponse response,Model model,HttpServletRequest request) {
		try {
			String dateParam = request.getParameter("dateQuery");
			if(null == dateParam) {
				dateParam = CasUtils.convertDate2YMDString(new Date());
			}
			Date date = CasUtils.convertString2YMDDate(dateParam);
			faultRecord.setFaultDate(date);
			faultRecordService.save(faultRecord);
			model.addAttribute("message", MSG_FR_SUCCESS);
			return WX_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message", ERR_FRR_SAVE);
		return SUCCESS_FR_PAGE;
	}
	
	//页面跳转-获取分配页面
	@RequestMapping(value = "frAdd")
	public String frAdd(HttpServletResponse response,Model model,HttpServletRequest request) {
		String openId = null;
		if (null != Global.TEST_WX_OPEN_ID) {
			// 微信测试
			openId = Global.TEST_WX_OPEN_ID;
		} else {
			// 是否已经注册并且激活
			openId = (String) model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId, model);
			if (null != regUrl) {
				// 有错误信息
				String errUrl = (String) model.asMap().get("errUrl");
				if (null != errUrl) {
					// 看是否有错误
					return errUrl;
				} else {
					return regUrl;
				}
			}
		}
		/**
		 * 需要获取员工号 查询员工信息后，获得任务，因为没有连接微信，所以暂时不写
		 */
		String empNo = findEmpNo(openId);
		if (null == empNo) {
			model.addAttribute("message", ERR_EMP_NO_NULL);
			return WX_ERROR;
		}

		if (null == workPersonService.findByEmpNo(empNo)) {
			model.addAttribute("message", ERR_WP_NULL);
			return WX_ERROR;
		}
		
	
		model.addAttribute("dps", BaseInfoUtils.getAllDpList());//部门
		model.addAttribute("wks", BaseInfoUtils.getAllWorkKindList());//工种
		model.addAttribute("wcs", BaseInfoUtils.getAllClassList());//班级
		model.addAttribute("cmcs", BaseInfoUtils.getAllCmcList());//车型
		model.addAttribute("cws", BaseInfoUtils.getCwList());//车号
		model.addAttribute("wps", BaseInfoUtils.getAllPersonList());//总负责人
		model.addAttribute("frTypes",DictUtils.getDictList("frType"));//故障类型
		
		return ADD_FR_PAGE;
	}

}
