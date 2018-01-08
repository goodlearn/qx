/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.supcan.annotation.treelist.cols.SupCol;

/**
 * 微信信息表Entity
 * @author wzy
 * @version 2017-12-25
 */
public class SysWxInfo extends DataEntity<SysWxInfo> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// 微信关联号
	private String idCard;		// 身份证号
	private User user;//关联的管理员
	
	public SysWxInfo() {
		super();
	}

	public SysWxInfo(String id){
		super(id);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@SupCol(isUnique="true")
	@Length(min=1, max=100, message="微信关联号长度必须介于 1 和 100 之间")
	public String getOpenId() {
		return openId;
	}

	@SupCol(isUnique="true")
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=1, max=100, message="身份证号长度必须介于 1 和 100 之间")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
}