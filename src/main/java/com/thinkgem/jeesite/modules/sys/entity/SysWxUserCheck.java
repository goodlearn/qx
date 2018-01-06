/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 信信息检查表Entity
 * @author wzy
 * @version 2018-01-06
 */
public class SysWxUserCheck extends DataEntity<SysWxUserCheck> {
	
	private static final long serialVersionUID = 1L;
	private String idCard;		// 身份证号
	private String name;		// 姓名
	private String phone;		// 手机
	private String openId;		// 微信关联号
	private String state;		// 激活状态
	
	public SysWxUserCheck() {
		super();
	}

	public SysWxUserCheck(String id){
		super(id);
	}

	@Length(min=1, max=100, message="身份证号长度必须介于 1 和 100 之间")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	@Length(min=0, max=100, message="姓名长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=200, message="手机长度必须介于 1 和 200 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=1, max=100, message="微信关联号长度必须介于 1 和 100 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=1, max=100, message="激活状态长度必须介于 1 和 100 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}