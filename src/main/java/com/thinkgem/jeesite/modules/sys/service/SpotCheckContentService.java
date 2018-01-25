/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckContent;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckContentDao;

/**
 * 点检卡内容表Service
 * @author wzy
 * @version 2018-01-25
 */
@Service
@Transactional(readOnly = true)
public class SpotCheckContentService extends CrudService<SpotCheckContentDao, SpotCheckContent> {

	public SpotCheckContent get(String id) {
		return super.get(id);
	}
	
	public List<SpotCheckContent> findList(SpotCheckContent spotCheckContent) {
		return super.findList(spotCheckContent);
	}
	
	public Page<SpotCheckContent> findPage(Page<SpotCheckContent> page, SpotCheckContent spotCheckContent) {
		return super.findPage(page, spotCheckContent);
	}
	
	@Transactional(readOnly = false)
	public void save(SpotCheckContent spotCheckContent) {
		super.save(spotCheckContent);
	}
	
	@Transactional(readOnly = false)
	public void delete(SpotCheckContent spotCheckContent) {
		super.delete(spotCheckContent);
	}
	
}