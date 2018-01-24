/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间部门Entity
 * @author wzy
 * @version 2018-01-24
 */
public class WorkDepartment extends DataEntity<WorkDepartment> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 部门名称
	private String workShopId;		// 所属车间
	private WorkShop workShop;	//所属车间信息
	
	public WorkDepartment() {
		super();
	}

	public WorkDepartment(String id){
		super(id);
	}

	@Length(min=1, max=100, message="部门名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属车间长度必须介于 1 和 64 之间")
	public String getWorkShopId() {
		return workShopId;
	}

	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}

	public WorkShop getWorkShop() {
		return workShop;
	}

	public void setWorkShop(WorkShop workShop) {
		this.workShop = workShop;
	}

	
}