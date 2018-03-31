package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
	
	@Transactional(readOnly = false)
	public void save(MonthMaskWc monthMaskWc) {
		super.save(monthMaskWc);
	}
	
	@Transactional(readOnly = false)
	public void saveWxEntity(MonthMaskWc monthMaskWc,String empNo) {
		User user = UserUtils.findByEmpNo(empNo);
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
	
	//获取列表数据
	//依据车间任务 查询班组任务
	public Page<MonthMaskWc> findPageAll(Page<MonthMaskWc> page, MonthMaskWc monthMaskWc,List<MonthMaskWs> listMmws) {
		List<MonthMaskWc> result = new ArrayList<MonthMaskWc>();
		if(null != listMmws && listMmws.size()>0) {
			MonthMaskWc queryMmw = new MonthMaskWc();
			for(MonthMaskWs forEntity : listMmws) {
				String mmwsId = forEntity.getId();
				queryMmw.setMonthMaskWsId(mmwsId);
				MonthMaskWc mmwcResult = dao.getByMmwsId(queryMmw);
				if(null!=mmwcResult) {
					mmwcResult.setMmws(forEntity);
					result.add(mmwcResult);//获得数据
				}
			}
			monthMaskWc.setPage(page);
			if(result.size()>0) {
				page.setList(result);
			}
		}
		return page;
	}
	
}