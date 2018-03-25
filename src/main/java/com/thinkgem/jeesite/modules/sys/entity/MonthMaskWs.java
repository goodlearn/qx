package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 月度计划车间任务Entity
 * @author wzy
 * @version 2018-03-25
 */
public class MonthMaskWs extends DataEntity<MonthMaskWs> {
	
	private static final long serialVersionUID = 1L;
	private String workKindId;		// 责任工种号
	private Date endDate;		// 结束时间
	private String maskDesc;		// 任务说明
	private String type;		// 任务类型
	private String submitState;		// 提交状态
	private WorkKind workKind;
	
	public WorkKind getWorkKind() {
		return workKind;
	}

	public void setWorkKind(WorkKind workKind) {
		this.workKind = workKind;
	}

	public MonthMaskWs() {
		super();
	}

	public MonthMaskWs(String id){
		super(id);
	}

	@Length(min=1, max=64, message="责任工种号长度必须介于 1 和 64 之间")
	public String getWorkKindId() {
		return workKindId;
	}

	public void setWorkKindId(String workKindId) {
		this.workKindId = workKindId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束时间不能为空")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=1, max=255, message="任务说明长度必须介于 1 和 255 之间")
	public String getMaskDesc() {
		return maskDesc;
	}

	public void setMaskDesc(String maskDesc) {
		this.maskDesc = maskDesc;
	}
	
	@Length(min=1, max=64, message="任务类型长度必须介于 1 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=100, message="提交状态长度必须介于 1 和 100 之间")
	public String getSubmitState() {
		return submitState;
	}

	public void setSubmitState(String submitState) {
		this.submitState = submitState;
	}
	
}