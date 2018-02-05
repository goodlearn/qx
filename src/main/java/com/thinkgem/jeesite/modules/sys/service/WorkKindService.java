/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.WorkKind;
import com.thinkgem.jeesite.modules.sys.dao.WorkKindDao;

/**
 * 车间工种Service
 * @author wzy
 * @version 2018-02-05
 */
@Service
@Transactional(readOnly = true)
public class WorkKindService extends CrudService<WorkKindDao, WorkKind> {

	public WorkKind get(String id) {
		return super.get(id);
	}
	
	public List<WorkKind> findList(WorkKind workKind) {
		return super.findList(workKind);
	}
	
	public Page<WorkKind> findPage(Page<WorkKind> page, WorkKind workKind) {
		return super.findPage(page, workKind);
	}
	
	@Transactional(readOnly = false)
	public void save(WorkKind workKind) {
		super.save(workKind);
	}
	
	@Transactional(readOnly = false)
	public void delete(WorkKind workKind) {
		super.delete(workKind);
	}
	
}