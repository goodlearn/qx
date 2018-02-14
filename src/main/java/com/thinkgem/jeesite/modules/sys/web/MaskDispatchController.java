package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.maskdispatch.MdControl;
import com.thinkgem.jeesite.modules.sys.service.MaskDispatchService;

/**
 * 任务调度Controller
 * @author wzy
 * @version 2018-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/maskDispatch")
public class MaskDispatchController extends BaseController {

	@Autowired
	private MaskDispatchService maskDispatchService;
	
	
	@RequiresPermissions("sys:wsMaskWc:edit")
	@RequestMapping(value = {"maskDispatch"})
	public String maskDispatch(HttpServletRequest request, HttpServletResponse response, Model model) {
		String maskId = request.getParameter("maskId");
		MdControl stateParam = maskDispatchService.pcMaskDispatch(maskId,model);
		return stateParam.getValue();
	}
	

}