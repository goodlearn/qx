/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间Entity
 * @author wzy
 * @version 2018-01-24
 */
public class WorkShop extends DataEntity<WorkShop> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 车间名称
	
	public WorkShop() {
		super();
	}

	public WorkShop(String id){
		super(id);
	}

	@Length(min=1, max=100, message="车间名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}