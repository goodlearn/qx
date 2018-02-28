package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 220T卡车钳工点检分区Entity
 * @author wzy
 * @version 2018-02-28
 */
public class Item220tQgDj extends DataEntity<Item220tQgDj> {
	
	private static final long serialVersionUID = 1L;
	private String part;		// 部位
	private String checkContent;		// 检查内容
	private String assembleId;		// 所属业务集
	private BusinessAssemble ba;
	
	
	public BusinessAssemble getBa() {
		return ba;
	}

	public void setBa(BusinessAssemble ba) {
		this.ba = ba;
	}

	public Item220tQgDj() {
		super();
	}

	public Item220tQgDj(String id){
		super(id);
	}

	@Length(min=1, max=100, message="部位长度必须介于 1 和 100 之间")
	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}
	
	@Length(min=0, max=255, message="检查内容长度必须介于 0 和 255 之间")
	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	
	@Length(min=1, max=64, message="所属业务集长度必须介于 1 和 64 之间")
	public String getAssembleId() {
		return assembleId;
	}

	public void setAssembleId(String assembleId) {
		this.assembleId = assembleId;
	}
	
}