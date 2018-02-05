/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间车型Entity
 * @author wzy
 * @version 2018-02-05
 */
public class CarMotorCycle extends DataEntity<CarMotorCycle> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String workWorkShopId;		// 所属车间
	private WorkShop ws;
	
	public CarMotorCycle() {
		super();
	}

	public CarMotorCycle(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属车间长度必须介于 1 和 64 之间")
	public String getWorkWorkShopId() {
		return workWorkShopId;
	}

	public void setWorkWorkShopId(String workWorkShopId) {
		this.workWorkShopId = workWorkShopId;
	}

	public WorkShop getWs() {
		return ws;
	}

	public void setWs(WorkShop ws) {
		this.ws = ws;
	}
	
	
}