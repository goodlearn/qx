/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysExpress;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.SysWxUser;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.SysExpressDao;
import com.thinkgem.jeesite.modules.sys.dao.SysWxInfoDao;
import com.thinkgem.jeesite.modules.sys.dao.SysWxUserDao;

/**
 * 快递信息表Service
 * @author wzy
 * @version 2017-12-25
 */
@Service
@Transactional(readOnly = true)
public class SysExpressService extends CrudService<SysExpressDao, SysExpress> {
	
	@Autowired
	private SysWxUserDao sysWxUserDao;
	@Autowired
	private SysWxInfoDao sysWxInfoDao;
	@Autowired
	private WxService wxService;

	public SysExpress get(String id) {
		return super.get(id);
	}
	
	//查询快递详细信息
	public SysExpress findDetailExpressInfo(SysExpress sysExpress) {
		//根据快递单中的电话查询个人信息
		//快递信息在页面进入的时候就会查询出来，所以这个部分不需要查询
		String phone = sysExpress.getPhone();
		SysWxUser sysWxUser = sysWxUserDao.findByPhone(phone);
		sysExpress.setSysWxUser(sysWxUser);
		return sysExpress;
	}
	
	public List<SysExpress> findList(SysExpress sysExpress) {
		return super.findList(sysExpress);
	}
	
	
	//获取未取货的快递单分页信息
	public Page<SysExpress> findUnEndPage(Page<SysExpress> page, SysExpress sysExpress) {
		sysExpress.setPage(page);
		page.setList(dao.findUnEndList(sysExpress));
		return page;
	}
	
	public Page<SysExpress> findPage(Page<SysExpress> page, SysExpress sysExpress) {
		return super.findPage(page, sysExpress);
	}
	
	//批量操作
	@Transactional(readOnly = false)
	public void saveBatchEnd(String[] sysExpressIds) {
		String state = DictUtils.getDictValue("已完结", "expressState", "0");
		List<SysExpress> sysExpresses = new ArrayList<SysExpress>();
		for(String sysExpressId:sysExpressIds) {
			SysExpress sysExpress = get(sysExpressId);
			sysExpress.setState(state);
			sysExpresses.add(sysExpress);
			System.out.println("批量操作取货:"+sysExpressId);
		}
		for(SysExpress sysExpress:sysExpresses) {
			save(sysExpress);
		}
	}
	
	//快递信息状态查询
	private String queryMsgState(SysExpress sysExpress) {
		
		String dictType = "expressMsgState";
		
		String phone = sysExpress.getPhone();
		
		//如果快递没有电话 那么不进行消息发送
		if(null == phone) {
			return DictUtils.getDictValue("快递单未绑定电话", dictType, "0");
		}
		
		//查询个人信息
		SysWxUser sysWxUser = sysWxUserDao.findByPhone(phone);
		//如果快递没有用户  那么不进行消息发送
		if(null == sysWxUser) {
			return DictUtils.getDictValue("没有关联的用户", dictType, "0");
		}
		
		String idCard = sysWxUser.getIdCard();
		//如果用户没有绑定身份信息  那么不进行消息发送
		if(null == idCard) {
			return DictUtils.getDictValue("用户未绑定身份信息", dictType, "0");
		}
		
		//查询微信号 微信没有绑定 不进行消息发送
		SysWxInfo sysWxInfo = sysWxInfoDao.findByIdCard(idCard);
		if(null == sysWxInfo) {
			return DictUtils.getDictValue("用户未关联微信", dictType, "0");
		}
		String openId = sysWxInfo.getOpenId();
		//如果用户没有绑定微信信息  那么不进行消息发送
		if(null == openId) {
			return DictUtils.getDictValue("用户未进行微信授权", dictType, "0");
		}
		
		return DictUtils.getDictValue("入库信息已发送", dictType, "0");
	}
	
	//入库后发送模板消息
	@Transactional(readOnly = false)
	public void save(SysExpress sysExpress) {
		
		//默认保存数据
		sysExpress.setMsgState(queryMsgState(sysExpress));
		super.save(sysExpress);
		
		/**
		 * 发送模板消息
		 */
		
		User user = UserUtils.getUser();
		String phone = sysExpress.getPhone();
		
		//如果快递没有电话 那么不进行消息发送
		if(null == phone) {
			return;
		}
		logger.info("phone is "+phone);
		
		//查询个人信息
		SysWxUser sysWxUser = sysWxUserDao.findByPhone(phone);
		//如果快递没有用户  那么不进行消息发送
		if(null == sysWxUser) {
			return;
		}
		
		String idCard = sysWxUser.getIdCard();
		//如果用户没有绑定身份信息  那么不进行消息发送
		if(null == idCard) {
			return;
		}
		logger.info("idCard is "+idCard);
		
		//查询微信号 微信没有绑定 不进行消息发送
		SysWxInfo sysWxInfo = sysWxInfoDao.findByIdCard(idCard);
		if(null == sysWxInfo) {
			return;
		}
		String openId = sysWxInfo.getOpenId();
		//如果用户没有绑定微信信息  那么不进行消息发送
		if(null == openId) {
			return;
		}
		logger.info("openId is "+openId);
		String userName = user.getName();
		wxService.sendMessageExpress(openId,userName,"0");
	}
	
	@Transactional(readOnly = false)
	public void delete(SysExpress sysExpress) {
		super.delete(sysExpress);
	}
	
}