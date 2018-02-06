package com.thinkgem.jeesite.modules.sys.maskdispatch.state;

import com.thinkgem.jeesite.modules.sys.maskdispatch.MdStateParam;

public abstract class AbsMaskPageState {

	MdStateParam param;
	
	//获取任务状态页面
	protected abstract void requestHandle(PageStateContext context);
	
}
