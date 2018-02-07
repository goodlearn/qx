package com.thinkgem.jeesite.modules.sys.maskdispatch;

import org.springframework.ui.Model;

import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.MotorCheckSpotItem1Dao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;

/**
 * MaskDispatch 任务分发状态参数
 * @author Administrator
 *
 */
public class MdStateParam {

	private WorkPersonDao workPersonDao;
	
	private WorkShopMaskDao workShopMaskDao;
	
	private WsMaskWcDao wsMaskWcDao;
	
	private BusinessAssembleDao businessAssembleDao;
	
	private MotorCheckSpotItem1Dao motorCheckSpotItem1Daol;
	
	private boolean isPC = false;//默认是微信 PC和微信返回的URL不同
	
	private Model model;//设置返回参数
	
	private String empNo;//员工编号
	
	private String maskId;//调度的任务Id
	
	private String value = null;//返回值
	
	
	public String getMaskId() {
		return maskId;
	}

	public void setMaskId(String maskId) {
		this.maskId = maskId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
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

	public WsMaskWcDao getWsMaskWcDao() {
		return wsMaskWcDao;
	}

	public void setWsMaskWcDao(WsMaskWcDao wsMaskWcDao) {
		this.wsMaskWcDao = wsMaskWcDao;
	}


	public boolean isPC() {
		return isPC;
	}

	public void setPC(boolean isPC) {
		this.isPC = isPC;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public BusinessAssembleDao getBusinessAssembleDao() {
		return businessAssembleDao;
	}

	public void setBusinessAssembleDao(BusinessAssembleDao businessAssembleDao) {
		this.businessAssembleDao = businessAssembleDao;
	}

	public MotorCheckSpotItem1Dao getMotorCheckSpotItem1Daol() {
		return motorCheckSpotItem1Daol;
	}

	public void setMotorCheckSpotItem1Daol(MotorCheckSpotItem1Dao motorCheckSpotItem1Daol) {
		this.motorCheckSpotItem1Daol = motorCheckSpotItem1Daol;
	}
	
	
	
	
	
	
}
