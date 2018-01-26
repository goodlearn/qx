package com.thinkgem.jeesite.modules.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.BusinessResultItemDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckContentDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckMainPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckSinglePersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
import com.thinkgem.jeesite.modules.sys.state.MonitorWsmPageState;
import com.thinkgem.jeesite.modules.sys.state.ScStateParam;
import com.thinkgem.jeesite.modules.sys.state.WsmPageStateContext;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 车间任务状态业务控制-点检
  * @author wzy
 * @version 2018-01-24
 */
@Service
@Transactional(readOnly = true)
public class WsmScStateService extends BaseService {

	@Autowired
	private WorkPersonDao workPersonDao;
	@Autowired
	private WorkShopMaskDao workShopMaskDao;
	@Autowired
	private WsMaskWcDao wsMaskWcDao;
	@Autowired
	private SpotCheckMainPersonDao spotCheckMainPersonDao;
	@Autowired
	private SpotCheckSinglePersonDao spotCheckSinglePersonDao;
	@Autowired
	private BusinessResultItemDao businessResultItemDao;
	@Autowired
	private SpotCheckContentDao spotCheckContentDao;
	
	//获取所有Dao 给状态业务传递参数
	private ScStateParam getAllDao(){
		ScStateParam stateParam = new ScStateParam();
		stateParam.setWorkPersonDao(workPersonDao);
		stateParam.setWorkShopMaskDao(workShopMaskDao);
		stateParam.setWsMaskWcDao(wsMaskWcDao);
		stateParam.setSpotCheckMainPersonDao(spotCheckMainPersonDao);
		stateParam.setSpotCheckSinglePersonDao(spotCheckSinglePersonDao);
		stateParam.setBusinessResultItemDao(businessResultItemDao);
		stateParam.setSpotCheckContentDao(spotCheckContentDao);
		return stateParam;
	}
	
	//前部
	public ScStateParam frontProcess(String empNo,Model model,boolean isPC) {
		//补充参数
		ScStateParam stateParam = getAllDao();
		stateParam.setEmpNo(empNo);//员工编号
		stateParam.setPC(isPC);//是否是PC端调用
		String item = DictUtils.getDictValue("前部", "bussinesItem", "0");
		stateParam.setItem(Integer.valueOf(item));//前部
		stateParam.setModel(model);
		return requestWsmStatePage(stateParam);
	}
	
	//中部
	public ScStateParam centralProcess(String empNo,Model model,boolean isPC) {
		//补充参数
		ScStateParam stateParam = getAllDao();
		stateParam.setEmpNo(empNo);//员工编号
		stateParam.setPC(isPC);//是否是PC端调用
		String item = DictUtils.getDictValue("中部", "bussinesItem", "1");
		stateParam.setItem(Integer.valueOf(item));//中部
		stateParam.setModel(model);
		return requestWsmStatePage(stateParam);
	}
	
	//后部
	public ScStateParam heelProcess(String empNo,Model model,boolean isPC) {
		//补充参数
		ScStateParam stateParam = getAllDao();
		stateParam.setEmpNo(empNo);//员工编号
		stateParam.setPC(isPC);//是否是PC端调用
		String item = DictUtils.getDictValue("后部", "bussinesItem", "2");
		stateParam.setItem(Integer.valueOf(item));//后部
		stateParam.setModel(model);
		return requestWsmStatePage(stateParam);
	}
	
	//请求状态页面
	private ScStateParam requestWsmStatePage(ScStateParam stateParam) {
		
		//先进行班长处理
		MonitorWsmPageState monitor = new MonitorWsmPageState(stateParam);
		//准备上下文
		WsmPageStateContext context = new WsmPageStateContext(monitor);
		//发出请求
		context.request();
		return stateParam;
	}
	
}
