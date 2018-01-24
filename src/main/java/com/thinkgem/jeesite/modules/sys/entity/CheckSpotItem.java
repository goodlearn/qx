/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 点检项Entity
 * @author wzy
 * @version 2018-01-24
 */
public class CheckSpotItem extends DataEntity<CheckSpotItem> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String assembleId;		// 所属业务集
	private String item;		// 项目
	private String part;		// 部位
	private String context;		// 检查内容
	private String resultAssembleId;		// 结果集
	private BusinessResultAssemble bra; //拥有结果集
	private BusinessAssemble ba;//所属业务集
	
	public CheckSpotItem() {
		super();
	}

	public CheckSpotItem(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属业务集长度必须介于 1 和 64 之间")
	public String getAssembleId() {
		return assembleId;
	}

	public void setAssembleId(String assembleId) {
		this.assembleId = assembleId;
	}
	
	@Length(min=1, max=100, message="项目长度必须介于 1 和 100 之间")
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	@Length(min=1, max=100, message="部位长度必须介于 1 和 100 之间")
	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}
	
	@Length(min=1, max=255, message="检查内容长度必须介于 1 和 255 之间")
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	@Length(min=1, max=64, message="结果集长度必须介于 1 和 64 之间")
	public String getResultAssembleId() {
		return resultAssembleId;
	}

	public void setResultAssembleId(String resultAssembleId) {
		this.resultAssembleId = resultAssembleId;
	}

	public BusinessResultAssemble getBra() {
		return bra;
	}

	public void setBra(BusinessResultAssemble bra) {
		this.bra = bra;
	}

	public BusinessAssemble getBa() {
		return ba;
	}

	public void setBa(BusinessAssemble ba) {
		this.ba = ba;
	}
	
	
	
}