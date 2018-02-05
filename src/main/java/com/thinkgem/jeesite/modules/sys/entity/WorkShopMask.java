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
	private String carMotorCycleId;		// 车型
	private String carWagonId;		// 车号
	private String workShopId;		// 车间号
	private String workDepartment;		// 部门号
	private String workClassId;		// 班级号
	private String releaseState;		// 发布状态
	private String bussinessAssembleId;		// 业务集
	private CarMotorCycle cmc;		// 车型
	private CarWagon cw;		// 车号
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
	
	
	@Length(min=1, max=64, message="车间号长度必须介于 1 和 64 之间")
	public String getWorkShopId() {
		return workShopId;
	}

	public String getCarMotorCycleId() {
		return carMotorCycleId;
	}

	public void setCarMotorCycleId(String carMotorCycleId) {
		this.carMotorCycleId = carMotorCycleId;
	}
	
	@Length(min=1, max=64, message="车号长度必须介于 1 和 64 之间")
	public String getCarWagonId() {
		return carWagonId;
	}

	public void setCarWagonId(String carWagonId) {
		this.carWagonId = carWagonId;
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

	
	public CarMotorCycle getCmc() {
		return cmc;
	}

	public void setCmc(CarMotorCycle cmc) {
		this.cmc = cmc;
	}

	public CarWagon getCw() {
		return cw;
	}

	public void setCw(CarWagon cw) {
		this.cw = cw;
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