package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.MonthMask;

/**
 * 月度任务表DAO接口
 * @author wzy
 * @version 2018-03-25
 */
@MyBatisDao
public interface MonthMaskDao extends CrudDao<MonthMask> {
	
	//查询数量
	public Integer findCountByType(MonthMask MonthMask);
}