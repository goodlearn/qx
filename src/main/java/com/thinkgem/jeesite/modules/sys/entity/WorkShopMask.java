/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间任务Entity
 * @author Wzy
 * @version 2018-01-24
 */
public class WorkShopMask extends DataEntity<WorkShopMask> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String carInfoId;		// 车型
	private String workShopId;		// 车间号
	private String workDepartment;		// 部门号
	private String workClassId;		// 班级号
	private String releaseState;		// 发布状态
	private String bussinessAssembleId;		// 业务集
	private CarInfo ci;		// 车型
	private WorkShop ws;		// 车间号
	private WorkDepartment wd;		// 部门号
	private WorkClass wc;		// 班级号
	private BusinessAssemble ba;		// 业务集
	
	public WorkShopMask() {
		super();
	}

	public WorkShopMask(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="车型长度必须介于 1 和 64 之间")
	public String getCarInfoId() {
		return carInfoId;
	}

	public void setCarInfoId(String carInfoId) {
		this.carInfoId = carInfoId;
	}
	
	@Length(min=1, max=64, message="车间号长度必须介于 1 和 64 之间")
	public String getWorkShopId() {
		return workShopId;
	}

	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}
	
	@Length(min=1, max=64, message="部门号长度必须介于 1 和 64 之间")
	public String getWorkDepartment() {
		return workDepartment;
	}

	public void setWorkDepartment(String workDepartment) {
		this.workDepartment = workDepartment;
	}
	
	@Length(min=1, max=64, message="班级号长度必须介于 1 和 64 之间")
	public String getWorkClassId() {
		return workClassId;
	}

	public void setWorkClassId(String workClassId) {
		this.workClassId = workClassId;
	}
	
	@Length(min=1, max=64, message="业务集长度必须介于 1 和 64 之间")
	public String getBussinessAssembleId() {
		return bussinessAssembleId;
	}

	public void setBussinessAssembleId(String bussinessAssembleId) {
		this.bussinessAssembleId = bussinessAssembleId;
	}
	
	

	public String getReleaseState() {
		return releaseState;
	}

	public void setReleaseState(String releaseState) {
		this.releaseState = releaseState;
	}

	public CarInfo getCi() {
		return ci;
	}

	public void setCi(CarInfo ci) {
		this.ci = ci;
	}

	public WorkShop getWs() {
		return ws;
	}

	public void setWs(WorkShop ws) {
		this.ws = ws;
	}

	public WorkDepartment getWd() {
		return wd;
	}

	public void setWd(WorkDepartment wd) {
		this.wd = wd;
	}

	public WorkClass getWc() {
		return wc;
	}

	public void setWc(WorkClass wc) {
		this.wc = wc;
	}

	public BusinessAssemble getBa() {
		return ba;
	}

	public void setBa(BusinessAssemble ba) {
		this.ba = ba;
	}
	
	
	
}