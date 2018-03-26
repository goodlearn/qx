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
 * 
 * @author Wzy
 *
 */
@Controller
@RequestMapping(value = "mmc")
public class WxMonthMaskController extends WxBaseController {

	@Autowired
	private WorkPersonService workPersonService;

	// 导航
	private final String NAVIGAION_1 = "任务分配";
	private final String NAVIGAION_2 = "任务执行";

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

		// 按照级别处理
		if (DictUtils.getDictValue("班长", "workPersonLevel", "2").equals(level)) {
			return workPersonLevelProcess(model, loginPerson);
		} else if (DictUtils.getDictValue("工作人员", "workPersonLevel", "0").equals(level)) {
			return workPersonLevelProcess(model, loginPerson);
		} else {
			model.addAttribute("message", ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}

	// 工作人员级别信息处理
	private String workPersonLevelProcess(Model model, WorkPerson loginPerson) {
		model.addAttribute("fullName", loginPerson.getFullName());
		model.addAttribute("userName", loginPerson.getName());
		model.addAttribute("isMonitor", "no");
		/**
		 * 导航
		 */
		List<String> navigaionList = new ArrayList<String>();
		navigaionList.add(NAVIGAION_1);
		model.addAttribute("navigaionList", navigaionList);

		return MONTH_MASK_INDEX_INFO;
	}

	// 班长级别信息处理
	private String monitorProcess(Model model, WorkPerson loginPerson) {
		model.addAttribute("fullName", loginPerson.getFullName());
		model.addAttribute("userName", loginPerson.getName());
		model.addAttribute("isMonitor", "yes");
		/**
		 * 导航
		 */
		List<String> navigaionList = new ArrayList<String>();
		navigaionList.add(NAVIGAION_1);
		navigaionList.add(NAVIGAION_2);
		model.addAttribute("navigaionList", navigaionList);

		/**
		 * 任务发布列表
		 */
		/*
		 * //查询对象 WorkShopMask queryWsm = new WorkShopMask(); //发布状态 String no =
		 * DictUtils.getDictValue("是", "yes_no", "1"); queryWsm.setReleaseState(no);
		 * //班级 queryWsm.setWorkClassId(loginPerson.getWorkClassId()); //查询车间任务
		 * List<WorkShopMask> wsmList = workShopMaskService.findList(queryWsm); //结果
		 * model.addAttribute("wsmList",wsmList);
		 */

		return MONTH_MASK_INDEX_INFO;
	}

}
