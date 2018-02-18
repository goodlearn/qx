package com.thinkgem.jeesite.modules.sys.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 总负责人任务表Entity
 * @author wzy
 * @version 2018-02-07
 */
public class MaskMainPerson extends DataEntity<MaskMainPerson> {
	
	private static final long serialVersionUID = 1L;
	private String wsMaskWcId;		// 任务号
	private String workPersonId;		// 个人号
	private String submitState;		// 提交状态
	private WsMaskWc wmw;//任务关联
	private WorkPerson wp;
	
	List<MaskSinglePerson> mspList;
	
	
	public List<MaskSinglePerson> getMspList() {
		return mspList;
	}

	public void setMspList(List<MaskSinglePerson> mspList) {
		this.mspList = mspList;
	}

	public MaskMainPerson() {
		super();
	}

	public MaskMainPerson(String id){
		super(id);
	}

	@Length(min=1, max=64, message="任务号长度必须介于 1 和 64 之间")
	public String getWsMaskWcId() {
		return wsMaskWcId;
	}

	public void setWsMaskWcId(String wsMaskWcId) {
		this.wsMaskWcId = wsMaskWcId;
	}
	
	@Length(min=1, max=64, message="个人号长度必须介于 1 和 64 之间")
	public String getWorkPersonId() {
		return workPersonId;
	}

	public void setWorkPersonId(String workPersonId) {
		this.workPersonId = workPersonId;
	}
	
	
	@Length(min=1, max=100, message="提交状态长度必须介于 1 和 100 之间")
	public String getSubmitState() {
		return submitState;
	}

	public void setSubmitState(String submitState) {
		this.submitState = submitState;
	}

	public WsMaskWc getWmw() {
		return wmw;
	}

	public void setWmw(WsMaskWc wmw) {
		this.wmw = wmw;
	}

	public WorkPerson getWp() {
		return wp;
	}

	public void setWp(WorkPerson wp) {
		this.wp = wp;
	}
	
	
}