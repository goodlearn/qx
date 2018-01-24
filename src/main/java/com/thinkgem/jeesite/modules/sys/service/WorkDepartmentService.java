/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.WorkDepartment;
import com.thinkgem.jeesite.modules.sys.entity.WorkShop;
import com.thinkgem.jeesite.modules.sys.dao.WorkDepartmentDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopDao;

/**
 * 车间部门Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class WorkDepartmentService extends CrudService<WorkDepartmentDao, WorkDepartment> {
	
	@Autowired
	private WorkShopDao workShopDao;
	
	
	/**
	 * 查询所有车间
	 * 用处：可以添加部门的时候，下拉列表显示所有车间，添加所属车间
	 */
	public List<WorkShop> findAllWorkShop(){
		//键是id 值是名称
		WorkShop entity = new WorkShop();
		List<WorkShop> workShops = workShopDao.findAllList(entity);
		if(null == workShops || workShops.size() == 0) {
			return null;//没有数据
		}
		return workShops;
	}

	public WorkDepartment get(String id) {
		return super.get(id);
	}
	
	public List<WorkDepartment> findList(WorkDepartment workDepartment) {
		return super.findList(workDepartment);
	}
	
	public Page<WorkDepartment> findPage(Page<WorkDepartment> page, WorkDepartment workDepartment) {
		return super.findPage(page, workDepartment);
	}
	
	@Transactional(readOnly = false)
	public void save(WorkDepartment workDepartment) {
		super.save(workDepartment);
	}
	
	@Transactional(readOnly = false)
	public void delete(WorkDepartment workDepartment) {
		super.delete(workDepartment);
	}
	
}