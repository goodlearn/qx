/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysWxUserCheck;

/**
 * 信信息检查表DAO接口
 * @author wzy
 * @version 2018-01-06
 */
@MyBatisDao
public interface SysWxUserCheckDao extends CrudDao<SysWxUserCheck> {
	
	public SysWxUserCheck findByIdCardAndPhone(String idCard,String phone,String state);
	
	public SysWxUserCheck findByOpenId(String openId);
	
}