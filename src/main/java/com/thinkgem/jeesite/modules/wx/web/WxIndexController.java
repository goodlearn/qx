package com.thinkgem.jeesite.modules.wx.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.service.WorkPersonService;
import com.thinkgem.jeesite.modules.sys.service.WorkShopMaskService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

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
		String empNo = request.getParameter("empNo");
		if(null == empNo) {
			model.addAttribute("message",ERR_EMP_NO_NULL);
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
			return monitorProcess(model,loginPerson);
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}
	
	//班长级别信息处理
	private String monitorProcess(Model model,WorkPerson loginPerson) {
		model.addAttribute("fullName",loginPerson.getFullName());
		model.addAttribute("userName",loginPerson.getName());
		
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
