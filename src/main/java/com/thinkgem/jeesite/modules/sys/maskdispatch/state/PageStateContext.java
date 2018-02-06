package com.thinkgem.jeesite.modules.sys.maskdispatch.state;


/**
 * 任务状态页面上下文
 */
public class PageStateContext {
	
	private AbsMaskPageState state;
	
	public PageStateContext(AbsMaskPageState state){
		this.state = state;
	}
	
	/**
	 * 发起请求
	 */
	public void request() {
		 state.requestHandle(this);
	}
}
