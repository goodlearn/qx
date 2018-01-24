/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.dao.WorkClassDao;

/**
 * 车间班组Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class WorkClassService extends CrudService<WorkClassDao, WorkClass> {

	public WorkClass get(String id) {
		return super.get(id);
	}
	
	public List<WorkClass> findList(WorkClass workClass) {
		return super.findList(workClass);
	}
	
	public Page<WorkClass> findPage(Page<WorkClass> page, WorkClass workClass) {
		return super.findPage(page, workClass);
	}
	
	@Transactional(readOnly = false)
	public void save(WorkClass workClass) {
		super.save(workClass);
	}
	
	@Transactional(readOnly = false)
	public void delete(WorkClass workClass) {
		super.delete(workClass);
	}
	
}