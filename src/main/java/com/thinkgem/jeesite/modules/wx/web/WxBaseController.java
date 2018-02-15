package com.thinkgem.jeesite.modules.wx.web;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.DateUtils;

public abstract class WxBaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	
	//页面
	protected final String INDEX_INFO = "modules/wxp/scIndex";//首页
	protected final String TASK_PUB = "modules/wxp/taskPub";//任务分配
	//错误页面
	protected final String WX_ERROR = "modules/wxp/500";
	//错误信息
	protected final String ERR_WSM_ID_NULL = "任务号为空";
	protected final String ERR_WSM_NULL = "不存在该任务";
	protected final String ERR_EMP_NO_NULL = "员工号为空";
	protected final String ERR_WP_NULL = "不存在该员工";
	protected final String ERR_WP_LEVEL_NULL = "不存在该级别员工";
	protected final String ERR_MASK_NOT_EXPIRED = "任务还未结束";
	protected final String ERR_NOT_MASK_SERVICE = "没有任务处理对象";
	
	
	//信息
	protected final String MSG_ALLOCATION_SUCCESS = "任务分配成功";

	
	protected final String successCode = "0";//成功码
	protected final String errCode = "1";//错误码
	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;
	
	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;
	
	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
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
	
	
	/**
	 * 根据微信获取员工号
	 */
	protected String findEmpNo() {
		return "11614090";
	}
}
