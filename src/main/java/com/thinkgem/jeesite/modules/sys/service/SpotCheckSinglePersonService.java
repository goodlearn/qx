/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckSinglePerson;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckSinglePersonDao;

/**
 * 点检卡个人负责人任务表Service
 * @author wzy
 * @version 2018-01-25
 */
@Service
@Transactional(readOnly = true)
public class SpotCheckSinglePersonService extends CrudService<SpotCheckSinglePersonDao, SpotCheckSinglePerson> {

	public SpotCheckSinglePerson get(String id) {
		return super.get(id);
	}
	
	public List<SpotCheckSinglePerson> findList(SpotCheckSinglePerson spotCheckSinglePerson) {
		return super.findList(spotCheckSinglePerson);
	}
	
	public Page<SpotCheckSinglePerson> findPage(Page<SpotCheckSinglePerson> page, SpotCheckSinglePerson spotCheckSinglePerson) {
		return super.findPage(page, spotCheckSinglePerson);
	}
	
	@Transactional(readOnly = false)
	public void save(SpotCheckSinglePerson spotCheckSinglePerson) {
		super.save(spotCheckSinglePerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(SpotCheckSinglePerson spotCheckSinglePerson) {
		super.delete(spotCheckSinglePerson);
	}
	
}