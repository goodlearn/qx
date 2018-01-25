/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 点检卡个人负责人任务表Entity
 * @author wzy
 * @version 2018-01-25
 */
public class SpotCheckSinglePerson extends DataEntity<SpotCheckSinglePerson> {
	
	private static final long serialVersionUID = 1L;
	private String scmpId;		// 父任务号
	private String workPersonId;		// 个人号
	private String submitState;		// 提交状态
	
	public SpotCheckSinglePerson() {
		super();
	}

	public SpotCheckSinglePerson(String id){
		super(id);
	}

	@Length(min=1, max=64, message="父任务号长度必须介于 1 和 64 之间")
	public String getScmpId() {
		return scmpId;
	}

	public void setScmpId(String scmpId) {
		this.scmpId = scmpId;
	}
	
	@Length(min=1, max=64, message="个人号长度必须介于 1 和 64 之间")
	public String getWorkPersonId() {
		return workPersonId;
	}

	public void setWorkPersonId(String workPersonId) {
		this.workPersonId = workPersonId;
	}
	
	@Length(min=1, max=100, message="提交状态长度必须介于 1 和 100 之间")
	public String getSubmitState() {
		return submitState;
	}

	public void setSubmitState(String submitState) {
		this.submitState = submitState;
	}
	
}