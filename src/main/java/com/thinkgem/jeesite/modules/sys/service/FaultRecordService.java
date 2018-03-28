/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.FaultRecord;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.dao.FaultRecordDao;

/**
 * 故障记录表Service
 * @author wzy
 * @version 2018-02-08
 */
@Service
@Transactional(readOnly = true)
public class FaultRecordService extends CrudService<FaultRecordDao, FaultRecord> {

	public FaultRecord get(String id) {
		return super.get(id);
	}
	
	public List<FaultRecord> findList(FaultRecord faultRecord) {
		return super.findList(faultRecord);
	}
	
	public Page<FaultRecord> findPage(Page<FaultRecord> page, FaultRecord faultRecord) {
		return super.findPage(page, faultRecord);
	}
	
	@Transactional(readOnly = false)
	public void saveWx(FaultRecord faultRecord,User user) {
		if (faultRecord.getIsNewRecord()){
			faultRecord.setId(IdGen.uuid());
			faultRecord.setUpdateBy(user);
			faultRecord.setCreateBy(user);
			faultRecord.setUpdateDate(new Date());
			faultRecord.setCreateDate(faultRecord.getUpdateDate());
			dao.insert(faultRecord);
		}else{
			faultRecord.setUpdateDate(new Date());
			faultRecord.setUpdateBy(user);
			dao.update(faultRecord);
		}
		super.save(faultRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(FaultRecord faultRecord) {
		super.save(faultRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(FaultRecord faultRecord) {
		super.delete(faultRecord);
	}
	
}