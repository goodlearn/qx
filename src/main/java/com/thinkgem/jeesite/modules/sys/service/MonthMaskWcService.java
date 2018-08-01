package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWc;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWs;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.MonthMaskWcDao;
import com.thinkgem.jeesite.modules.sys.dao.MonthMaskWsDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkClassDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;

/**
 * 班组任务月度表Service
 * @author wzy
 * @version 2018-03-25
 */
@Service
@Transactional(readOnly = true)
public class MonthMaskWcService extends CrudService<MonthMaskWcDao, MonthMaskWc> {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private WorkClassDao workClassDao;
	
	@Autowired
	private WorkPersonDao workPersonDao;
	
	@Autowired
	private MonthMaskWsDao monthMaskWsDao;
	
	public MonthMaskWc get(String id) {
		return super.get(id);
	}
	
	public List<MonthMaskWc> findList(MonthMaskWc monthMaskWc) {
		return super.findList(monthMaskWc);
	}
	
	public List<MonthMaskWc> findListAllByMmc(MonthMaskWc monthMaskWc,String mmwsId) {
		
		String value = DictUtils.getDictValue("是", "yes_no", "是");
		MonthMaskWs queryMmw = new MonthMaskWs();
		queryMmw.setEndDate(new Date());
		queryMmw.setSubmitState(value);//依据发布的
		
		MonthMaskWc queryMmwc = new MonthMaskWc();
		queryMmwc.setMmws(queryMmw);
		
		if(UserUtils.getUser().isAdmin()) {
			return dao.findList(queryMmwc);
		}
		//查询员工号
		String empNo = UserUtils.getUser().getEmpNo();
		if(null == empNo) {
			return null;//空数据
		}
		WorkPerson resultWp = new WorkPerson();
		resultWp = workPersonDao.findByEmpNo(empNo);
		String classId = resultWp.getWorkClassId();//查询班级
		WorkClass resultWc = workClassDao.get(classId);//查询班级
		queryMmwc.setWorkClassId(classId);
		queryMmwc.setMonthMaskWsId(mmwsId);
		return dao.findListAll(queryMmwc);
	}
	
	public Page<MonthMaskWc> findPage(Page<MonthMaskWc> page, MonthMaskWc monthMaskWc) {
		
		if(UserUtils.getUser().isAdmin()) {
			return super.findPage(page, monthMaskWc);
		}
		
		//查询员工号
		String empNo = UserUtils.getUser().getEmpNo();
		if(null == empNo) {
			return page;//空数据
		}
		
		WorkPerson resultWp = new WorkPerson();
		resultWp = workPersonDao.findByEmpNo(empNo);
		monthMaskWc.setWp(resultWp);
		monthMaskWc.setPage(page);
		page.setList(dao.findListAll(monthMaskWc));
		return page;
	}
	
	/**
	 * 只查看班组的任务 不查看个人的
	 */
	public Page<MonthMaskWc> findClassPage(Page<MonthMaskWc> page, MonthMaskWc monthMaskWc) {
		
		if(UserUtils.getUser().isAdmin()) {
			return super.findPage(page, monthMaskWc);
		}
		
		String userId = UserUtils.getUser().getId();
		if(userId != null && "2".equals(userId)) {
			return super.findPage(page, monthMaskWc);
		}
		
		//查询员工号
		String empNo = UserUtils.getUser().getEmpNo();
		if(null == empNo) {
			return page;//空数据
		}
		
		WorkPerson wpByEp = workPersonDao.findByEmpNo(empNo);
		monthMaskWc.setWorkClassId(wpByEp.getWorkClassId());
		WorkClass wc = new WorkClass();
		wc.setWorkKindId(wpByEp.getWk().getId());
		monthMaskWc.setWc(wc);
		monthMaskWc.setPage(page);
		page.setList(dao.findListAll(monthMaskWc));
		return page;
	}
	
	/**
	 * 只查看班组的任务 不查看个人的(微信不分页)
	 */
	public List<MonthMaskWc> findClassMcs(String empNo) {
		
		//查询员工号
		if(null == empNo) {
			return null;//空数据
		}
		
		
		MonthMaskWc monthMaskWc = new MonthMaskWc();
		WorkPerson wpByEp = workPersonDao.findByEmpNo(empNo);
		monthMaskWc.setWorkClassId(wpByEp.getWorkClassId());
		WorkClass wc = new WorkClass();
		wc.setWorkKindId(wpByEp.getWk().getId());
		monthMaskWc.setWc(wc);
		return dao.findListAll(monthMaskWc);
	}
	
	@Transactional(readOnly = false)
	public void save(MonthMaskWc monthMaskWc) {
		super.save(monthMaskWc);
	}
	
	@Transactional(readOnly = false)
	public void saveWxEntity(MonthMaskWc monthMaskWc) {
		User user =userDao.get(Global.DEFAULT_ID_SYS_MANAGER);
		if (monthMaskWc.getIsNewRecord()){
			monthMaskWc.setId(IdGen.uuid());
			monthMaskWc.setUpdateBy(user);
			monthMaskWc.setCreateBy(user);
			monthMaskWc.setUpdateDate(new Date());
			monthMaskWc.setCreateDate(monthMaskWc.getUpdateDate());
			dao.insert(monthMaskWc);
		}else{
			monthMaskWc.setUpdateDate(new Date());
			monthMaskWc.setUpdateBy(user);
			dao.update(monthMaskWc);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(MonthMaskWc monthMaskWc) {
		super.delete(monthMaskWc);
	}
	
	//查询发布任务
	public List<MonthMaskWc> findMaskMmwcs(){
		
		String value = DictUtils.getDictValue("是", "yes_no", "是");
		MonthMaskWs queryMmw = new MonthMaskWs();
		queryMmw.setEndDate(new Date());
		queryMmw.setSubmitState(value);//依据发布的
		
		MonthMaskWc queryMmwc = new MonthMaskWc();
		queryMmwc.setMmws(queryMmw);
		
		if(UserUtils.getUser().isAdmin()) {
			return dao.findListAll(queryMmwc);
		}
		
		//查询员工号
		String empNo = UserUtils.getUser().getEmpNo();
		if(null == empNo) {
			return null;//空数据
		}
		
		WorkPerson resultWp = new WorkPerson();
		resultWp = workPersonDao.findByEmpNo(empNo);
		String classId = resultWp.getWorkClassId();//查询班级
		WorkClass resultWc = workClassDao.get(classId);//查询班级
		String wkId = resultWc.getWorkKindId();//查询工种

		queryMmw.setWorkKindId(wkId);//设置工种查询
		return dao.findListAll(queryMmwc);
	}
	
	//查询当前用户的任务 已发布车间任务的班组任务
	public List<MonthMaskWc> findMaskMmwcs(String empNo){
		//查询员工号
		if(null == empNo) {
			return null;//空数据
		}
		
		MonthMaskWc queryMmwc = new MonthMaskWc();
		
		//查询人员
		WorkPerson resultWp = new WorkPerson();
		resultWp = workPersonDao.findByEmpNo(empNo);
		
		//设置查询条件
		String value = DictUtils.getDictValue("是", "yes_no", "是");
		MonthMaskWs queryMmw = new MonthMaskWs();
		
		queryMmw.setEndDate(new Date());//时间
		queryMmw.setSubmitState(value);//依据发布的
		queryMmwc.setMmws(queryMmw);
		queryMmwc.setWp(resultWp);//设置班级
		queryMmwc.setWorkPersonId(resultWp.getId());//这个人的任务
		
		//员工的任务列表
		List<MonthMaskWc> ret = dao.findListAll(queryMmwc);
		return ret;
	}
	
}