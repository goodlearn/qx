/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.dao.MaskMainPersonDao;

/**
 * 总负责人任务表Service
 * @author wzy
 * @version 2018-02-07
 */
@Service
@Transactional(readOnly = true)
public class MaskMainPersonService extends CrudService<MaskMainPersonDao, MaskMainPerson> {

	public MaskMainPerson get(String id) {
		return super.get(id);
	}
	
	public List<MaskMainPerson> findList(MaskMainPerson maskMainPerson) {
		return super.findList(maskMainPerson);
	}
	
	public Page<MaskMainPerson> findPage(Page<MaskMainPerson> page, MaskMainPerson maskMainPerson) {
		return super.findPage(page, maskMainPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(MaskMainPerson maskMainPerson) {
		super.save(maskMainPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(MaskMainPerson maskMainPerson) {
		super.delete(maskMainPerson);
	}
	
}