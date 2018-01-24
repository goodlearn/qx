/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.BusinessResultAssemble;
import com.thinkgem.jeesite.modules.sys.dao.BusinessResultAssembleDao;

/**
 * 业务结果集表Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class BusinessResultAssembleService extends CrudService<BusinessResultAssembleDao, BusinessResultAssemble> {

	public BusinessResultAssemble get(String id) {
		return super.get(id);
	}
	
	public List<BusinessResultAssemble> findList(BusinessResultAssemble businessResultAssemble) {
		return super.findList(businessResultAssemble);
	}
	
	public Page<BusinessResultAssemble> findPage(Page<BusinessResultAssemble> page, BusinessResultAssemble businessResultAssemble) {
		return super.findPage(page, businessResultAssemble);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessResultAssemble businessResultAssemble) {
		super.save(businessResultAssemble);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessResultAssemble businessResultAssemble) {
		super.delete(businessResultAssemble);
	}
	
}