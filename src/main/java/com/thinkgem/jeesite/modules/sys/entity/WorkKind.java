/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间工种Entity
 * @author wzy
 * @version 2018-02-05
 */
public class WorkKind extends DataEntity<WorkKind> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 工种名称
	private String workDepartmentId;		// 所属部门
	private WorkDepartment workDepartment;
	
	public WorkKind() {
		super();
	}

	public WorkKind(String id){
		super(id);
	}

	@Length(min=1, max=100, message="工种名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属部门长度必须介于 1 和 64 之间")
	public String getWorkDepartmentId() {
		return workDepartmentId;
	}

	public void setWorkDepartmentId(String workDepartmentId) {
		this.workDepartmentId = workDepartmentId;
	}

	public WorkDepartment getWorkDepartment() {
		return workDepartment;
	}

	public void setWorkDepartment(WorkDepartment workDepartment) {
		this.workDepartment = workDepartment;
	}
	
	
}