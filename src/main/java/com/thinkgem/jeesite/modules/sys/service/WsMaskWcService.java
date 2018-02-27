package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.Date2Utils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;

/**
 * 车间任务班级关联Service
 * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class WsMaskWcService extends CrudService<WsMaskWcDao, WsMaskWc> {
	
	@Autowired
	private WorkPersonDao workPersonDao;
	@Autowired
	private WorkShopMaskDao workShopMaskDao;

	public WsMaskWc get(String id) {
		return super.get(id);
	}
	
	public List<WsMaskWc> findList(WsMaskWc wsMaskWc) {
		return super.findList(wsMaskWc);
	}
	
	public Page<WsMaskWc> findPage(Page<WsMaskWc> page, WsMaskWc wsMaskWc) {
		return super.findPage(page, wsMaskWc);
	}
	
	@Transactional(readOnly = false)
	public void save(WsMaskWc wsMaskWc) {
		super.save(wsMaskWc);
	}
	
	@Transactional(readOnly = false)
	public void delete(WsMaskWc wsMaskWc) {
		super.delete(wsMaskWc);
	}
	
	/**
	 * 依据类型查询
	 */
	public Page<WsMaskWc> findTypeListPage(Page<WsMaskWc> page, WsMaskWc wsMaskWc) {
		wsMaskWc.setPage(page);
		page.setList(dao.findTypeList(wsMaskWc));
		return page;
	}
	
	public String validateSubmitState(String wmwId) {
		WsMaskWc query = new WsMaskWc();
		query.setId(wmwId);
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		query.setSubmitState(no);//未提交的
		List<WsMaskWc> exitList = dao.findList(query);
		if(null!=exitList && exitList.size() > 0) {
			return wmwId;
		}
		return null;
	}
	
	/**
	 * 提交的任务
	 */
	public WsMaskWc findSubmitMask(String id) {
		WsMaskWc queryWmw = dao.get(id);
		String state = queryWmw.getSubmitState();
		String yes = DictUtils.getDictValue("是", "yes_no", "0");
		if(yes.equals(state)) {
			return queryWmw;
		}
		return null;
	}
	
	/**
	 * 验证今天是不是已经发布过任务了
	 * 空值为正常
	 * 非空值为返回消息
	 * @param wsmId
	 * @return
	 */
	public String validateReleasePd(String wsmId) {
		try {
			WsMaskWc query = new WsMaskWc();
			query.setWorkShopMaskId(wsmId);
			String no = DictUtils.getDictValue("否", "yes_no", "0");
			String dateParam = CasUtils.convertDate2YMDString(new Date());
			query.setSubmitState(no);//未提交的
			Date date = null;
			date = CasUtils.convertString2YMDDate(dateParam);
			String beginDate = CasUtils.convertDate2HMSString(Date2Utils.getDayStartTime(date));
			String endDate = CasUtils.convertDate2HMSString(Date2Utils.getDayEndTime(date));
			query.setBeginQueryDate(beginDate);
			query.setEndQueryDate(endDate);
			List<WsMaskWc> exitList = dao.findList(query);
			if(null!=exitList && exitList.size() > 0) {
				return "今天已经发布过任务";
			}
			query.setEndDate(new Date());//当前时间小于结束的时间
			List<WsMaskWc> expired = dao.findList(query);
			if(null != expired && expired.size() > 0) {
				//有 没有处理的任务 未过期 未提交
				return "该任务还未结束";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	/**
	 * 任务是否为空
	 */
	public List<WsMaskWc> validateWsmId(String wsmId) {
		WsMaskWc query = new WsMaskWc();
		query.setWorkShopMaskId(wsmId);
		List<WsMaskWc> entities = dao.findList(query);
		if(null == entities) {
			return null;
		}
		return entities ;
	}
	
	/**
	 * 特定时间范围内的数据
	 */
	public List<WsMaskWc> findMaskInClass(String empNo,String beginDate,String endDate){
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		String classId = person.getWorkClassId();
		
		//依据班级号查询任务
		WsMaskWc query = new WsMaskWc();
		query.setWorkClassId(classId);
		query.setBeginQueryDate(beginDate);
		query.setEndQueryDate(endDate);
		List<WsMaskWc> wmsList = dao.findList(query);
		if(null == wmsList || wmsList.size() == 0) {
			//没有发布过任务
			//没有发布
			return null;
		}
		return wmsList;
	}
	
	/**
	 * 所在班级是否有未提交的数据
	 * @param empNo
	 * @return
	 */
	public List<WsMaskWc> findUnFinishMaskInClass(String empNo) {
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		String classId = person.getWorkClassId();
		
		//依据班级号查询任务
		WsMaskWc query = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		query.setWorkClassId(classId);
		List<WsMaskWc> wmsList = dao.findList(query);
		if(null == wmsList || wmsList.size() == 0) {
			//没有发布过任务
			//没有发布
			return null;
		}
		
		query.setSubmitState(no);//未提交的
		query.setEndDate(new Date());//当前时间小于结束的时间
		List<WsMaskWc> expired = dao.findList(query);
		if(null != expired && expired.size() > 0) {
			//有 没有处理的任务 未过期 未提交
			return expired;
		}
		return null;
	}
	
	//每日发布任务
	@Transactional(readOnly = false)
	public WsMaskWc releasePd(String wsmId,User user) {
		WorkShopMask workShopMask = workShopMaskDao.get(wsmId);
		if(null == workShopMask) {
			return null;
		}
		//班级任务生成
		WsMaskWc wsMaskWc = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		wsMaskWc.setId(IdGen.uuid());
		wsMaskWc.setWorkClassId(workShopMask.getWorkClassId());
		wsMaskWc.setWorkShopMaskId(workShopMask.getId());
		wsMaskWc.setSubmitState(no);//未提交的
		wsMaskWc.setRunTime("0");
		wsMaskWc.setCreateBy(user);
		wsMaskWc.setCreateDate(new Date());
		wsMaskWc.setUpdateBy(user);
		wsMaskWc.setUpdateDate(new Date());
		wsMaskWc.setEndDate(Date2Utils.getBeginDayOfTomorrow());
		dao.insert(wsMaskWc);
		return wsMaskWc;
	}
	
	//未提交数据列表
	public Page<WsMaskWc> findUnSubmitList(Page<WsMaskWc> page, WsMaskWc wsMaskWc) {
		User user = UserUtils.getUser();
		String empNo = user.getEmpNo();
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		String classId = person.getWorkClassId();
		//依据班级号查询任务
		WsMaskWc query = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		query.setWorkClassId(classId);
		query.setSubmitState(no);//未提交的
		query.setEndDate(new Date());//当前时间小于结束的时间
		return super.findPage(page, query);
	}
	
	//结束任务
	@Transactional(readOnly = false)
	public WsMaskWc endMask(WsMaskWc wsMaskWc) {
		User user = UserUtils.getUser();
		String yes = DictUtils.getDictValue("是", "yes_no", "1");
		wsMaskWc.setSubmitState(yes);//未提交的
		wsMaskWc.setUpdateBy(user);
		wsMaskWc.setUpdateDate(new Date());
		dao.update(wsMaskWc);
		return wsMaskWc;
	}
	
	//提交任务
	@Transactional(readOnly = false)
	public WsMaskWc submitMask(WsMaskWc wsMaskWc,User user) {
		String yes = DictUtils.getDictValue("是", "yes_no", "1");
		wsMaskWc.setSubmitState(yes);//提交
		wsMaskWc.setUpdateBy(user);
		wsMaskWc.setUpdateDate(new Date());
		dao.update(wsMaskWc);
		return wsMaskWc;
	}
	
	//保存任务
	@Transactional(readOnly = false)
	public WsMaskWc save(String wsmid) {
		WorkShopMask wsm = workShopMaskDao.get(wsmid);
		User user = UserUtils.getUser();
		WsMaskWc wsMaskWc = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		wsMaskWc.setId(IdGen.uuid());
		wsMaskWc.setWorkClassId(wsm.getWorkClassId());
		wsMaskWc.setWorkShopMaskId(wsmid);
		wsMaskWc.setSubmitState(no);//未提交的
		wsMaskWc.setCreateBy(user);
		wsMaskWc.setCreateDate(new Date());
		wsMaskWc.setUpdateBy(user);
		wsMaskWc.setUpdateDate(new Date());
		wsMaskWc.setEndDate(Date2Utils.getEndDayOfTomorrow());
		dao.insert(wsMaskWc);
		return wsMaskWc;
	}
	
	
	
	
	//获取班级所有人
	public List<WorkPerson> findWpByWsmId(String wsmid){
		WorkShopMask wsm = workShopMaskDao.get(wsmid);
		WorkPerson query = new WorkPerson();
		query.setWorkClassId(wsm.getWorkClassId());
		return workPersonDao.findAllList(query);
	}
	
}