package com.thinkgem.jeesite.modules.sys.state;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 车间任务发布状态处理
 * @author Administrator
 *
 */
public class MonitorWsmPublisState extends AbsMonitorWsmPageState{

	public MonitorWsmPublisState(StateParam param) {
		this.param = param;
	}
	
	@Override
	protected void requestHandle(MwsmPageStateContext context) {
		
		//没有发布 就提示没有发布的任务就可以了 不用进入下一个状态
		//看车间是不是发布任务了
		if(!isNoPublish()) {
			//没有发布
			param.setResultCode(param.MESSAGE_CODE);//设置结果码
			param.setValue(param.NO_PUBLISH_WORK_SHOP_MASK);
		}else {
			//发布任务了 就需要进行任务发布 
			//去班级任务发布状态中进行状态判断
			MonitorWsmspState state = new MonitorWsmspState(param);
			state.requestHandle(context);
		}
	
		
		
	}
	
	//是否没有发布任务
	private boolean isNoPublish() {
		//先获取班级号
		String empNo = param.getEmpNo();
		WorkPersonDao workPersonDao = param.getWorkPersonDao();
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		String classId = person.getWorkClassId();
		
		//依据班级号查询任务 依据发布的
		WorkShopMaskDao workShopMaskDao = param.getWorkShopMaskDao();
		WorkShopMask query = new WorkShopMask();
		String no = DictUtils.getDictValue("否", "yes_no", "0");
		query.setReleaseState(no);//设置查询条件 只查询未发布数据
		query.setWorkClassId(classId);
		List<WorkShopMask> findAllList = workShopMaskDao.findAllList(query);
		if(null == findAllList || findAllList.size() == 0) {
			return true;
		}
		return false;
	}

}
