package com.thinkgem.jeesite.modules.sys.state;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.sys.dao.SpotCheckMainPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckSinglePersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.SpotCheckSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 
 * 工作人员任务状态页面
 * @author wzy
 *  @version 2018-01-24
 */
public class NormalWsmPageState extends AbsWsmPageState{

	public NormalWsmPageState(ScStateParam param) {
		this.param = param;
	}
	
	@Override
	public void requestHandle(WsmPageStateContext context) {
		
		//找到班级号
		String empNo = param.getEmpNo();
		WorkPersonDao workPersonDao = param.getWorkPersonDao();
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		String classId = person.getWorkClassId();
		
		//依据班级号查询任务
		WsMaskWcDao wsMaskWcDao = param.getWsMaskWcDao();
		WsMaskWc query = new WsMaskWc();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		query.setWorkClassId(classId);
		query.setSubmitState(no);//未提交的
		query.setEndDate(new Date());//当前时间小于结束的时间
		List<WsMaskWc> wmsList = wsMaskWcDao.findList(query);
		if(null == wmsList || wmsList.size() == 0) {
			//没有发布过任务
			//没有发布
			param.getModel().addAttribute("message",ScStateParam.NO_PUBLISH_CLASS_MASK);
			if(param.isPC()) {
				param.setValue(ScStateParam.ERROR_URL_PC);//班长还未分配任务
			}else {
				param.setValue(ScStateParam.ERROR_URL_WX);//班长还未分配任务
			}
			return;
		}
		
		if(!isDuty(wmsList,person.getId())) {
			//没有责任
			param.getModel().addAttribute("message",ScStateParam.NO_PUBLISH_PERSON_MASK);
			if(param.isPC()) {
				param.setValue(ScStateParam.ERROR_URL_PC);
			}else {
				param.setValue(ScStateParam.ERROR_URL_WX);
			}
			return;
		}
		
		//如果是个人负责，进入数据生成
		ScBaCreate scbac = new ScBaCreate();
		scbac.processDispatcher(param);//生成结果内容
		if(param.isPC()) {
			param.setValue(ScStateParam.NO_CLASS_NO_PROCESS_URL_PC);//还有未处理的任务 去请求URL
		}else {
			param.setValue(ScStateParam.NO_CLASS_NO_PROCESS);//还有未处理的任务 去请求URL
		}
		
	}
	
	//是否有我负责的内容
	private boolean isDuty(List<WsMaskWc> wmsList,String empId) {
		SpotCheckMainPersonDao spotCheckMainPersonDao  = param.getSpotCheckMainPersonDao();
		SpotCheckSinglePersonDao spotCheckSinglePersonDao = param.getSpotCheckSinglePersonDao();
		int item = param.getItem();//前部 中部 后部
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		//有 没有处理的任务 未过期 未提交
		//是否是个人负责
		for(WsMaskWc forEntity : wmsList) {
			String wsmId = forEntity.getId();
			SpotCheckMainPerson spotCheckMainPerson = new SpotCheckMainPerson();
			spotCheckMainPerson.setWsMaskWcId(wsmId);//该任务
			spotCheckMainPerson.setSubmitState(no);//没有提交
			List<SpotCheckMainPerson> scmpList = spotCheckMainPersonDao.findList(spotCheckMainPerson);
			for(SpotCheckMainPerson scmpEntity : scmpList) {
				String scmpId = scmpEntity.getId();
				SpotCheckSinglePerson scsp = new SpotCheckSinglePerson();
				scsp.setScmpId(scmpId);//该任务
				scsp.setWorkPersonId(empId);//个人
				scsp.setItem(String.valueOf(item));//部位
				scsp.setSubmitState(no);
				List<SpotCheckSinglePerson> resultList = spotCheckSinglePersonDao.findList(scsp);
				if(null!=resultList && resultList.size() > 0) {
					return true;//有责任
				}
			}
		}
		return false;//无责任
	}
	
}
