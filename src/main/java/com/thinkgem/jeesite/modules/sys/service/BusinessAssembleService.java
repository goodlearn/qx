package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;

/**
 * 业务集Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class BusinessAssembleService extends CrudService<BusinessAssembleDao, BusinessAssemble> {

	@Autowired
	private WorkShopMaskDao workShopMaskDao;
	
	@Autowired
	private WsMaskWcDao wsMaskWcDao;
	
	public BusinessAssemble get(String id) {
		return super.get(id);
	}
	
	public List<BusinessAssemble> findList(BusinessAssemble businessAssemble) {
		return super.findList(businessAssemble);
	}
	
	public Page<BusinessAssemble> findPage(Page<BusinessAssemble> page, BusinessAssemble businessAssemble) {
		return super.findPage(page, businessAssemble);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessAssemble businessAssemble) {
		super.save(businessAssemble);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessAssemble businessAssemble) {
		super.delete(businessAssemble);
	}
	
	//获取任务模板类型
	public String findBaType(String wsMaskWcId) {
		//依据任务号找到车间任务号
		WsMaskWc wsMaskWc = wsMaskWcDao.get(wsMaskWcId);
		String workShopMaskId = wsMaskWc.getWorkShopMaskId();
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskDao.get(workShopMaskId);
		//找到业务集号
		String bussinessAssembleId = workShopMask.getBussinessAssembleId();
		//找到业务集
		BusinessAssemble businessAssemble = dao.get(bussinessAssembleId);
		//找到类型
		return businessAssemble.getType();
		
	}
	
}