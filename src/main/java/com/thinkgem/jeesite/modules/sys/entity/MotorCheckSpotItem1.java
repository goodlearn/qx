package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 发动机点检单一Entity
 * @author wzy
 * @version 2018-02-05
 */
public class MotorCheckSpotItem1 extends DataEntity<MotorCheckSpotItem1> {
	
	private static final long serialVersionUID = 1L;
	private String part;		// 部位
	private String number;		// 序号
	private String item;		// 项目
	private String standard;		// 标准
	private String tool;		// 工具
	private String person;		// 人数
	private String assembleId;		// 所属业务集
	private BusinessAssemble ba;
	
	public MotorCheckSpotItem1() {
		super();
	}

	public MotorCheckSpotItem1(String id){
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
	
	@Length(min=1, max=255, message="项目长度必须介于 1 和 255 之间")
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	@Length(min=1, max=255, message="标准长度必须介于 1 和 255 之间")
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	@Length(min=1, max=100, message="工具长度必须介于 1 和 100 之间")
	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}
	
	@Length(min=1, max=100, message="人数长度必须介于 1 和 100 之间")
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
	
	@Length(min=1, max=64, message="所属业务集长度必须介于 1 和 64 之间")
	public String getAssembleId() {
		return assembleId;
	}

	public void setAssembleId(String assembleId) {
		this.assembleId = assembleId;
	}
	
	public BusinessAssemble getBa() {
		return ba;
	}

	public void setBa(BusinessAssemble ba) {
		this.ba = ba;
	}
	
}