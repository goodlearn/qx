package com.thinkgem.jeesite.modules.sys.state;

import java.util.Map;

import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 
 * 班长任务状态页面
 * @author wzy
 *  @version 2018-01-24
 */
public class MonitorWsmPageState extends AbsWsmPageState{

	public MonitorWsmPageState(ScStateParam param) {
		this.param = param;
	}
	
	@Override
	public void requestHandle(WsmPageStateContext context) {
		
		//先看看是不是班长
		if(!isMonitor()) {
			//不是班长 让工作人员处理
			NormalWsmPageState normalWsmPageState = new NormalWsmPageState(param);
			normalWsmPageState.requestHandle(context);
			return;
		}
		
		//是班长
		
		/**
		 * 先查看是不是有发布的任务
		 * 如果没有发布的任务，就提示没有发布的任务
		 * 如果有发布的任务，就需要进行任务发布
		 * 发布任务，如果已经发过任务了，那么直接进入任务操作界面
		 * 如果班级级别没有发布任务，进入发布任务界面
		 */
		
		//先看是不是有发布的任务
		MonitorWsmPublisState monitorPublis = new MonitorWsmPublisState(param);
		//准备上下文
		MwsmPageStateContext mwsmContext = new MwsmPageStateContext(monitorPublis);
		//发出请求
		mwsmContext.request();
		
	}
	
	//先看看是不是班长
	private boolean isMonitor() {
		String empNo = param.getEmpNo();
		WorkPersonDao workPersonDao = param.getWorkPersonDao();
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		String level = person.getLevel();
		String dictLevel = DictUtils.getDictValue("班长", "workPersonLevel", "1");
		if(dictLevel.equals(level)) {
			return true;
		}
		return false;
	}

}
