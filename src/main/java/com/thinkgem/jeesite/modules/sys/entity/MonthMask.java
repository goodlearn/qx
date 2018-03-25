/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 月度任务表Entity
 * @author wzy
 * @version 2018-03-25
 */
public class MonthMask extends DataEntity<MonthMask> {
	
	private static final long serialVersionUID = 1L;
	private String monthMaskWcId;		// 班组任务
	private String workPersonId;		// 责任人
	private String carMotorCycleId;		// 车型
	private String carWagonId;		// 车号
	private String runTime;		// 运行时间数
	private String checkResult;		// 情况说明
	private Date checkDate;		// 检查时间
	
	private WorkPerson wp;
	private MonthMaskWc mmwc;
	private MonthMaskWs mmws;
	private CarMotorCycle cmc;
	private CarWagon cw;
	
	
	
	
	public MonthMaskWs getMmws() {
		return mmws;
	}

	public void setMmws(MonthMaskWs mmws) {
		this.mmws = mmws;
	}

	public WorkPerson getWp() {
		return wp;
	}

	public void setWp(WorkPerson wp) {
		this.wp = wp;
	}

	public MonthMaskWc getMmwc() {
		return mmwc;
	}

	public void setMmwc(MonthMaskWc mmwc) {
		this.mmwc = mmwc;
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

	public MonthMask() {
		super();
	}

	public MonthMask(String id){
		super(id);
	}

	@Length(min=1, max=64, message="班组任务长度必须介于 1 和 64 之间")
	public String getMonthMaskWcId() {
		return monthMaskWcId;
	}

	public void setMonthMaskWcId(String monthMaskWcId) {
		this.monthMaskWcId = monthMaskWcId;
	}
	
	@Length(min=1, max=64, message="责任人长度必须介于 1 和 64 之间")
	public String getWorkPersonId() {
		return workPersonId;
	}

	public void setWorkPersonId(String workPersonId) {
		this.workPersonId = workPersonId;
	}
	
	@Length(min=0, max=64, message="车型长度必须介于 0 和 64 之间")
	public String getCarMotorCycleId() {
		return carMotorCycleId;
	}

	public void setCarMotorCycleId(String carMotorCycleId) {
		this.carMotorCycleId = carMotorCycleId;
	}
	
	@Length(min=0, max=64, message="车号长度必须介于 0 和 64 之间")
	public String getCarWagonId() {
		return carWagonId;
	}

	public void setCarWagonId(String carWagonId) {
		this.carWagonId = carWagonId;
	}
	
	@Length(min=0, max=100, message="运行时间数长度必须介于 0 和 100 之间")
	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	
	@Length(min=0, max=255, message="情况说明长度必须介于 0 和 255 之间")
	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
}