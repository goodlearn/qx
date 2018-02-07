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
	
	/**
	 * 验证今天是不是已经发布过任务了
	 * 空值为正常
	 * 非空值为返回消息
	 * @param wsmId
	 * @return
	 */
	public String validateReleasePd(String wsmId) {
		WsMaskWc query = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		query.setWorkShopMaskId(wsmId);
		query.setSubmitState(no);//未提交的
		query.setEndDate(new Date());//当前时间小于结束的时间
		List<WsMaskWc> expired = dao.findList(query);
		if(null != expired && expired.size() > 0) {
			//有 没有处理的任务 未过期 未提交
			return "该任务还为结束或者没有提交";
		}
		return null ;
	}
	
	//是否有未提交的数据
	private boolean isNotSubmit(String empNo) {
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		String classId = person.getWorkClassId();
		
		//依据班级号查询任务
		WsMaskWc query = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		query.setWorkClassId(classId);
		List<WsMaskWc> wmsList = dao.findList(query);
		if(null == wmsList || wmsList.size() == 0) {
			//没有发布过任务
			//没有发布
			return false;
		}
		
		query.setSubmitState(no);//未提交的
		query.setEndDate(new Date());//当前时间小于结束的时间
		List<WsMaskWc> expired = dao.findList(query);
		if(null != expired && expired.size() > 0) {
			//有 没有处理的任务 未过期 未提交
			return true;
		}
		return false;
	}
	
	//每日发布任务
	@Transactional(readOnly = false)
	public void releasePd(String wsmId) {
		WorkShopMask workShopMask = workShopMaskDao.get(wsmId);
		if(null == workShopMask) {
			return;
		}
		//班级任务生成
		User user = UserUtils.getUser();
		WsMaskWc wsMaskWc = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		wsMaskWc.setId(IdGen.uuid());
		wsMaskWc.setWorkClassId(workShopMask.getWorkClassId());
		wsMaskWc.setWorkShopMaskId(workShopMask.getId());
		wsMaskWc.setSubmitState(no);//未提交的
		wsMaskWc.setCreateBy(user);
		wsMaskWc.setCreateDate(new Date());
		wsMaskWc.setUpdateBy(user);
		wsMaskWc.setUpdateDate(new Date());
		wsMaskWc.setEndDate(Date2Utils.getEndDayOfTomorrow());
		dao.insert(wsMaskWc);
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
	
	
	
	
	//获取班级所有人
	public List<WorkPerson> findWpByWsmId(String wsmid){
		WorkShopMask wsm = workShopMaskDao.get(wsmid);
		WorkPerson query = new WorkPerson();
		query.setWorkClassId(wsm.getWorkClassId());
		return workPersonDao.findAllList(query);
	}
	
}