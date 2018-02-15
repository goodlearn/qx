package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;

/**
 * 业务集Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class BusinessAssembleService extends CrudService<BusinessAssembleDao, BusinessAssemble> {

	public BusinessAssemble get(String id) {
		return super.get(id);
	}
	
	public List<BusinessAssemble> findList(BusinessAssemble businessAssemble) {
		return super.findList(businessAssemble);
	}
	
	public Page<BusinessAssemble> findPage(Page<BusinessAssemble> page, BusinessAssemble businessAssemble) {
		return super.findPage(page, businessAssemble);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessAssemble businessAssemble) {
		super.save(businessAssemble);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessAssemble businessAssemble) {
		super.delete(businessAssemble);
	}
	
}