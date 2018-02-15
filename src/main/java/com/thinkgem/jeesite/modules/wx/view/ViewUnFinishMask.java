package com.thinkgem.jeesite.modules.wx.view;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.entity.Dict;

/**
 * 未完成任务
 */
public class ViewUnFinishMask {

	private String workShopMaskId;
	
	private String workShopMaskName;
	
	private List<Dict> parts;//部位

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

	public List<Dict> getParts() {
		return parts;
	}

	public void setParts(List<Dict> parts) {
		this.parts = parts;
	}

}
