/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.BusinessResultItem;
import com.thinkgem.jeesite.modules.sys.dao.BusinessResultItemDao;

/**
 * 业务结果项表Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class BusinessResultItemService extends CrudService<BusinessResultItemDao, BusinessResultItem> {

	public BusinessResultItem get(String id) {
		return super.get(id);
	}
	
	public List<BusinessResultItem> findList(BusinessResultItem businessResultItem) {
		return super.findList(businessResultItem);
	}
	
	public Page<BusinessResultItem> findPage(Page<BusinessResultItem> page, BusinessResultItem businessResultItem) {
		return super.findPage(page, businessResultItem);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessResultItem businessResultItem) {
		super.save(businessResultItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessResultItem businessResultItem) {
		super.delete(businessResultItem);
	}
	
}