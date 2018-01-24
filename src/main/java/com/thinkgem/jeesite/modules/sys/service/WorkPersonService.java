/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;

/**
 * 车间人员Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class WorkPersonService extends CrudService<WorkPersonDao, WorkPerson> {

	public WorkPerson get(String id) {
		return super.get(id);
	}
	
	public List<WorkPerson> findList(WorkPerson workPerson) {
		return super.findList(workPerson);
	}
	
	public Page<WorkPerson> findPage(Page<WorkPerson> page, WorkPerson workPerson) {
		return super.findPage(page, workPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(WorkPerson workPerson) {
		super.save(workPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(WorkPerson workPerson) {
		super.delete(workPerson);
	}
	
}