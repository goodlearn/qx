package com.thinkgem.jeesite.modules.sys.maskdispatch;

import java.util.List;

import org.springframework.ui.Model;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.MotorCheckSpotItem1Dao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.MotorCheckSpotItem1;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.BaseInfoUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 页面调度的时候，用来进行分发的类
 * 发动机 电工的表格不同，通过这个类，对每个任务请求返回不同的处理页面
 *
 */
public class MdControl {

	
	private WorkPersonDao workPersonDao;
	
	private WorkShopMaskDao workShopMaskDao;
	
	private WsMaskWcDao wsMaskWcDao;
	
	private BusinessAssembleDao businessAssembleDao;
	
	
	private MotorCheckSpotItem1Dao motorCheckSpotItem1Daol;
	
	public final static String ERROR_URL_PC = "modules/sys/sysError";
	public final static String MOTOR_CHECK_SPOT_ITEM_1 = "modules/maskdispatch/mcsi1Form";//模板表1 发动机点检单一
	
	public final static String NO_MONITOR = "您不是班长，无操作权限";
	
	private String value = null;//返回值
	
	private Model model;//设置返回参数
	
	private String maskId;//调度的任务Id
	
	public void pageDispatch() {
		//先看看是不是班长
		if(!isMonitor()) {
			model.addAttribute("message",NO_MONITOR);
			setValue(ERROR_URL_PC);
		}
		
		//根据任务号 找到分配页面
		setMaskAllocation();
	}
	
	//根据任务号 设置分配页面
	private void setMaskAllocation() {
		
		//依据任务号找到车间任务号
		WsMaskWc wsMaskWc = wsMaskWcDao.get(maskId);
		String workShopMaskId = wsMaskWc.getWorkShopMaskId();
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskDao.get(workShopMaskId);
		//找到业务集号
		String bussinessAssembleId = workShopMask.getBussinessAssembleId();
		//找到业务集
		BusinessAssemble businessAssemble = businessAssembleDao.get(bussinessAssembleId);
		//找到类型
		String type = businessAssemble.getType();
		
		//字典数据
		if(type.equals(DictUtils.getDictValue(Global.MOTOR_CHECK_SPOT_ITEM_1, "businessResultType", "1"))) {
			//模板表1 发动机点检单一
			setMotorCheckSpotItem1Data();//设置数据
		}
	}

	//模板表 发动机点检单一
	private void setMotorCheckSpotItem1Data() {
		model.addAttribute("mcsis", DictUtils.getDictList("motorCsItem1"));
		model.addAttribute("wp", BaseInfoUtils.getAllPersonList());
		model.addAttribute("maskId",maskId);
		setValue(MOTOR_CHECK_SPOT_ITEM_1);
	}
	
	//先看看是不是班长
	private boolean isMonitor() {
		User user = UserUtils.getUser();
		if(user.isAdmin()) {
			return true;//如果是管理员直接跳过
		}
		String empNo = user.getEmpNo();
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		String level = person.getLevel();
		String dictLevel = DictUtils.getDictValue("班长", "workPersonLevel", "1");
		if(dictLevel.equals(level)) {
			return true;
		}
		return false;
	}
	
	
	public BusinessAssembleDao getBusinessAssembleDao() {
		return businessAssembleDao;
	}

	public void setBusinessAssembleDao(BusinessAssembleDao businessAssembleDao) {
		this.businessAssembleDao = businessAssembleDao;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

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


	public MotorCheckSpotItem1Dao getMotorCheckSpotItem1Daol() {
		return motorCheckSpotItem1Daol;
	}

	public void setMotorCheckSpotItem1Daol(MotorCheckSpotItem1Dao motorCheckSpotItem1Daol) {
		this.motorCheckSpotItem1Daol = motorCheckSpotItem1Daol;
	}
	
	
	
}
