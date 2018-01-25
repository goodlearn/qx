package com.thinkgem.jeesite.modules.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
import com.thinkgem.jeesite.modules.sys.state.MonitorWsmPageState;
import com.thinkgem.jeesite.modules.sys.state.StateParam;
import com.thinkgem.jeesite.modules.sys.state.WsmPageStateContext;

/**
 * 车间任务状态业务控制
  * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class WsmStateService extends BaseService {

	@Autowired
	private WorkPersonDao workPersonDao;
	@Autowired
	private WorkShopMaskDao workShopMaskDao;
	@Autowired
	private WsMaskWcDao wsMaskWcDao;
	
	//获取所有Dao 给状态业务传递参数
	private StateParam getAllDao(){
		StateParam stateParam = new StateParam();
		stateParam.setWorkPersonDao(workPersonDao);
		stateParam.setWorkShopMaskDao(workShopMaskDao);
		stateParam.setWsMaskWcDao(wsMaskWcDao);
		return stateParam;
	}
	
	//请求状态页面
	public StateParam requestWsmStatePage(String empNo) {
		
		//补充参数
		StateParam stateParam = getAllDao();
		stateParam.setEmpNo(empNo);
		//先进行班长处理
		MonitorWsmPageState monitor = new MonitorWsmPageState(stateParam);
		//准备上下文
		WsmPageStateContext context = new WsmPageStateContext(monitor);
		//发出请求
		context.request();
		return stateParam;
	}
	
}
