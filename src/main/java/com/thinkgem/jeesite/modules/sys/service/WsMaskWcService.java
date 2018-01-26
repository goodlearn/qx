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
import com.thinkgem.jeesite.modules.sys.entity.CheckSpotItem;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckContent;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.CheckSpotItemDao;
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
	private WorkShopMaskDao workShopMaskDao;
	@Autowired
	private CheckSpotItemDao checkSpotItemDao;
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
	
	//未提交数据列表
	public Page<WsMaskWc> findUnSubmitList(Page<WsMaskWc> page, WsMaskWc wsMaskWc) {
		User user = UserUtils.getUser();
		String empNo = user.getEmpNo();
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		String classId = person.getWorkClassId();
		//依据班级号查询任务
		WsMaskWc query = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		query.setWorkClassId(classId);
		query.setSubmitState(no);//未提交的
		query.setEndDate(new Date());//当前时间小于结束的时间
		return super.findPage(page, query);
	}
	
	//结束任务
	@Transactional(readOnly = false)
	public WsMaskWc endMask(WsMaskWc wsMaskWc) {
		User user = UserUtils.getUser();
		String yes = DictUtils.getDictValue("是", "yes_no", "1");
		wsMaskWc.setSubmitState(yes);//未提交的
		wsMaskWc.setUpdateBy(user);
		wsMaskWc.setUpdateDate(new Date());
		dao.update(wsMaskWc);
		return wsMaskWc;
	}
	
	//保存任务
	@Transactional(readOnly = false)
	public WsMaskWc save(String wsmid) {
		WorkShopMask wsm = workShopMaskDao.get(wsmid);
		User user = UserUtils.getUser();
		WsMaskWc wsMaskWc = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		wsMaskWc.setId(IdGen.uuid());
		wsMaskWc.setWorkClassId(wsm.getWorkClassId());
		wsMaskWc.setWorkShopMaskId(wsmid);
		wsMaskWc.setSubmitState(no);//未提交的
		wsMaskWc.setCreateBy(user);
		wsMaskWc.setCreateDate(new Date());
		wsMaskWc.setUpdateBy(user);
		wsMaskWc.setUpdateDate(new Date());
		wsMaskWc.setEndDate(Date2Utils.getEndDayOfTomorrow());
		dao.insert(wsMaskWc);
		return wsMaskWc;
	}
	
	
	//分配任务
	@Transactional(readOnly = false)
	public void saveScMask(WsMaskWc wsMaskWc) {
		
		//提交状态
		String noSubmit = DictUtils.getDictValue("否", "yes_no", "0");
		
		//先保存总负责人
		User user = UserUtils.getUser();
		String empNo = user.getEmpNo();
		WorkPerson wp = workPersonDao.findByEmpNo(empNo);//员工
		String wsMaskWcId = wsMaskWc.getId();//任务关联
		String workShopId = wsMaskWc.getWorkShopMaskId();
		WorkShopMask workShopMask = workShopMaskDao.get(workShopId);//车间任务
		String bussinessAssesmbleId = workShopMask.getBussinessAssembleId();//车间任务集
		//查找任务内容
		CheckSpotItem csitem = new CheckSpotItem();
		csitem.setAssembleId(bussinessAssesmbleId);
		
		
		//主负责人
		SpotCheckMainPerson spotCheckMainPerson = new SpotCheckMainPerson();
		String mainId = IdGen.uuid();
		spotCheckMainPerson.setId(mainId);
		spotCheckMainPerson.setWsMaskWcId(wsMaskWcId);
		spotCheckMainPerson.setWorkPersonId(wp.getId());
		spotCheckMainPerson.setRunTime("0");
		spotCheckMainPerson.setSubmitState(noSubmit);
		spotCheckMainPerson.setCreateBy(user);
		spotCheckMainPerson.setCreateDate(new Date());
		spotCheckMainPerson.setUpdateBy(user);
		spotCheckMainPerson.setUpdateDate(new Date());
		spotCheckMainPersonDao.insert(spotCheckMainPerson);
		//保存分部负责人
		
		//前部
		String frontId = wsMaskWc.getFrontPerson();
		String forntItem = DictUtils.getDictValue("前部", "bussinesItem", "0");
		SpotCheckSinglePerson frontScsp = new SpotCheckSinglePerson();
		String frontScspId = IdGen.uuid();
		frontScsp.setId(frontScspId);
		frontScsp.setScmpId(mainId);
		frontScsp.setWorkPersonId(frontId);
		frontScsp.setSubmitState(noSubmit);
		frontScsp.setItem(forntItem);
		frontScsp.setCreateBy(user);
		frontScsp.setCreateDate(new Date());
		frontScsp.setUpdateBy(user);
		frontScsp.setUpdateDate(new Date());
		spotCheckSinglePersonDao.insert(frontScsp);
		csitem.setItem(forntItem);
		List<CheckSpotItem> frontList = checkSpotItemDao.findList(csitem);//获得所有内容
		//前部内容
		for(CheckSpotItem forEntity : frontList) {
			SpotCheckContent entity = new SpotCheckContent();
			entity.setId(IdGen.uuid());
			entity.setOldNew(noSubmit);//旧内容 不是用户添加的
			entity.setPart(forEntity.getPart());//部位
			entity.setContext(forEntity.getContext());//内容
			entity.setResultContent("");//结果为空
			entity.setScspId(frontScspId);
			entity.setCreateBy(user);
			entity.setCreateDate(new Date());
			entity.setUpdateBy(user);
			entity.setUpdateDate(new Date());
			spotCheckContentDao.insert(entity);
		}
		
		 
		
		//中部
		String centralId = wsMaskWc.getCentralPerson();
		String centralItem = DictUtils.getDictValue("中部", "bussinesItem", "1");
		SpotCheckSinglePerson centralPerson = new SpotCheckSinglePerson();
		String centralPersonId = IdGen.uuid();
		centralPerson.setId(centralPersonId);
		centralPerson.setScmpId(mainId);
		centralPerson.setWorkPersonId(centralId);
		centralPerson.setSubmitState(noSubmit);
		centralPerson.setItem(centralItem);
		centralPerson.setCreateBy(user);
		centralPerson.setCreateDate(new Date());
		centralPerson.setUpdateBy(user);
		centralPerson.setUpdateDate(new Date());
		spotCheckSinglePersonDao.insert(centralPerson);
		csitem.setItem(centralItem);
		List<CheckSpotItem> centralList = checkSpotItemDao.findList(csitem);//获得所有内容
		//后部内容
		for(CheckSpotItem forEntity : centralList) {
			SpotCheckContent entity = new SpotCheckContent();
			entity.setId(IdGen.uuid());
			entity.setOldNew(noSubmit);//旧内容 不是用户添加的
			entity.setPart(forEntity.getPart());//部位
			entity.setContext(forEntity.getContext());//内容
			entity.setResultContent("");//结果为空
			entity.setScspId(centralPersonId);
			entity.setCreateBy(user);
			entity.setCreateDate(new Date());
			entity.setUpdateBy(user);
			entity.setUpdateDate(new Date());
			spotCheckContentDao.insert(entity);
		}
		
		//后部
		String heelId = wsMaskWc.getHeelPerson();
		SpotCheckSinglePerson heelPerson = new SpotCheckSinglePerson();
		String heelItem = DictUtils.getDictValue("后部", "bussinesItem", "2");
		String heelPersonId = IdGen.uuid();
		heelPerson.setId(heelPersonId);
		heelPerson.setScmpId(mainId);
		heelPerson.setWorkPersonId(heelId);
		heelPerson.setSubmitState(noSubmit);
		heelPerson.setItem(heelItem);
		heelPerson.setCreateBy(user);
		heelPerson.setCreateDate(new Date());
		heelPerson.setUpdateBy(user);
		heelPerson.setUpdateDate(new Date());
		spotCheckSinglePersonDao.insert(heelPerson);
		csitem.setItem(heelItem);
		List<CheckSpotItem> heelList = checkSpotItemDao.findList(csitem);//获得所有内容
		//后部内容
		for(CheckSpotItem forEntity : heelList) {
			SpotCheckContent entity = new SpotCheckContent();
			entity.setId(IdGen.uuid());
			entity.setOldNew(noSubmit);//旧内容 不是用户添加的
			entity.setPart(forEntity.getPart());//部位
			entity.setContext(forEntity.getContext());//内容
			entity.setResultContent("");//结果为空
			entity.setScspId(heelPersonId);
			entity.setCreateBy(user);
			entity.setCreateDate(new Date());
			entity.setUpdateBy(user);
			entity.setUpdateDate(new Date());
			spotCheckContentDao.insert(entity);
		}
	}
	
	
	//获取班级所有人
	public List<WorkPerson> findWpByWsmId(String wsmid){
		WorkShopMask wsm = workShopMaskDao.get(wsmid);
		WorkPerson query = new WorkPerson();
		query.setWorkClassId(wsm.getWorkClassId());
		return workPersonDao.findAllList(query);
	}
	
}