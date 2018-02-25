package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.WorkClassDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkDepartmentDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.entity.WorkDepartment;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShop;

/**
 * 微信基础数据
 * @author Administrator
 *
 */
@Service
public class WxBaseService extends BaseService implements InitializingBean {

	@Autowired
	private WorkPersonDao workPersonDao;

	@Autowired
	private WorkClassDao workClassDao;
	
	@Autowired
	private WorkDepartmentDao workDepartmentDao;
	
	@Autowired
	private WorkShopDao workShopDao;
	
	@Autowired
	private WorkShopMaskDao workShopMaskDao;
	@Autowired
	private WsMaskWcDao wsMaskWcDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	//获取个人信息
	public WorkPerson findWpByEmpNo(String empNo) {
		return workPersonDao.findByEmpNo(empNo);
	}
	
	//获取班级信息
	public WorkClass findWpId(String classId) {
		return workClassDao.get(classId);
	}
	
	//获取部门信息
	public WorkDepartment findWdById(String departmentId) {
		return workDepartmentDao.get(departmentId);
	}
	
	//获取车间信息
	public WorkShop findWsById(String workShopId) {
		return workShopDao.get(workShopId);
	}
	

}
