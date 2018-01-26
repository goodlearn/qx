package com.thinkgem.jeesite.modules.sys.state;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.sys.dao.BusinessResultItemDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckContentDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckMainPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckSinglePersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.BusinessResultItem;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckContent;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 点检结果创建
 * @author Administrator
 *
 */
public class ScBaCreate {

	//进行数据处理
	public void processDispatcher(ScStateParam param) {
		//员工
		String empNo = param.getEmpNo();
		WorkPersonDao workPersonDao = param.getWorkPersonDao();
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		List<SpotCheckContent> sccList = findBriListResult(param,person);
		param.getModel().addAttribute("scccList",sccList);
	}

	
	
	//找到所属任务
	private List<WsMaskWc> findWsmwList(ScStateParam param,WorkPerson person){
		WsMaskWcDao wsMaskWcDao = param.getWsMaskWcDao();
		WsMaskWc query = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		query.setWorkClassId(person.getWorkClassId());
		query.setSubmitState(no);//未提交的
		query.setEndDate(new Date());//当前时间小于结束的时间
		return wsMaskWcDao.findList(query);
	}
	
	//找到个人项
	private List<SpotCheckContent> findBriListResult(ScStateParam param,WorkPerson person){
		List<SpotCheckContent> sccList = new ArrayList<SpotCheckContent>();
		SpotCheckMainPersonDao spotCheckMainPersonDao  = param.getSpotCheckMainPersonDao();
		SpotCheckSinglePersonDao spotCheckSinglePersonDao = param.getSpotCheckSinglePersonDao();
		List<WsMaskWc> wmsList = findWsmwList(param,person);//找到所属任务
		int item = param.getItem();//前部 中部 后部
		for(WsMaskWc forEntity : wmsList) {
			String wsmId = forEntity.getId();
			SpotCheckMainPerson spotCheckMainPerson = new SpotCheckMainPerson();
			spotCheckMainPerson.setWsMaskWcId(wsmId);//该任务
			List<SpotCheckMainPerson> scmpList = spotCheckMainPersonDao.findList(spotCheckMainPerson);
			for(SpotCheckMainPerson scmpEntity : scmpList) {
				String scmpId = scmpEntity.getId();
				SpotCheckSinglePerson scsp = new SpotCheckSinglePerson();
				scsp.setScmpId(scmpId);//该任务
				scsp.setWorkPersonId(person.getId());//个人
				scsp.setItem(String.valueOf(item));//部位
				List<SpotCheckSinglePerson> resultList = spotCheckSinglePersonDao.findList(scsp);
				sccList.addAll(findBriList(param,person,forEntity,resultList));
			}
		}
		return sccList;
	}
	
	//找到所有的结果项
	private List<SpotCheckContent> findBriList(ScStateParam param,WorkPerson person,WsMaskWc wsMaskWc,List<SpotCheckSinglePerson> scspList){
		List<SpotCheckContent> sccList = null;
		SpotCheckContentDao spotCheckContentDao = param.getSpotCheckContentDao();
		
		WorkShopMaskDao workShopMaskDao = param.getWorkShopMaskDao();
		//将每一项的结果补充
		for(SpotCheckSinglePerson sccp : scspList) {
			SpotCheckContent scc = new SpotCheckContent();
			scc.setScspId(sccp.getId());
			sccList = spotCheckContentDao.findList(scc);
			for(SpotCheckContent forEntity : sccList) {
				BusinessResultItemDao businessResultItemDao = param.getBusinessResultItemDao();
				String workShopMaskId = wsMaskWc.getWorkShopMaskId();
				WorkShopMask workShopMask = workShopMaskDao.get(workShopMaskId);
				BusinessAssemble ba = workShopMask.getBa();
				BusinessResultItem businessResultItem = new BusinessResultItem();
				businessResultItem.setAssembleId(ba.getId());
				List<BusinessResultItem> findList = businessResultItemDao.findList(businessResultItem);
				forEntity.setBriList(findList);
			}
			
		}
		return sccList;
	}
	
	//找到所有
}
