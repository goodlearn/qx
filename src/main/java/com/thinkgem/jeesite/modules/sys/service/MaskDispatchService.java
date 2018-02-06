package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.BusinessResultItemDao;
import com.thinkgem.jeesite.modules.sys.dao.MotorCheckSpotItem1Dao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckContentDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckMainPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.SpotCheckSinglePersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
import com.thinkgem.jeesite.modules.sys.maskdispatch.MdControl;
import com.thinkgem.jeesite.modules.sys.maskdispatch.MdStateParam;
import com.thinkgem.jeesite.modules.sys.maskdispatch.state.MonitorMaskState;
import com.thinkgem.jeesite.modules.sys.maskdispatch.state.PageStateContext;

@Service
@Transactional(readOnly = true)
public class MaskDispatchService extends BaseService {

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
	@Autowired
	private BusinessAssembleDao businessAssembleDao;
	@Autowired
	private MotorCheckSpotItem1Dao motorCheckSpotItem1Daol;
	
	//获取所有Dao 给状态业务传递参数
	private MdStateParam setMdStateParam(){
		MdStateParam param = new MdStateParam();
		param.setWorkPersonDao(workPersonDao);
		param.setWorkShopMaskDao(workShopMaskDao);
		param.setWsMaskWcDao(wsMaskWcDao);
		param.setSpotCheckMainPersonDao(spotCheckMainPersonDao);
		param.setSpotCheckSinglePersonDao(spotCheckSinglePersonDao);
		param.setBusinessResultItemDao(businessResultItemDao);
		param.setSpotCheckContentDao(spotCheckContentDao);
		param.setBusinessAssembleDao(businessAssembleDao);
		param.setMotorCheckSpotItem1Daol(motorCheckSpotItem1Daol);
		return param;
	}
	
	//获取所有Dao
	private MdControl setMdControl(){
		MdControl param = new MdControl();
		param.setWorkPersonDao(workPersonDao);
		param.setWorkShopMaskDao(workShopMaskDao);
		param.setWsMaskWcDao(wsMaskWcDao);
		param.setSpotCheckMainPersonDao(spotCheckMainPersonDao);
		param.setSpotCheckSinglePersonDao(spotCheckSinglePersonDao);
		param.setBusinessResultItemDao(businessResultItemDao);
		param.setSpotCheckContentDao(spotCheckContentDao);
		param.setBusinessAssembleDao(businessAssembleDao);
		param.setMotorCheckSpotItem1Daol(motorCheckSpotItem1Daol);
		return param;
	}
	
	//PC任务分配调度
	public MdControl pcMaskDispatch(String maskId,Model model) {
		MdControl param = setMdControl();
		param.setMaskId(maskId);
		param.setModel(model);
		param.pageDispatch();//数据分发
		return param;
	}
	
	//任务页面 请求获得不同的页面
	public MdStateParam mskDispatchPage(String empNo,String maskId,Model model,boolean isPC) {
		MdStateParam param = setMdStateParam();
		param.setEmpNo(empNo);
		param.setMaskId(maskId);
		param.setModel(model);
		param.setPC(isPC);
		return requestStatePage(param);
	}
	
	//请求状态页面
	private MdStateParam requestStatePage(MdStateParam mdStateParam) {
		
		//先进行班长处理
		MonitorMaskState monitor = new MonitorMaskState(mdStateParam);
		//准备上下文
		PageStateContext context = new PageStateContext(monitor);
		//发出请求
		context.request();
		//返回参数
		return mdStateParam;
	}
}
