package com.thinkgem.jeesite.modules.wx.view;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;

/**
 * 未完成任务
 */
public class ViewUnFinishMask {

	private String workShopMaskId;//车间任务
	
	private String workShopMaskName;
	
	private String wsMaskWcId;//班组任务
	
	private List<MaskSinglePerson> mspList;//个人任务

	public String getWorkShopMaskId() {
		return workShopMaskId;
	}

	public void setWorkShopMaskId(String workShopMaskId) {
		this.workShopMaskId = workShopMaskId;
	}

	public String getWorkShopMaskName() {
		return workShopMaskName;
	}

	public void setWorkShopMaskName(String workShopMaskName) {
		this.workShopMaskName = workShopMaskName;
	}

	public String getWsMaskWcId() {
		return wsMaskWcId;
	}

	public void setWsMaskWcId(String wsMaskWcId) {
		this.wsMaskWcId = wsMaskWcId;
	}

	public List<MaskSinglePerson> getMspList() {
		return mspList;
	}

	public void setMspList(List<MaskSinglePerson> mspList) {
		this.mspList = mspList;
	}


}
