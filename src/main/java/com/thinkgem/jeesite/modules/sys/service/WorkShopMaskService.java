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
import com.thinkgem.jeesite.common.utils.Date2Utils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;

/**
 * 车间任务Service
 * @author Wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class WorkShopMaskService extends CrudService<WorkShopMaskDao, WorkShopMask> {

	@Autowired
	private WsMaskWcDao wsMaskWcDao;
	
	@Autowired
	private WorkPersonDao workPersonDao;
	
	public WorkShopMask get(String id) {
		return super.get(id);
	}
	
	public List<WorkShopMask> findList(WorkShopMask workShopMask) {
		return super.findList(workShopMask);
	}
	
	public Page<WorkShopMask> findPage(Page<WorkShopMask> page, WorkShopMask workShopMask) {
		return super.findPage(page, workShopMask);
	}
	
	@Transactional(readOnly = false)
	public void save(WorkShopMask workShopMask) {
		super.save(workShopMask);
	}
	
	@Transactional(readOnly = false)
	public void delete(WorkShopMask workShopMask) {
		super.delete(workShopMask);
	}
	
	
	//发布任务
	@Transactional(readOnly = false)
	public String release(String paramId) {
		//想将任务表 状态值设置为发布
		WorkShopMask workShopMask = get(paramId);
		if(null == workShopMask) {
			return null;
		}
		String yes = DictUtils.getDictValue("是", "yes_no", "1");
		//更新数据
		workShopMask.setReleaseState(yes);
		save(workShopMask);
	
		return "0";//返回一个非空数据
	}
	
	//撤销发布任务
	@Transactional(readOnly = false)
	public String unrelease(String paramId) {
		//想将任务表 状态值设置为未发布
		WorkShopMask workShopMask = get(paramId);
		if(null == workShopMask) {
			return null;
		}
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		//更新数据
		workShopMask.setReleaseState(no);
		save(workShopMask);
		return "0";//返回一个非空数据
	}
	
	//获取班级所有人
	public List<WorkPerson> findWpByClassId(String classId){
		WorkPerson query = new WorkPerson();
		query.setWorkClassId(classId);
		return workPersonDao.findAllList(query);
	}
	
}