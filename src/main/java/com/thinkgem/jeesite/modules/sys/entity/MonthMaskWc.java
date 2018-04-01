package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 班组任务月度表Entity
 * @author wzy
 * @version 2018-03-25
 */
public class MonthMaskWc extends DataEntity<MonthMaskWc> {
	
	private static final long serialVersionUID = 1L;
	private String monthMaskWsId;		// 车间任务
	private String workPersonId;		// 责任人
	private String workClassId;		// 责任班级
	private MonthMaskWs mmws;
	private String maskDesc;
	private WorkPerson wp;
	
	private List<MonthMask> mmList;//已经分配的任务

	public List<MonthMask> getMmList() {
		return mmList;
	}

	public String getWorkClassId() {
		return workClassId;
	}


	public void setWorkClassId(String workClassId) {
		this.workClassId = workClassId;
	}


	public void setMmList(List<MonthMask> mmList) {
		this.mmList = mmList;
	}

	public String getMaskDesc() {
		return maskDesc;
	}

	public void setMaskDesc(String maskDesc) {
		this.maskDesc = maskDesc;
	}

	public WorkPerson getWp() {
		return wp;
	}

	public void setWp(WorkPerson wp) {
		this.wp = wp;
	}

	public MonthMaskWs getMmws() {
		return mmws;
	}

	public void setMmws(MonthMaskWs mmws) {
		this.mmws = mmws;
	}

	public MonthMaskWc() {
		super();
	}

	public MonthMaskWc(String id){
		super(id);
	}

	@Length(min=1, max=64, message="车间任务长度必须介于 1 和 64 之间")
	public String getMonthMaskWsId() {
		return monthMaskWsId;
	}

	public void setMonthMaskWsId(String monthMaskWsId) {
		this.monthMaskWsId = monthMaskWsId;
	}

	public String getWorkPersonId() {
		return workPersonId;
	}

	public void setWorkPersonId(String workPersonId) {
		this.workPersonId = workPersonId;
	}
	
	
}