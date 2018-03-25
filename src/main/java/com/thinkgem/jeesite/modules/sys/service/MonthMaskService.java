/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.MonthMask;
import com.thinkgem.jeesite.modules.sys.dao.MonthMaskDao;

/**
 * 月度任务表Service
 * @author wzy
 * @version 2018-03-25
 */
@Service
@Transactional(readOnly = true)
public class MonthMaskService extends CrudService<MonthMaskDao, MonthMask> {

	public MonthMask get(String id) {
		return super.get(id);
	}
	
	public List<MonthMask> findList(MonthMask monthMask) {
		return super.findList(monthMask);
	}
	
	public Page<MonthMask> findPage(Page<MonthMask> page, MonthMask monthMask) {
		return super.findPage(page, monthMask);
	}
	
	@Transactional(readOnly = false)
	public void save(MonthMask monthMask) {
		super.save(monthMask);
	}
	
	@Transactional(readOnly = false)
	public void delete(MonthMask monthMask) {
		super.delete(monthMask);
	}
	
}