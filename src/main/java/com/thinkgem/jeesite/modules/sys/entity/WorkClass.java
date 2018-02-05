/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间班组Entity
 * @author wzy
 * @version 2018-02-05
 */
public class WorkClass extends DataEntity<WorkClass> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 班组名称
	private String workKindId;		// work_kind_id
	private WorkKind workKind;
	
	public WorkClass() {
		super();
	}

	public WorkClass(String id){
		super(id);
	}

	@Length(min=1, max=100, message="班组名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="work_kind_id长度必须介于 1 和 64 之间")
	public String getWorkKindId() {
		return workKindId;
	}

	public void setWorkKindId(String workKindId) {
		this.workKindId = workKindId;
	}

	public WorkKind getWorkKind() {
		return workKind;
	}

	public void setWorkKind(WorkKind workKind) {
		this.workKind = workKind;
	}
	
}