package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.modules.sys.excel.item108t2000hBy.Item108t2000hByExcel;

@Controller
@RequestMapping(value = "test")
public class TestControl {

	@Autowired
	private Item108t2000hByExcel item108t2000hByExcel;
	
	@RequestMapping(value = "ce")
	public String ce(HttpServletResponse response) {
		item108t2000hByExcel.createExcel(response,null);
		return null;
	}
	
}
