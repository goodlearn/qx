/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.CheckSpotItem;
import com.thinkgem.jeesite.modules.sys.dao.CheckSpotItemDao;

/**
 * 点检项Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class CheckSpotItemService extends CrudService<CheckSpotItemDao, CheckSpotItem> {

	public CheckSpotItem get(String id) {
		return super.get(id);
	}
	
	public List<CheckSpotItem> findList(CheckSpotItem checkSpotItem) {
		return super.findList(checkSpotItem);
	}
	
	public Page<CheckSpotItem> findPage(Page<CheckSpotItem> page, CheckSpotItem checkSpotItem) {
		return super.findPage(page, checkSpotItem);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckSpotItem checkSpotItem) {
		super.save(checkSpotItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckSpotItem checkSpotItem) {
		super.delete(checkSpotItem);
	}
	
}