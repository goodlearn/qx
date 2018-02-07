/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.dao.MaskSinglePersonDao;

/**
 * 个人负责人任务表Service
 * @author wzy
 * @version 2018-02-07
 */
@Service
@Transactional(readOnly = true)
public class MaskSinglePersonService extends CrudService<MaskSinglePersonDao, MaskSinglePerson> {

	public MaskSinglePerson get(String id) {
		return super.get(id);
	}
	
	public List<MaskSinglePerson> findList(MaskSinglePerson maskSinglePerson) {
		return super.findList(maskSinglePerson);
	}
	
	public Page<MaskSinglePerson> findPage(Page<MaskSinglePerson> page, MaskSinglePerson maskSinglePerson) {
		return super.findPage(page, maskSinglePerson);
	}
	
	@Transactional(readOnly = false)
	public void save(MaskSinglePerson maskSinglePerson) {
		super.save(maskSinglePerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(MaskSinglePerson maskSinglePerson) {
		super.delete(maskSinglePerson);
	}
	
}