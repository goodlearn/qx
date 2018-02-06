package com.thinkgem.jeesite.modules.sys.maskdispatch.state;

import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.maskdispatch.MdStateParam;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 班长任务状态
 */
public class MonitorMaskState extends AbsMaskPageState{

	public MonitorMaskState(MdStateParam param) {
		this.param = param;
	}
	
	@Override
	protected void requestHandle(PageStateContext context) {
		//先看看是不是班长
		if(!isMonitor()) {
			//不是班长交给普通员工进行处理
		}
		
		//是班长，班长处理
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
