package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;

/**
 * 车间任务DAO接口
 * @author Wzy
 * @version 2018-01-24
 */
@MyBatisDao
public interface WorkShopMaskDao extends CrudDao<WorkShopMask> {
	
	public List<WorkShopMask> findWxList(WorkShopMask workShopMask);
	
}