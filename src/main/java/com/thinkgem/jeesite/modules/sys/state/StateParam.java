package com.thinkgem.jeesite.modules.sys.state;

import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;

/**
 * 状态参数对象 也是结果对象
 *
 */
public class StateParam {

	private WorkPersonDao workPersonDao;
	
	private WorkShopMaskDao workShopMaskDao;
	
	private WsMaskWcDao wsMaskWcDao;
	
	private String empNo;
	
	private String resultCode;//结果码
	
	public final String NO_PUBLISH_WORK_SHOP_MASK = "车间未发布任务";
	public final String NO_PUBLISH_CLASS_MASK = "班长还未分配任务";
	public final String NO_PUBLISH_WORK_SHOP_MASK_CLASS_URL = "还未发布过任务,进入发布URL";
	public final String NO_CLASS_NO_PROCESS = "URL 还有未处理的任务";
	
	
	public final String MESSAGE_CODE = "1";//任务提示
	public final String URL_CODE = "2";//这个是URL
	
	private  String value = null;


	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	
	public WsMaskWcDao getWsMaskWcDao() {
		return wsMaskWcDao;
	}

	public void setWsMaskWcDao(WsMaskWcDao wsMaskWcDao) {
		this.wsMaskWcDao = wsMaskWcDao;
	}

	public WorkPersonDao getWorkPersonDao() {
		return workPersonDao;
	}

	public void setWorkPersonDao(WorkPersonDao workPersonDao) {
		this.workPersonDao = workPersonDao;
	}

	
	public WorkShopMaskDao getWorkShopMaskDao() {
		return workShopMaskDao;
	}

	public void setWorkShopMaskDao(WorkShopMaskDao workShopMaskDao) {
		this.workShopMaskDao = workShopMaskDao;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	
	
}
