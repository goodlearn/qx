/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间任务班级关联Entity
 * @author wzy
 * @version 2018-01-24
 */
public class WsMaskWc extends DataEntity<WsMaskWc> {
	
	private static final long serialVersionUID = 1L;
	private String workShopMaskId;		// 车间任务号
	private String workClassId;		// 班级号
	private String runTime;  //运行时间数
	private Date endDate;	// 结束日期
	private String submitState;//是否提交
	private WorkShopMask wsm; //任务
	private WorkClass wc; //班级
	
	//非数据库数据
	private String frontPerson;//前部人员
	private String centralPerson;//中部人员
	private String heelPerson;//后部人员
	
	public WsMaskWc() {
		super();
	}

	public WsMaskWc(String id){
		super(id);
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getFrontPerson() {
		return frontPerson;
	}

	public void setFrontPerson(String frontPerson) {
		this.frontPerson = frontPerson;
	}

	public String getCentralPerson() {
		return centralPerson;
	}

	public void setCentralPerson(String centralPerson) {
		this.centralPerson = centralPerson;
	}

	public String getHeelPerson() {
		return heelPerson;
	}

	public void setHeelPerson(String heelPerson) {
		this.heelPerson = heelPerson;
	}

	@Length(min=1, max=64, message="车间任务号长度必须介于 1 和 64 之间")
	public String getWorkShopMaskId() {
		return workShopMaskId;
	}

	public void setWorkShopMaskId(String workShopMaskId) {
		this.workShopMaskId = workShopMaskId;
	}
	
	@Length(min=1, max=64, message="班级号长度必须介于 1 和 64 之间")
	public String getWorkClassId() {
		return workClassId;
	}

	public void setWorkClassId(String workClassId) {
		this.workClassId = workClassId;
	}

	public WorkShopMask getWsm() {
		return wsm;
	}

	public void setWsm(WorkShopMask wsm) {
		this.wsm = wsm;
	}

	public WorkClass getWc() {
		return wc;
	}

	public void setWc(WorkClass wc) {
		this.wc = wc;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSubmitState() {
		return submitState;
	}

	public void setSubmitState(String submitState) {
		this.submitState = submitState;
	}
	
	
}