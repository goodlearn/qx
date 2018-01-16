package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysExpress;

/**
 * 快递信息表DAO接口
 * @author wzy
 * @version 2017-12-25
 */
@MyBatisDao
public interface SysExpressDao extends CrudDao<SysExpress> {

	//获取未取货的快递信息
	public List<SysExpress> findUnEndList(SysExpress sysExpress);
	
	//获取快递
	public List<SysExpress> findByIdCard(String idCard);
	
	//获取快递
	public List<SysExpress> findNoActiveByIdCard(String idCard);
	
	//获取快递
	public SysExpress findByExpressId(String expressId);
	
	//获取取货码
	public Integer findMaxPickUp(String company,String enterTime);
	
}