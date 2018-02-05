/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.CarMotorCycle;
import com.thinkgem.jeesite.modules.sys.dao.CarMotorCycleDao;

/**
 * 车间车型Service
 * @author wzy
 * @version 2018-02-05
 */
@Service
@Transactional(readOnly = true)
public class CarMotorCycleService extends CrudService<CarMotorCycleDao, CarMotorCycle> {

	public CarMotorCycle get(String id) {
		return super.get(id);
	}
	
	public List<CarMotorCycle> findList(CarMotorCycle carMotorCycle) {
		return super.findList(carMotorCycle);
	}
	
	public Page<CarMotorCycle> findPage(Page<CarMotorCycle> page, CarMotorCycle carMotorCycle) {
		return super.findPage(page, carMotorCycle);
	}
	
	@Transactional(readOnly = false)
	public void save(CarMotorCycle carMotorCycle) {
		super.save(carMotorCycle);
	}
	
	@Transactional(readOnly = false)
	public void delete(CarMotorCycle carMotorCycle) {
		super.delete(carMotorCycle);
	}
	
}