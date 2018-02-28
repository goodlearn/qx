package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 108T卡车660小时保养单(机械部分)Entity
 * @author wzy
 * @version 2018-02-28
 */
public class Item108t660By extends DataEntity<Item108t660By> {
	
	private static final long serialVersionUID = 1L;
	private String part;		// 部位
	private String number;		// 序号
	private String byItem;		// 保养项目
	private String byStandard;		// 保养标准
	private String byTool;		// 保养工具
	private String needTime;		// 所需工时
	private String needPerson;		// 所需人数
	private String checkResult;		// 检查结果
	private String assembleId;		// 所属业务集
	private BusinessAssemble ba;
	
	
	public BusinessAssemble getBa() {
		return ba;
	}

	public void setBa(BusinessAssemble ba) {
		this.ba = ba;
	}
	
	public Item108t660By() {
		super();
	}

	public Item108t660By(String id){
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
	
	@Length(min=1, max=255, message="保养项目长度必须介于 1 和 255 之间")
	public String getByItem() {
		return byItem;
	}

	public void setByItem(String byItem) {
		this.byItem = byItem;
	}
	
	@Length(min=0, max=255, message="保养标准长度必须介于 0 和 255 之间")
	public String getByStandard() {
		return byStandard;
	}

	public void setByStandard(String byStandard) {
		this.byStandard = byStandard;
	}
	
	@Length(min=1, max=100, message="保养工具长度必须介于 1 和 100 之间")
	public String getByTool() {
		return byTool;
	}

	public void setByTool(String byTool) {
		this.byTool = byTool;
	}
	
	@Length(min=1, max=100, message="所需工时长度必须介于 1 和 100 之间")
	public String getNeedTime() {
		return needTime;
	}

	public void setNeedTime(String needTime) {
		this.needTime = needTime;
	}
	
	@Length(min=1, max=100, message="所需人数长度必须介于 1 和 100 之间")
	public String getNeedPerson() {
		return needPerson;
	}

	public void setNeedPerson(String needPerson) {
		this.needPerson = needPerson;
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