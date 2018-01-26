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
	
	public MonitorWsmspState(ScStateParam param) {
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
		query.setSubmitState(no);//未提交的
		query.setEndDate(new Date());//当前时间小于结束的时间
		List<WsMaskWc> wmsList = wsMaskWcDao.findList(query);
		if(null == wmsList || wmsList.size() == 0) {
			//没有发布过任务
			//没有发布
			param.getModel().addAttribute("message",ScStateParam.NO_PUBLISH_WORK_SHOP_MASK);
			param.setValue(ScStateParam.ERROR_URL_WX);
			return;
		}else {
			
		}
	}

}
