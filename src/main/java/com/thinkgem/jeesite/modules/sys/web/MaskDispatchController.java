/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

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
import com.thinkgem.jeesite.modules.sys.maskdispatch.MdControl;
import com.thinkgem.jeesite.modules.sys.maskdispatch.MdStateParam;
import com.thinkgem.jeesite.modules.sys.service.MaskDispatchService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;

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