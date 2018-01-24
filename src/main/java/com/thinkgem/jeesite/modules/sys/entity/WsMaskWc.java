/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间任务班级关联Entity
 * @author wzy
 * @version 2018-01-24
 */
public class WsMaskWc extends DataEntity<WsMaskWc> {
	
	private static final long serialVersionUID = 1L;
	private String workShopMaskId;		// 车间任务号
	private String workClassId;		// 班级号
	private String acceptState;		// 接受状态
	
	public WsMaskWc() {
		super();
	}

	public WsMaskWc(String id){
		super(id);
	}

	@Length(min=1, max=64, message="车间任务号长度必须介于 1 和 64 之间")
	public String getWorkShopMaskId() {
		return workShopMaskId;
	}

	public void setWorkShopMaskId(String workShopMaskId) {
		this.workShopMaskId = workShopMaskId;
	}
	
	@Length(min=1, max=64, message="班级号长度必须介于 1 和 64 之间")
	public String getWorkClassId() {
		return workClassId;
	}

	public void setWorkClassId(String workClassId) {
		this.workClassId = workClassId;
	}
	
	@Length(min=1, max=100, message="接受状态长度必须介于 1 和 100 之间")
	public String getAcceptState() {
		return acceptState;
	}

	public void setAcceptState(String acceptState) {
		this.acceptState = acceptState;
	}
	
}