/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;

/**
 * 车间任务班级关联Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class WsMaskWcService extends CrudService<WsMaskWcDao, WsMaskWc> {
	
	@Autowired
	private WorkPersonDao workPersonDao;


	public WsMaskWc get(String id) {
		return super.get(id);
	}
	
	public List<WsMaskWc> findList(WsMaskWc wsMaskWc) {
		return super.findList(wsMaskWc);
	}
	
	public Page<WsMaskWc> findPage(Page<WsMaskWc> page, WsMaskWc wsMaskWc) {
		return super.findPage(page, wsMaskWc);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMaskWc wsMaskWc) {
		super.save(wsMaskWc);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMaskWc wsMaskWc) {
		super.delete(wsMaskWc);
	}
	
}