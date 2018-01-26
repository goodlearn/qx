package com.thinkgem.jeesite.modules.sys.state;

/**
 * 班长状态处理
 * @author Administrator
 *
 */
public abstract class AbsMonitorWsmPageState {
	
	protected ScStateParam param;
	
	//获取任务状态页面
	protected abstract void requestHandle(MwsmPageStateContext context);
}
