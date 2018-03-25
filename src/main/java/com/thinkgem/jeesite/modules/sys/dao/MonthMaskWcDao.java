package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWc;

/**
 * 班组任务月度表DAO接口
 * @author wzy
 * @version 2018-03-25
 */
@MyBatisDao
public interface MonthMaskWcDao extends CrudDao<MonthMaskWc> {
	
	public MonthMaskWc getByMmwsId(MonthMaskWc monthMaskWc);//依据车间任务查询
	
	public List<MonthMaskWc> findListAll(MonthMaskWc monthMaskWc);//任务查询
}