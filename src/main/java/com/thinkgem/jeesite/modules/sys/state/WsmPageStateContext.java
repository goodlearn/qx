package com.thinkgem.jeesite.modules.sys.state;
/**
 * 
 * 任务状态页面上下文
 * @author wzy
 *  @version 2018-01-24
 */
public class WsmPageStateContext {

	private AbsWsmPageState state;
	
	public WsmPageStateContext(AbsWsmPageState state){
		this.state = state;
	}
	
	/**
	 * 发起请求
	 */
	public void request() {
		 state.requestHandle(this);
	}
	
}
