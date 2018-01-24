/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.CarInfo;
import com.thinkgem.jeesite.modules.sys.dao.CarInfoDao;

/**
 * 车辆信息Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class CarInfoService extends CrudService<CarInfoDao, CarInfo> {

	public CarInfo get(String id) {
		return super.get(id);
	}
	
	public List<CarInfo> findList(CarInfo carInfo) {
		return super.findList(carInfo);
	}
	
	public Page<CarInfo> findPage(Page<CarInfo> page, CarInfo carInfo) {
		return super.findPage(page, carInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CarInfo carInfo) {
		super.save(carInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CarInfo carInfo) {
		super.delete(carInfo);
	}
	
}