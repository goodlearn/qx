/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.MaskContent;
import com.thinkgem.jeesite.modules.sys.dao.MaskContentDao;

/**
 * 任务内容表Service
 * @author wzy
 * @version 2018-02-07
 */
@Service
@Transactional(readOnly = true)
public class MaskContentService extends CrudService<MaskContentDao, MaskContent> {

	public MaskContent get(String id) {
		return super.get(id);
	}
	
	public List<MaskContent> findList(MaskContent maskContent) {
		return super.findList(maskContent);
	}
	
	public Page<MaskContent> findPage(Page<MaskContent> page, MaskContent maskContent) {
		return super.findPage(page, maskContent);
	}
	
	@Transactional(readOnly = false)
	public void save(MaskContent maskContent) {
		super.save(maskContent);
	}
	
	@Transactional(readOnly = false)
	public void delete(MaskContent maskContent) {
		super.delete(maskContent);
	}
	
}