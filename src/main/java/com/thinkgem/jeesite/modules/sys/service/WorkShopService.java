package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.WorkShop;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopDao;

/**
 * 车间Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class WorkShopService extends CrudService<WorkShopDao, WorkShop> {

	public WorkShop get(String id) {
		return super.get(id);
	}
	
	public List<WorkShop> findList(WorkShop workShop) {
		return super.findList(workShop);
	}
	
	public Page<WorkShop> findPage(Page<WorkShop> page, WorkShop workShop) {
		return super.findPage(page, workShop);
	}
	
	@Transactional(readOnly = false)
	public void save(WorkShop workShop) {
		super.save(workShop);
	}
	
	@Transactional(readOnly = false)
	public void delete(WorkShop workShop) {
		super.delete(workShop);
	}
	
}