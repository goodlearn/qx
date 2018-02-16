package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 任务内容表Entity
 * @author wzy
 * @version 2018-02-07
 */
public class MaskContent extends DataEntity<MaskContent> {
	
	private static final long serialVersionUID = 1L;
	private String mspId;		// 父任务号
	private String templateId;		// 模板号
	private String problem;		// 有无问题
	private MaskSinglePerson msp;
	
	
	
	public MaskContent() {
		super();
	}

	public MaskContent(String id){
		super(id);
	}

	
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public MaskSinglePerson getMsp() {
		return msp;
	}

	public void setMsp(MaskSinglePerson msp) {
		this.msp = msp;
	}

	@Length(min=1, max=64, message="父任务号长度必须介于 1 和 64 之间")
	public String getMspId() {
		return mspId;
	}

	public void setMspId(String mspId) {
		this.mspId = mspId;
	}
	
	@Length(min=1, max=1, message="有无问题长度必须介于 1 和 1 之间")
	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}
	
}