package com.thinkgem.jeesite.modules.sys.state;

/**
 * 班长请求页面上下文
 * @author Administrator
 *
 */
public class MwsmPageStateContext {

	private AbsMonitorWsmPageState state;

	public AbsMonitorWsmPageState getState() {
		return state;
	}

	public MwsmPageStateContext(AbsMonitorWsmPageState state){
		this.state = state;
	}
	
	/**
	 * 发起请求
	 */
	public void request() {
		 state.requestHandle(this);
	}
}
