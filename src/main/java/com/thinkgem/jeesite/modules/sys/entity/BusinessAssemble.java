/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 业务集Entity
 * @author wzy
 * @version 2018-01-24
 */
public class BusinessAssemble extends DataEntity<BusinessAssemble> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 业务集名称
	private String type;		// 业务集类型
	
	public BusinessAssemble() {
		super();
	}

	public BusinessAssemble(String id){
		super(id);
	}

	@Length(min=1, max=100, message="业务集名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=100, message="业务集类型长度必须介于 1 和 100 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}