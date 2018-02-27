package com.thinkgem.jeesite.modules.sys.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 个人负责人任务表Entity
 * @author wzy
 * @version 2018-02-07
 */
public class MaskSinglePerson extends DataEntity<MaskSinglePerson> {
	
	private static final long serialVersionUID = 1L;
	private String mmpId;		// 父任务号
	private String workPersonId;		// 个人号
	private String submitState;		// 提交状态
	private String part;//部位
	private MaskMainPerson mmp; //所属任务
	private WorkPerson wp;
	
	private String partName;//部位名称
	private List<MaskContent> mcList;//所属任务
	private String desc;

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	public String getDesc() {
		return desc;
	}


	public String addDesc() {
		if(null == mcList || mcList.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		int count = 1;
		for(MaskContent forMc : mcList) {
			if(null == forMc.getTemplateId()) {
				sb.append(count+"、" + " " + forMc.getRemarks() + "\n");
				count++;
			}
		}
		return sb.toString();
	}


	public List<MaskContent> getMcList() {
		return mcList;
	}

	public void setMcList(List<MaskContent> mcList) {
		this.mcList = mcList;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public MaskSinglePerson() {
		super();
	}

	public MaskSinglePerson(String id){
		super(id);
	}

	@Length(min=1, max=64, message="父任务号长度必须介于 1 和 64 之间")
	public String getMmpId() {
		return mmpId;
	}

	public void setMmpId(String mmpId) {
		this.mmpId = mmpId;
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

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public MaskMainPerson getMmp() {
		return mmp;
	}

	public void setMmp(MaskMainPerson mmp) {
		this.mmp = mmp;
	}

	public WorkPerson getWp() {
		return wp;
	}

	public void setWp(WorkPerson wp) {
		this.wp = wp;
	}
	
	
}