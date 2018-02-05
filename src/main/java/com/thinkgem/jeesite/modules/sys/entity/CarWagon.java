/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间车号Entity
 * @author wzy
 * @version 2018-02-05
 */
public class CarWagon extends DataEntity<CarWagon> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String carMotorCycleId;		// 所属车型
	private CarMotorCycle cmc;
	
	public CarWagon() {
		super();
	}

	public CarWagon(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属车型长度必须介于 1 和 64 之间")
	public String getCarMotorCycleId() {
		return carMotorCycleId;
	}

	public void setCarMotorCycleId(String carMotorCycleId) {
		this.carMotorCycleId = carMotorCycleId;
	}

	public CarMotorCycle getCmc() {
		return cmc;
	}

	public void setCmc(CarMotorCycle cmc) {
		this.cmc = cmc;
	}
	
}