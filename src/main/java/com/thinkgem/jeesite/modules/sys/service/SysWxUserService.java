/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysWxUser;
import com.thinkgem.jeesite.modules.sys.dao.SysWxUserDao;

/**
 * 微信用户表Service
 * @author wzy
 * @version 2017-12-25
 */
@Service
@Transactional(readOnly = true)
public class SysWxUserService extends CrudService<SysWxUserDao, SysWxUser> {

	public SysWxUser get(String id) {
		return super.get(id);
	}
	
	public SysWxUser getByIdCard(String idCard) {
		return dao.findByIdCard(idCard);
	}
	
	public List<SysWxUser> findList(SysWxUser sysWxUser) {
		return super.findList(sysWxUser);
	}
	
	public Page<SysWxUser> findPage(Page<SysWxUser> page, SysWxUser sysWxUser) {
		return super.findPage(page, sysWxUser);
	}
	
	@Transactional(readOnly = false)
	public void save(SysWxUser sysWxUser) {
		super.save(sysWxUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysWxUser sysWxUser) {
		super.delete(sysWxUser);
	}
	
}