/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 业务结果项表Entity
 * @author wzy
 * @version 2018-01-24
 */
public class BusinessResultItem extends DataEntity<BusinessResultItem> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String assembleId;		// 所属结果集
	private BusinessResultAssemble bra; //所属结果集
	
	public BusinessResultItem() {
		super();
	}

	public BusinessResultItem(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属结果集长度必须介于 1 和 64 之间")
	public String getAssembleId() {
		return assembleId;
	}

	public void setAssembleId(String assembleId) {
		this.assembleId = assembleId;
	}

	public BusinessResultAssemble getBra() {
		return bra;
	}

	public void setBra(BusinessResultAssemble bra) {
		this.bra = bra;
	}

}