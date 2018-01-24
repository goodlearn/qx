/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车辆信息Entity
 * @author wzy
 * @version 2018-01-24
 */
public class CarInfo extends DataEntity<CarInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 车名称
	private String motorcycle;		// 车型
	private String wagon;		// 车号
	
	public CarInfo() {
		super();
	}

	public CarInfo(String id){
		super(id);
	}

	@Length(min=1, max=100, message="车名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=100, message="车型长度必须介于 1 和 100 之间")
	public String getMotorcycle() {
		return motorcycle;
	}

	public void setMotorcycle(String motorcycle) {
		this.motorcycle = motorcycle;
	}
	
	@Length(min=1, max=100, message="车号长度必须介于 1 和 100 之间")
	public String getWagon() {
		return wagon;
	}

	public void setWagon(String wagon) {
		this.wagon = wagon;
	}
	
}