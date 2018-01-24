/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间班组Entity
 * @author wzy
 * @version 2018-01-24
 */
public class WorkClass extends DataEntity<WorkClass> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 班组名称
	private String workDepartmentId;		// 所属部门
	private WorkDepartment workDepartment;	//所属部门
	
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