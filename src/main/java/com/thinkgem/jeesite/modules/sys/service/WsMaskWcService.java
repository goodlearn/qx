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
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckContentDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckMainPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckSinglePersonDao;
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
	@Autowired
	private SpotCheckMainPersonDao spotCheckMainPersonDao;
	@Autowired
	private SpotCheckSinglePersonDao spotCheckSinglePersonDao;
	@Autowired
	private SpotCheckContentDao spotCheckContentDao;

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
	
	//是否有未提交的数据
	public boolean isNotSubmit(String empNo) {
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		String classId = person.getWorkClassId();
		
		//依据班级号查询任务
		WsMaskWc query = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		query.setWorkClassId(classId);
		List<WsMaskWc> wmsList = dao.findAllList(query);
		if(null == wmsList || wmsList.size() == 0) {
			//没有发布过任务
			//没有发布
			return false;
		}
		
		query.setSubmitState(no);//未提交的
		query.setEndDate(new Date());//当前时间小于结束的时间
		List<WsMaskWc> expired = dao.findAllList(query);
		if(null != expired && expired.size() > 0) {
			//有 没有处理的任务 未过期 未提交
			return true;
		}
		return false;
	}
	
	//分配任务
	@Transactional(readOnly = false)
	public void saveScMask(WsMaskWc wsMaskWc) {
		
		//提交状态
		String noSubmit = DictUtils.getDictValue("否", "yes_no", "0");
		//先保存总负责人
		User user = UserUtils.getUser();
		String empNo = user.getEmpNo();
		WorkPerson wp = workPersonDao.findByEmpNo(empNo);
		
		
		SpotCheckMainPerson spotCheckMainPerson = new SpotCheckMainPerson();
		String mainId = IdGen.uuid();
		spotCheckMainPerson.setId(mainId);
		spotCheckMainPerson.setWsMaskWcId(wsMaskWc.getWorkShopMaskId());
		spotCheckMainPerson.setWorkPersonId(wp.getId());
		spotCheckMainPerson.setRunTime(Date2Utils.getEndDayOfTomorrow());
		spotCheckMainPerson.setSubmitState(noSubmit);
		spotCheckMainPerson.setCreateBy(user);
		spotCheckMainPerson.setCreateDate(new Date());
		spotCheckMainPerson.setUpdateBy(user);
		spotCheckMainPerson.setUpdateDate(new Date());
		spotCheckMainPersonDao.insert(spotCheckMainPerson);
		//保存分部负责人
		
		//前部
		String frontId = wsMaskWc.getFrontPerson();
		SpotCheckSinglePerson frontScsp = new SpotCheckSinglePerson();
		frontScsp.setId(IdGen.uuid());
		frontScsp.setScmpId(mainId);
		frontScsp.setWorkPersonId(frontId);
		frontScsp.setSubmitState(noSubmit);
		frontScsp.setCreateBy(user);
		frontScsp.setCreateDate(new Date());
		frontScsp.setUpdateBy(user);
		frontScsp.setUpdateDate(new Date());
		spotCheckSinglePersonDao.insert(frontScsp);
		
		//中部
		String centralId = wsMaskWc.getCentralPerson();
		SpotCheckSinglePerson centralPerson = new SpotCheckSinglePerson();
		centralPerson.setId(IdGen.uuid());
		centralPerson.setScmpId(mainId);
		centralPerson.setWorkPersonId(centralId);
		centralPerson.setSubmitState(noSubmit);
		centralPerson.setCreateBy(user);
		centralPerson.setCreateDate(new Date());
		centralPerson.setUpdateBy(user);
		centralPerson.setUpdateDate(new Date());
		spotCheckSinglePersonDao.insert(centralPerson);
		
		//后部
		String heelId = wsMaskWc.getHeelPerson();
		SpotCheckSinglePerson heelPerson = new SpotCheckSinglePerson();
		heelPerson.setId(IdGen.uuid());
		heelPerson.setScmpId(mainId);
		heelPerson.setWorkPersonId(heelId);
		heelPerson.setSubmitState(noSubmit);
		heelPerson.setCreateBy(user);
		heelPerson.setCreateDate(new Date());
		heelPerson.setUpdateBy(user);
		heelPerson.setUpdateDate(new Date());
		spotCheckSinglePersonDao.insert(heelPerson);
		
		//保存单项
	}
	
	
	//获取班级所有人
	public List<WorkPerson> findWpByClassId(String classId){
		WorkPerson query = new WorkPerson();
		query.setWorkClassId(classId);
		return workPersonDao.findAllList(query);
	}
	
}