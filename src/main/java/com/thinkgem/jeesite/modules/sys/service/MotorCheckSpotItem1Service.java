/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.MotorCheckSpotItem1;
import com.thinkgem.jeesite.modules.sys.dao.MotorCheckSpotItem1Dao;

/**
 * 发动机点检单一Service
 * @author wzy
 * @version 2018-02-05
 */
@Service
@Transactional(readOnly = true)
public class MotorCheckSpotItem1Service extends CrudService<MotorCheckSpotItem1Dao, MotorCheckSpotItem1> {

	public MotorCheckSpotItem1 get(String id) {
		return super.get(id);
	}
	
	public List<MotorCheckSpotItem1> findList(MotorCheckSpotItem1 motorCheckSpotItem1) {
		return super.findList(motorCheckSpotItem1);
	}
	
	public Page<MotorCheckSpotItem1> findPage(Page<MotorCheckSpotItem1> page, MotorCheckSpotItem1 motorCheckSpotItem1) {
		return super.findPage(page, motorCheckSpotItem1);
	}
	
	@Transactional(readOnly = false)
	public void save(MotorCheckSpotItem1 motorCheckSpotItem1) {
		super.save(motorCheckSpotItem1);
	}
	
	@Transactional(readOnly = false)
	public void delete(MotorCheckSpotItem1 motorCheckSpotItem1) {
		super.delete(motorCheckSpotItem1);
	}
	
}