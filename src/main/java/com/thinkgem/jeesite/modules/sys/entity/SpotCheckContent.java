/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 点检卡内容表Entity
 * @author wzy
 * @version 2018-01-25
 */
public class SpotCheckContent extends DataEntity<SpotCheckContent> {
	
	private static final long serialVersionUID = 1L;
	private String scspId;		// 父任务号
	private String oldNew;		// 新旧内容
	private String part;		// 部位
	private String context;		// 检查内容
	private String resultContent;		// 结果内容
	
	
	//非DB读取数据
	private List<BusinessResultItem> briList;//结果项
	
	public SpotCheckContent() {
		super();
	}

	public SpotCheckContent(String id){
		super(id);
	}

	@Length(min=1, max=64, message="父任务号长度必须介于 1 和 64 之间")
	public String getScspId() {
		return scspId;
	}

	public void setScspId(String scspId) {
		this.scspId = scspId;
	}
	
	@Length(min=1, max=100, message="新旧内容长度必须介于 1 和 100 之间")
	public String getOldNew() {
		return oldNew;
	}

	public void setOldNew(String oldNew) {
		this.oldNew = oldNew;
	}
	
	
	@Length(min=1, max=100, message="部位长度必须介于 1 和 100 之间")
	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}
	
	@Length(min=1, max=255, message="检查内容长度必须介于 1 和 255 之间")
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	@Length(min=1, max=255, message="结果内容长度必须介于 1 和 255 之间")
	public String getResultContent() {
		return resultContent;
	}

	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}

	public List<BusinessResultItem> getBriList() {
		return briList;
	}

	public void setBriList(List<BusinessResultItem> briList) {
		this.briList = briList;
	}

	
	
	
}