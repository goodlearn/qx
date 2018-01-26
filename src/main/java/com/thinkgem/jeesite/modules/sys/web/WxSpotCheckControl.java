package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.service.WsmScStateService;
import com.thinkgem.jeesite.modules.sys.service.WxBaseService;
import com.thinkgem.jeesite.modules.sys.state.ScStateParam;

import org.springframework.ui.Model;
/**
 * 点检任务
 * @author Administrator
 *
 */

@Controller
@RequestMapping(value = "/wx/sc")
public class WxSpotCheckControl extends BaseController {
	
	@Autowired
	private WxBaseService wxBaseService;
	
	@Autowired
	private WsmScStateService wsmStateService;
	
	//点检任务页面
	private final String WX_SC_INDEX = "modules/wxp/scIndex";
	
	//获取微信对应工号
	private String findEmpNo() {
		WorkPerson wp = findWpByOpenId();
		String empNo = wp.getNo();
		System.out.println(empNo);
		return "201801241229";
	}
	
	//获取微信对应员工信息
	private WorkPerson findWpByOpenId() {
		//此处应该是微信获取，打通微信后更改
		return new WorkPerson();
	}
	
	//获取点检任务页面
	@RequestMapping(value="/scIndex",method=RequestMethod.GET)
	public String scIndex(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		
		
		
		//班级、个人姓名
		//此处应该是微信获取，打通微信后更改
		String empNo = findEmpNo();
		
		
		/*//检查班长是否分配任务
		StateParam stateParam = wsmStateService.requestWsmStatePage(empNo,model,false);
		if(StateParam.URL_ERR_CODE.equals(stateParam.getResultCode())) {
			//错误页面
			model.addAttribute("message",stateParam.getMassage());
			return stateParam.getValue();
		}
		
		WorkPerson wp = wxBaseService.findWpByEmpNo(empNo);
		WorkClass wc = wp.getWorkClass();
		
		model.addAttribute("wp",wp);
		model.addAttribute("wc",wc);*/
		return WX_SC_INDEX;
	}
	
}
