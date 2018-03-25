/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.Date2Utils;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWc;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWs;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.MonthMaskWcDao;
import com.thinkgem.jeesite.modules.sys.dao.MonthMaskWsDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkClassDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;

/**
 * 月度计划车间任务Service
 * @author wzy
 * @version 2018-03-25
 */
@Service
@Transactional(readOnly = true)
public class MonthMaskWsService extends CrudService<MonthMaskWsDao, MonthMaskWs> {

	
	@Autowired
	private WorkClassDao workClassDao;
	
	@Autowired
	private WorkPersonDao workPersonDao;
	
	@Autowired
	private MonthMaskWcDao monthMaskWcDao;
	
	public MonthMaskWs get(String id) {
		return super.get(id);
	}
	
	public List<MonthMaskWs> findList(MonthMaskWs monthMaskWs) {
		return super.findList(monthMaskWs);
	}
	
	public Page<MonthMaskWs> findPage(Page<MonthMaskWs> page, MonthMaskWs monthMaskWs) {
		return super.findPage(page, monthMaskWs);
	}
	
	//依据员工查询
	public Page<MonthMaskWs> findPageByEmpNo(Page<MonthMaskWs> page, MonthMaskWs monthMaskWs) {
		if(UserUtils.getUser().isAdmin()) {
			return super.findPage(page, monthMaskWs);
		}
		
		//查询员工号
		String empNo = UserUtils.getUser().getEmpNo();
		if(null == empNo) {
			return page;//空数据
		}
		
		WorkPerson resultWp = new WorkPerson();
		resultWp = workPersonDao.findByEmpNo(empNo);
		String classId = resultWp.getWorkClassId();//查询班级
		WorkClass resultWc = workClassDao.get(classId);//查询班级
		String wkId = resultWc.getWorkKindId();//查询工种
		
		String value = DictUtils.getDictValue("是", "yes_no", "是");
		
		monthMaskWs.setEndDate(new Date());
		monthMaskWs.setSubmitState(value);//依据发布的
		monthMaskWs.setWorkKindId(wkId);//设置工种查询
		return super.findPage(page, monthMaskWs);
	}
	

	
	@Transactional(readOnly = false)
	public void save(MonthMaskWs monthMaskWs) {
		super.save(monthMaskWs);
	}
	
	@Transactional(readOnly = false)
	public void delete(MonthMaskWs monthMaskWs) {
		super.delete(monthMaskWs);
	}
	
	
	
	@Transactional(readOnly = false)
	public void saveBatchEnd(String[] ids) {
		String value = DictUtils.getDictValue("是", "yes_no", "是");
		for(String id : ids) {
			MonthMaskWs monthMaskWs = get(id);
			if(null!=monthMaskWs) {
				monthMaskWs.setEndDate(Date2Utils.getEndDayOfMonth());//本月结束
				monthMaskWs.setSubmitState(value);//设置发布状态
				save(monthMaskWs);
			}
		}
	}
	
}