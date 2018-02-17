package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * SF31904卡车电工周点检卡（电气部分）Entity
 * @author wzy
 * @version 2018-02-17
 */
public class ItemSf31904KcDgDj extends DataEntity<ItemSf31904KcDgDj> {
	
	private static final long serialVersionUID = 1L;
	private String part;		// 部位
	private String number;		// 序号
	private String item;		// 检查项目
	private String checkStandard;		// 检查标准
	private String checkResult;		// 检查结果
	private String assembleId;		// 所属业务集
	private BusinessAssemble ba;

	public BusinessAssemble getBa() {
		return ba;
	}

	public void setBa(BusinessAssemble ba) {
		this.ba = ba;
	}

	public ItemSf31904KcDgDj() {
		super();
	}

	public ItemSf31904KcDgDj(String id){
		super(id);
	}

	@Length(min=1, max=100, message="部位长度必须介于 1 和 100 之间")
	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}
	
	@Length(min=1, max=100, message="序号长度必须介于 1 和 100 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=1, max=255, message="检查项目长度必须介于 1 和 255 之间")
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	@Length(min=0, max=255, message="检查标准长度必须介于 0 和 255 之间")
	public String getCheckStandard() {
		return checkStandard;
	}

	public void setCheckStandard(String checkStandard) {
		this.checkStandard = checkStandard;
	}
	
	@Length(min=0, max=255, message="检查结果长度必须介于 0 和 255 之间")
	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	
	@Length(min=1, max=64, message="所属业务集长度必须介于 1 和 64 之间")
	public String getAssembleId() {
		return assembleId;
	}

	public void setAssembleId(String assembleId) {
		this.assembleId = assembleId;
	}
	
}