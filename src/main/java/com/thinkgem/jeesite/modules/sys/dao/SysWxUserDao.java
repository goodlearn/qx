/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysWxUser;

/**
 * 微信用户表DAO接口
 * @author wzy
 * @version 2017-12-25
 */
@MyBatisDao
public interface SysWxUserDao extends CrudDao<SysWxUser> {
	
	public SysWxUser findByPhone(String phone);
	
	public SysWxUser findByIdCard(String idCard);
	
	public SysWxUser findByIdCardAndPhone(String idCard,String phone);
}