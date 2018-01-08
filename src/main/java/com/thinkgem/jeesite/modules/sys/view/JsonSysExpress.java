package com.thinkgem.jeesite.modules.sys.view;

import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
* @author wzy
* @version 创建时间：2018年1月8日 下午12:31:02
* @ClassName Json快递视图
* @Description 类描述
*/
public class JsonSysExpress {
	
	private String expressId;//快递单号
	private String phone;		// 手机
	private String company;		//快递公司
	private String name;//姓名
	private String address;
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExpressId() {
		return expressId;
	}
	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		String companyName = DictUtils.getDictLabel(company, "expressCompany", "0");
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	

}
