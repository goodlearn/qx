package com.thinkgem.jeesite.modules.sys.state;

import java.util.Map;

/**
 * 
 * 任务状态页面
 * @author wzy
 *  @version 2018-01-24
 */
public abstract class AbsWsmPageState {

	protected ScStateParam param;
	
	//获取任务状态页面
	protected abstract void requestHandle(WsmPageStateContext context);
	
}
