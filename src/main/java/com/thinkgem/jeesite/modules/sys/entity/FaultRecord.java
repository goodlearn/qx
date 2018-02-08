package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 故障记录表Entity
 * @author wzy
 * @version 2018-02-08
 */
public class FaultRecord extends DataEntity<FaultRecord> {
	
	private static final long serialVersionUID = 1L;
	private String workDepartmentId;		// 部门
	private String workKindId;		// 工种
	private String workClassId;		// 班组
	private String carMotorCycleId;		// 车型
	private String carWagonId;		// 车号
	private String wpTotalId;		// 总负责人
	private String wpPartId;		// 参与人员
	private String runTime;		// 运行时间
	private String type;		// 故障类型
	private String description;		// 故障描述
	private String dealMethod;		// 处理方法
	private Date faultDate;		// 故障时间
	private WorkDepartment wd;
	private WorkKind wk;
	private WorkClass wc;
	private CarMotorCycle cmc;
	private CarWagon cw;
	private WorkPerson wpTotal;
	private WorkPerson wpPart;
	
	public FaultRecord() {
		super();
	}

	public FaultRecord(String id){
		super(id);
	}
	
	@ExcelField(title="所属部门", align=2, sort=1)
	public String getDepartmentName() {
		return wd.getName();
	}
	@ExcelField(title="所属工种", align=2, sort=2)
	public String getWorkKindName() {
		return wk.getName();
	}
	@ExcelField(title="所属班组", align=2, sort=3)
	public String getWorkClassName() {
		return wc.getName();
	}
	@ExcelField(title="车型", align=2, sort=4)
	public String getCarMotorCycleName() {
		return cmc.getName();
	}
	@ExcelField(title="车号", align=2, sort=5)
	public String getCarWagonName() {
		return cw.getName();
	}
	@ExcelField(title="总负责人", align=2, sort=6)
	public String getWpTotalName() {
		return wpTotal.getName();
	}
	@ExcelField(title="参与人员", align=2, sort=7)
	public String getWpPartName() {
		return wpPart.getName();
	}
	@Length(min=1, max=64, message="班组长度必须介于 1 和 64 之间")
	public String getWorkClassId() {
		return workClassId;
	}

	public void setWorkClassId(String workClassId) {
		this.workClassId = workClassId;
	}
	
	@Length(min=1, max=64, message="车型长度必须介于 1 和 64 之间")
	public String getCarMotorCycleId() {
		return carMotorCycleId;
	}

	public void setCarMotorCycleId(String carMotorCycleId) {
		this.carMotorCycleId = carMotorCycleId;
	}
	
	@Length(min=1, max=64, message="车号长度必须介于 1 和 64 之间")
	public String getCarWagonId() {
		return carWagonId;
	}

	public void setCarWagonId(String carWagonId) {
		this.carWagonId = carWagonId;
	}
	
	@Length(min=1, max=64, message="总负责人长度必须介于 1 和 64 之间")
	public String getWpTotalId() {
		return wpTotalId;
	}

	public void setWpTotalId(String wpTotalId) {
		this.wpTotalId = wpTotalId;
	}
	
	@Length(min=1, max=64, message="参与人员长度必须介于 1 和 64 之间")
	public String getWpPartId() {
		return wpPartId;
	}

	public void setWpPartId(String wpPartId) {
		this.wpPartId = wpPartId;
	}
	
	@Length(min=1, max=100, message="运行时间长度必须介于 1 和 100 之间")
	@ExcelField(title="运行时间", align=2, sort=8)
	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	
	@Length(min=1, max=100, message="故障类型长度必须介于 1 和 100 之间")
	@ExcelField(title="故障类型", align=2, sort=9)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=255, message="故障描述长度必须介于 1 和 255 之间")
	@ExcelField(title="故障描述", align=2, sort=10)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=1, max=255, message="处理方法长度必须介于 1 和 255 之间")
	@ExcelField(title="处理方法", align=2, sort=11)
	public String getDealMethod() {
		return dealMethod;
	}

	public void setDealMethod(String dealMethod) {
		this.dealMethod = dealMethod;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="故障时间不能为空")
	@ExcelField(title="故障时间", align=2, sort=12)
	public Date getFaultDate() {
		return faultDate;
	}
	

	public String getWorkDepartmentId() {
		return workDepartmentId;
	}

	public void setWorkDepartmentId(String workDepartmentId) {
		this.workDepartmentId = workDepartmentId;
	}

	public WorkDepartment getWd() {
		return wd;
	}

	public void setWd(WorkDepartment wd) {
		this.wd = wd;
	}

	public void setFaultDate(Date faultDate) {
		this.faultDate = faultDate;
	}

	public WorkClass getWc() {
		return wc;
	}

	public void setWc(WorkClass wc) {
		this.wc = wc;
	}

	public CarMotorCycle getCmc() {
		return cmc;
	}

	public void setCmc(CarMotorCycle cmc) {
		this.cmc = cmc;
	}

	public CarWagon getCw() {
		return cw;
	}

	public void setCw(CarWagon cw) {
		this.cw = cw;
	}

	public WorkPerson getWpTotal() {
		return wpTotal;
	}

	public void setWpTotal(WorkPerson wpTotal) {
		this.wpTotal = wpTotal;
	}

	public WorkPerson getWpPart() {
		return wpPart;
	}

	public void setWpPart(WorkPerson wpPart) {
		this.wpPart = wpPart;
	}

	public String getWorkKindId() {
		return workKindId;
	}

	public void setWorkKindId(String workKindId) {
		this.workKindId = workKindId;
	}

	public WorkKind getWk() {
		return wk;
	}

	public void setWk(WorkKind wk) {
		this.wk = wk;
	}
	
	
	
}