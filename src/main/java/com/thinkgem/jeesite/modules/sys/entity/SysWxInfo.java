package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.supcan.annotation.treelist.cols.SupCol;

/**
 * 微信信息表Entity
 * @author wzy
 * @version 2017-12-25
 */
public class SysWxInfo extends DataEntity<SysWxInfo> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// 微信关联号
	private String nickname;		// 用户昵称
	private String sex;		// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String headimgurl;		// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	private String idCard;		// 身份证号
	private String expressNum;		// 快递数量
	private User user;//关联的管理员
	
	public SysWxInfo() {
		super();
	}

	public SysWxInfo(String id){
		super(id);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@SupCol(isUnique="true")
	@Length(min=1, max=100, message="微信关联号长度必须介于 1 和 100 之间")
	public String getOpenId() {
		return openId;
	}

	@SupCol(isUnique="true")
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=1, max=100, message="身份证号长度必须介于 1 和 100 之间")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getExpressNum() {
		return expressNum;
	}

	public void setExpressNum(String expressNum) {
		this.expressNum = expressNum;
	}
	
	
}