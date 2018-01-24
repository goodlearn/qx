/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;

/**
 * 车间任务Service
 * @author Wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class WorkShopMaskService extends CrudService<WorkShopMaskDao, WorkShopMask> {

	public WorkShopMask get(String id) {
		return super.get(id);
	}
	
	public List<WorkShopMask> findList(WorkShopMask workShopMask) {
		return super.findList(workShopMask);
	}
	
	public Page<WorkShopMask> findPage(Page<WorkShopMask> page, WorkShopMask workShopMask) {
		return super.findPage(page, workShopMask);
	}
	
	@Transactional(readOnly = false)
	public void save(WorkShopMask workShopMask) {
		super.save(workShopMask);
	}
	
	@Transactional(readOnly = false)
	public void delete(WorkShopMask workShopMask) {
		super.delete(workShopMask);
	}
	
}