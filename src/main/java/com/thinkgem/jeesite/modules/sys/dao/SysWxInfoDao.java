/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;

/**
 * 微信信息表DAO接口
 * @author wzy
 * @version 2017-12-25
 */
@MyBatisDao
public interface SysWxInfoDao extends CrudDao<SysWxInfo> {
	
	/**
	 * 前100名
	 */
	public List<SysWxInfo> findByTotal();
	
	/**
	 * 时间-前100名
	 */
	public List<SysWxInfo> findByTime(String start,String end);
	
	/**
	 * 根据openId查询
	 */
	public SysWxInfo findByOpenId(String openId);
	
	/**
	 * 依据身份证查询
	 */
	public SysWxInfo findByIdCard(String idCard);
	
	/**
	 * 依据openId查询管理员信息
	 */
	public SysWxInfo findOperatorByOpenId(String openId);
	
}