package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 车间任务班级关联Entity
 * @author wzy
 * @version 2018-01-24
 */
public class WsMaskWc extends DataEntity<WsMaskWc> {
	
	private static final long serialVersionUID = 1L;
	private String workShopMaskId;		// 车间任务号
	private String workClassId;		// 班级号
	private String runTime;  //运行时间数
	private Date endDate;	// 结束日期
	private String submitState;//是否提交
	private String imagePath; //图片路径
	private WorkShopMask wsm; //任务
	private WorkClass wc; //班级
	private String beginQueryDate;
	private String endQueryDate;
	
	private List<MaskMainPerson> mmpList;//总负责人

	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<MaskMainPerson> getMmpList() {
		return mmpList;
	}

	public void setMmpList(List<MaskMainPerson> mmpList) {
		this.mmpList = mmpList;
	}

	public String getBeginQueryDate() {
		return beginQueryDate;
	}

	public void setBeginQueryDate(String beginQueryDate) {
		this.beginQueryDate = beginQueryDate;
	}

	public String getEndQueryDate() {
		return endQueryDate;
	}

	public void setEndQueryDate(String endQueryDate) {
		this.endQueryDate = endQueryDate;
	}

	public WsMaskWc() {
		super();
	}

	public WsMaskWc(String id){
		super(id);
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}


	@Length(min=1, max=64, message="车间任务号长度必须介于 1 和 64 之间")
	public String getWorkShopMaskId() {
		return workShopMaskId;
	}

	public void setWorkShopMaskId(String workShopMaskId) {
		this.workShopMaskId = workShopMaskId;
	}
	
	@Length(min=1, max=64, message="班级号长度必须介于 1 和 64 之间")
	public String getWorkClassId() {
		return workClassId;
	}

	public void setWorkClassId(String workClassId) {
		this.workClassId = workClassId;
	}

	public WorkShopMask getWsm() {
		return wsm;
	}

	public void setWsm(WorkShopMask wsm) {
		this.wsm = wsm;
	}

	public WorkClass getWc() {
		return wc;
	}

	public void setWc(WorkClass wc) {
		this.wc = wc;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSubmitState() {
		return submitState;
	}

	public void setSubmitState(String submitState) {
		this.submitState = submitState;
	}
	
	
}