package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;

/**
 * 车间班组DAO接口
 * @author wzy
 * @version 2018-02-05
 */
@MyBatisDao
public interface WorkClassDao extends CrudDao<WorkClass> {
	
}