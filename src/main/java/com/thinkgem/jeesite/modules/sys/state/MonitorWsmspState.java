package com.thinkgem.jeesite.modules.sys.state;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 车间任务发布状态判断
 * @author Administrator
 *
 */
public class MonitorWsmspState extends AbsMonitorWsmPageState{
	
	public MonitorWsmspState(StateParam param) {
		this.param = param;
	}

	@Override
	protected void requestHandle(MwsmPageStateContext context) {
		
		/**
		 * 是不是今天已经发布过了 分为已经提交和未提交两种状态
		 * 已经提交的话就不显示了
		 * 如果未提交 就返回任务处理页面
		 */
		
		
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
			param.setResultCode(param.URL_CODE);//设置结果码
			param.setValue(param.NO_PUBLISH_WORK_SHOP_MASK_CLASS_URL);
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
		
		//如果没发布过 就去发布
		param.setResultCode(param.URL_CODE);//设置结果码
		param.setValue(param.NO_PUBLISH_WORK_SHOP_MASK_CLASS_URL);
		return;
	}
	
	
	

}
