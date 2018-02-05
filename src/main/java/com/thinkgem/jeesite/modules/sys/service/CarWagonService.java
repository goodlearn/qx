/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.CarWagon;
import com.thinkgem.jeesite.modules.sys.dao.CarWagonDao;

/**
 * 车间车号Service
 * @author wzy
 * @version 2018-02-05
 */
@Service
@Transactional(readOnly = true)
public class CarWagonService extends CrudService<CarWagonDao, CarWagon> {

	public CarWagon get(String id) {
		return super.get(id);
	}
	
	public List<CarWagon> findList(CarWagon carWagon) {
		return super.findList(carWagon);
	}
	
	public Page<CarWagon> findPage(Page<CarWagon> page, CarWagon carWagon) {
		return super.findPage(page, carWagon);
	}
	
	@Transactional(readOnly = false)
	public void save(CarWagon carWagon) {
		super.save(carWagon);
	}
	
	@Transactional(readOnly = false)
	public void delete(CarWagon carWagon) {
		super.delete(carWagon);
	}
	
}