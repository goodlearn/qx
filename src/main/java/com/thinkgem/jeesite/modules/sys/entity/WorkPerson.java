/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间人员Entity
 * @author wzy
 * @version 2018-01-24
 */
public class WorkPerson extends DataEntity<WorkPerson> {
	
	private static final long serialVersionUID = 1L;
	private String no;	//员工编号
	private String name;		// 名称
	private String workClassId;		// 所属班组
	private String level;		// 级别
	private WorkClass workClass; //班组
	
	public WorkPerson() {
		super();
	}

	public WorkPerson(String id){
		super(id);
	}

	
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属班组长度必须介于 1 和 64 之间")
	public String getWorkClassId() {
		return workClassId;
	}

	public void setWorkClassId(String workClassId) {
		this.workClassId = workClassId;
	}
	
	@Length(min=1, max=64, message="级别长度必须介于 1 和 64 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public WorkClass getWorkClass() {
		return workClass;
	}

	public void setWorkClass(WorkClass workClass) {
		this.workClass = workClass;
	}
	
	
}