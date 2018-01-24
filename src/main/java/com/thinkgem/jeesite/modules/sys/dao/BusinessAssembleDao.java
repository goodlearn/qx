/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;

/**
 * 业务集DAO接口
 * @author wzy
 * @version 2018-01-24
 */
@MyBatisDao
public interface BusinessAssembleDao extends CrudDao<BusinessAssemble> {
	
}