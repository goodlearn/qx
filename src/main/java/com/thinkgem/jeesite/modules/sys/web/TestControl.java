package com.thinkgem.jeesite.modules.sys.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.modules.sys.excel.item108t2000hBy.Item108t2000hByExcel;

@Controller
@RequestMapping(value = "test")
public class TestControl {

	protected final String successCode = "0";//成功码
	protected final String errCode = "1";//错误码
	
	@Autowired
	private Item108t2000hByExcel item108t2000hByExcel;
	
	@RequestMapping(value = "ce")
	public String ce(HttpServletResponse response) {
		item108t2000hByExcel.createExcel(response,null);
		return null;
	}
	
	@RequestMapping(value = "getTieInfo")
	public String getTieInfo(HttpServletResponse response) {
		return "modules/wxp/wxLogin";
	}
	
	@RequestMapping(value="/tieInfo",method=RequestMethod.POST)
	@ResponseBody
	public String tieInfo(HttpServletRequest request, HttpServletResponse response,Model model) {
		String name = request.getParameter("desc");
		String no = request.getParameter("no");
		if(StringUtils.isEmpty(name)) {
			return backJsonWithCode(errCode,"姓名不能为空");
		}
		
		if(StringUtils.isEmpty(no)) {
			return backJsonWithCode(errCode,"工号不能为空");
		}
		return backJsonWithCode(successCode,null);
	}
	
	/**
	 * 返回数据 携带检验码和参数
	 */
	protected String backJsonWithCode(String code,String message){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("message", message);
		String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
		return jsonResult;
	}
	
}
