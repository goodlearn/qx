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
	
	
	//点检任务查看
	public Page<WorkShopMask> findSpotCheckPage(Page<WorkShopMask> page, WorkShopMask workShopMask) {
		//获取点检类型
		String type = DictUtils.getDictValue("点检", "businessResultType", "0");
		BusinessAssemble ba = new BusinessAssemble();
		ba.setType(type);
		workShopMask.setBa(ba);
		
		//已经发布
		String releaseState = DictUtils.getDictValue("是", "yes_no", "0");
		workShopMask.setReleaseState(releaseState);
		
		//查看每个人员班级的任务，管理员可以全部查看
		User user = UserUtils.getUser();
		if("1".equals(user.getId())) {
			//管理员
			return super.findPage(page, workShopMask);
		}
		
		String empNo = user.getEmpNo();
		WorkPerson workPerson = workPersonDao.findByEmpNo(empNo);
		if(null == workPerson) {
			return page;//没有数据
		}
		
		
		String classId = workPerson.getWorkClassId();
		if(null == classId) {
			return page;//没有数据
		}
		
		workShopMask.setWorkClassId(classId);
		return super.findPage(page, workShopMask);
	}
	
	//发布任务
	@Transactional(readOnly = false)
	public String release(String paramId) {
		//想将任务表 状态值设置为发布
		WorkShopMask workShopMask = get(paramId);
		if(null == workShopMask) {
			return null;
		}
		
		//更新数据
		workShopMask.setReleaseState("1");
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
		
		//更新数据
		workShopMask.setReleaseState("0");
		save(workShopMask);
		
		//设置关联数据查询条件
		WsMaskWc wsMaskWc = new WsMaskWc();
		wsMaskWc.setWorkShopMaskId(paramId);
		List<WsMaskWc> wsMaskWcs = wsMaskWcDao.findList(wsMaskWc);//查询
		//删除
		for(WsMaskWc forEntity : wsMaskWcs) {
			wsMaskWcDao.delete(forEntity);
		}
		return "0";//返回一个非空数据
	}
	
}