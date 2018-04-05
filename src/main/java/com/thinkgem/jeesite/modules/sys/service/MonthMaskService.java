package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.MonthMask;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWc;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.entity.WorkKind;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.dao.MonthMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.MonthMaskWcDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkClassDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkKindDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;

/**
 * 月度任务表Service
 * @author wzy
 * @version 2018-03-25
 */
@Service
@Transactional(readOnly = true)
public class MonthMaskService extends CrudService<MonthMaskDao, MonthMask> {
	
	@Autowired
	private MonthMaskWcDao monthMaskWcDao;
	
	@Autowired
	private WorkKindDao workKindDao;
	
	@Autowired
	private WorkPersonDao workPersonDao;
	
	@Autowired
	private WorkClassDao workClassDao;

	public MonthMask get(String id) {
		return super.get(id);
	}
	
	public List<MonthMask> findList(MonthMask monthMask) {
		return super.findList(monthMask);
	}
	
	public Page<MonthMask> findPage(Page<MonthMask> page, MonthMask monthMask) {
		return super.findPage(page, monthMask);
	}
	
	@Transactional(readOnly = false)
	public void save(MonthMask monthMask) {
		super.save(monthMask);
	}
	
	@Transactional(readOnly = false)
	public void saveForm(MonthMask monthMask) {
		String mmwcId = monthMask.getMonthMaskWcId();
		MonthMaskWc mmwc = monthMaskWcDao.get(mmwcId);
		monthMask.setWorkPersonId(mmwc.getWorkPersonId());
		super.save(monthMask);
	}
	
	//查询已经添加任务的数据
	public void setMonthMask(List<MonthMaskWc> mmwsList){
		//如果班组任务为空 不返回数据
		if(null == mmwsList || mmwsList.size() == 0) {
			return;
		}
		
		for(MonthMaskWc forMmws : mmwsList) {
			String forMmwsId = forMmws.getId();
			MonthMask queryMonthMask = new MonthMask();
			queryMonthMask.setMonthMaskWcId(forMmwsId);
			List<MonthMask> mmList = dao.findList(queryMonthMask);//找到班组任务已经添加的任务
			if(null != mmList && mmList.size()>0) {
				//设置数据
				forMmws.setMmList(mmList);
			}
		}
	}
	
	//查询数量
	public boolean validateNum(MonthMask monthMask) {
		String mmwcId = monthMask.getMonthMaskWcId();
		MonthMaskWc mmwc = monthMaskWcDao.get(mmwcId);
		
		//班组分配的人
		String workPersonId = mmwc.getWorkPersonId();
		//班组人员
		WorkPerson wp = workPersonDao.get(workPersonId);
		//班组信息
		WorkClass wc = workClassDao.get(wp.getWorkClassId());
		//工种信息
		WorkKind wk = workKindDao.get(wc.getWorkKindId());
		//分配人员的工种信息
		String workKindId = wk.getId();
		//该工作下、该工种已经完成的任务数量
		MonthMask queryMonthMask = new MonthMask();
		queryMonthMask.setWorkKindId(workKindId);
		queryMonthMask.setMonthMaskWcId(mmwcId);
		int num = dao.findCountByType(queryMonthMask);
		
		//该工种要求的数量
		String monthMaskNum = wk.getMmNum();
		
		//限制数量
		int limitNum = 0;
		//给一个默认值
		if(null == monthMaskNum) {
			limitNum = 0;
		}else {
			limitNum = Integer.valueOf(monthMaskNum);
		}
		
		//如果超出规定数量 返回错误
		if(num >= limitNum) {
			return false;
		}
		return true;
	}
	
	@Transactional(readOnly = false)
	public void delete(MonthMask monthMask) {
		super.delete(monthMask);
	}
	
	@Transactional(readOnly = false)
	public void saveWx(MonthMask monthMask,User user) {
		monthMask.setCheckDate(new Date());
		String mmwcId = monthMask.getMonthMaskWcId();
		MonthMaskWc mmwc = monthMaskWcDao.get(mmwcId);
		monthMask.setWorkPersonId(mmwc.getWorkPersonId());
		if (monthMask.getIsNewRecord()){
			monthMask.setId(IdGen.uuid());
			monthMask.setUpdateBy(user);
			monthMask.setCreateBy(user);
			monthMask.setUpdateDate(new Date());
			monthMask.setCreateDate(monthMask.getUpdateDate());
			dao.insert(monthMask);
		}else{
			monthMask.setUpdateDate(new Date());
			monthMask.setUpdateBy(user);
			dao.update(monthMask);
		}
		super.save(monthMask);
	}
	
}