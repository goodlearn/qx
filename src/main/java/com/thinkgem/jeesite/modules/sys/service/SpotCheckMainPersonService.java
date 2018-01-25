/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckMainPerson;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckMainPersonDao;

/**
 * 点检卡总负责人任务表Service
 * @author wzy
 * @version 2018-01-25
 */
@Service
@Transactional(readOnly = true)
public class SpotCheckMainPersonService extends CrudService<SpotCheckMainPersonDao, SpotCheckMainPerson> {

	public SpotCheckMainPerson get(String id) {
		return super.get(id);
	}
	
	public List<SpotCheckMainPerson> findList(SpotCheckMainPerson spotCheckMainPerson) {
		return super.findList(spotCheckMainPerson);
	}
	
	public Page<SpotCheckMainPerson> findPage(Page<SpotCheckMainPerson> page, SpotCheckMainPerson spotCheckMainPerson) {
		return super.findPage(page, spotCheckMainPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(SpotCheckMainPerson spotCheckMainPerson) {
		super.save(spotCheckMainPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(SpotCheckMainPerson spotCheckMainPerson) {
		super.delete(spotCheckMainPerson);
	}
	
}