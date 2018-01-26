package com.thinkgem.jeesite.modules.sys.state;

import org.springframework.ui.Model;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.dao.BusinessResultItemDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckContentDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckMainPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckSinglePersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;

/**
 * 点检状态参数对象 也是结果对象
 *
 */
public class ScStateParam {

	private WorkPersonDao workPersonDao;
	
	private WorkShopMaskDao workShopMaskDao;
	
	private WsMaskWcDao wsMaskWcDao;
	
	private SpotCheckMainPersonDao spotCheckMainPersonDao;
	
	private SpotCheckSinglePersonDao spotCheckSinglePersonDao;
	
	private SpotCheckContentDao spotCheckContentDao;
	
	private BusinessResultItemDao businessResultItemDao;
	
	private boolean isPC = false;//默认是微信 PC和微信返回的URL不同
	
	private Model model;//设置返回参数
	
	private String empNo;//员工编号
	
	private int item = 0;//点检项目 前部 中部 后部
	
	private String value = null;
	
	//提示消息，如果是错误页面，需要将错误消息返回
	public final static String NO_PUBLISH_WORK_SHOP_MASK = "车间未发布任务";
	public final static String NO_PUBLISH_CLASS_MASK = "班长还未分配任务";
	public final static String NO_PUBLISH_PERSON_MASK = "该部位进度未开放";
	
	//微信  URL
	public final static String NO_PUBLISH_WORK_SHOP_MASK_CLASS_URL = "还未发布过任务,进入发布URL";
	public final static String ERROR_URL_WX = "modules/wxp/wx500";
	public final static String NO_CLASS_NO_PROCESS = "URL 还有未处理的任务";
	
	//PC URL
	public final static String ERROR_URL_PC = "modules/sys/sysError";
	public final static String NO_CLASS_NO_PROCESS_URL_PC = "modules/checkspot/sccList";
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
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

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public SpotCheckMainPersonDao getSpotCheckMainPersonDao() {
		return spotCheckMainPersonDao;
	}

	public void setSpotCheckMainPersonDao(SpotCheckMainPersonDao spotCheckMainPersonDao) {
		this.spotCheckMainPersonDao = spotCheckMainPersonDao;
	}

	public SpotCheckSinglePersonDao getSpotCheckSinglePersonDao() {
		return spotCheckSinglePersonDao;
	}

	public void setSpotCheckSinglePersonDao(SpotCheckSinglePersonDao spotCheckSinglePersonDao) {
		this.spotCheckSinglePersonDao = spotCheckSinglePersonDao;
	}

	public BusinessResultItemDao getBusinessResultItemDao() {
		return businessResultItemDao;
	}

	public void setBusinessResultItemDao(BusinessResultItemDao businessResultItemDao) {
		this.businessResultItemDao = businessResultItemDao;
	}

	public SpotCheckContentDao getSpotCheckContentDao() {
		return spotCheckContentDao;
	}

	public void setSpotCheckContentDao(SpotCheckContentDao spotCheckContentDao) {
		this.spotCheckContentDao = spotCheckContentDao;
	}
	
	
}
