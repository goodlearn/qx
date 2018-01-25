package com.thinkgem.jeesite.modules.sys.state;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
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

	public NormalWsmPageState(StateParam param) {
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
		List<WsMaskWc> wmsList = wsMaskWcDao.findAllList(query);
		if(null == wmsList || wmsList.size() == 0) {
			//没有发布过任务
			//没有发布
			param.setResultCode(param.MESSAGE_CODE);//设置结果码
			param.setValue(param.NO_PUBLISH_CLASS_MASK);//班长还未分配任务
			return;
		}
		
		query.setSubmitState(no);//未提交的
		query.setEndDate(new Date());//当前时间小于结束的时间
		List<WsMaskWc> expired = wsMaskWcDao.findAllList(query);
		if(null != expired && expired.size() > 0) {
			//有 没有处理的任务 未过期 未提交
			param.setResultCode(param.URL_CODE);//设置结果码
			param.setValue(param.NO_CLASS_NO_PROCESS);//还有未处理的任务 去请求URL
			return;
		}
		
		//没有发布
		param.setResultCode(param.MESSAGE_CODE);//设置结果码
		param.setValue(param.NO_PUBLISH_CLASS_MASK);//班长还未分配任务
		return;
	}

}
