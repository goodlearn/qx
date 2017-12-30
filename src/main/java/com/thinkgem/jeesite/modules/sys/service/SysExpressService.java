/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysExpress;
import com.thinkgem.jeesite.modules.sys.entity.SysWxUser;
import com.thinkgem.jeesite.modules.sys.dao.SysExpressDao;
import com.thinkgem.jeesite.modules.sys.dao.SysWxUserDao;

/**
 * 快递信息表Service
 * @author wzy
 * @version 2017-12-25
 */
@Service
@Transactional(readOnly = true)
public class SysExpressService extends CrudService<SysExpressDao, SysExpress> {
	
	@Autowired
	private SysWxUserDao sysWxUserDao;
	

	public SysExpress get(String id) {
		return super.get(id);
	}
	
	//查询快递详细信息
	public SysExpress findDetailExpressInfo(SysExpress sysExpress) {
		//根据快递单中的电话查询个人信息
		//快递信息在页面进入的时候就会查询出来，所以这个部分不需要查询
		String phone = sysExpress.getPhone();
		SysWxUser sysWxUser = sysWxUserDao.findByPhone(phone);
		sysExpress.setSysWxUser(sysWxUser);
		return sysExpress;
	}
	
	public List<SysExpress> findList(SysExpress sysExpress) {
		return super.findList(sysExpress);
	}
	
	public Page<SysExpress> findPage(Page<SysExpress> page, SysExpress sysExpress) {
		return super.findPage(page, sysExpress);
	}
	
	@Transactional(readOnly = false)
	public void save(SysExpress sysExpress) {
		super.save(sysExpress);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysExpress sysExpress) {
		super.delete(sysExpress);
	}
	
}