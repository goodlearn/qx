/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.SysWxInfoDao;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;

/**
 * 微信信息表Service
 * @author wzy
 * @version 2017-12-25
 */
@Service
@Transactional(readOnly = true)
public class SysWxInfoService extends CrudService<SysWxInfoDao, SysWxInfo> {

	public SysWxInfo get(String id) {
		return super.get(id);
	}
	
	public List<SysWxInfo> findList(SysWxInfo sysWxInfo) {
		return super.findList(sysWxInfo);
	}
	
	public Page<SysWxInfo> findPage(Page<SysWxInfo> page, SysWxInfo sysWxInfo) {
		return super.findPage(page, sysWxInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SysWxInfo sysWxInfo) {
		super.save(sysWxInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysWxInfo sysWxInfo) {
		super.delete(sysWxInfo);
	}
	
}