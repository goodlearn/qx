/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

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
	
}