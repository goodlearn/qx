/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysWxUserCheck;
import com.thinkgem.jeesite.modules.sys.dao.SysWxUserCheckDao;

/**
 * 信信息检查表Service
 * @author wzy
 * @version 2018-01-06
 */
@Service
@Transactional(readOnly = true)
public class SysWxUserCheckService extends CrudService<SysWxUserCheckDao, SysWxUserCheck> {

	public SysWxUserCheck get(String id) {
		return super.get(id);
	}
	
	public List<SysWxUserCheck> findList(SysWxUserCheck sysWxUserCheck) {
		return super.findList(sysWxUserCheck);
	}
	
	public Page<SysWxUserCheck> findPage(Page<SysWxUserCheck> page, SysWxUserCheck sysWxUserCheck) {
		return super.findPage(page, sysWxUserCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(SysWxUserCheck sysWxUserCheck) {
		super.save(sysWxUserCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysWxUserCheck sysWxUserCheck) {
		super.delete(sysWxUserCheck);
	}
	
}