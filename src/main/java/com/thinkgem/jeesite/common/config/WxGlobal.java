package com.thinkgem.jeesite.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author wzy
* @version 创建时间：2018年1月2日 下午4:29:52
* @ClassName 类名称
* @Description 类描述
*/
public class WxGlobal {

	
	public static String WX_TOKEN = "V189006VVxvvO8S6VHVQo9KfVAs1eS0Z";
	
	public static final String APPID = "wxd186964df0fdbfd5";
	
	public static final String APPSECREST = "30b3282df5334af8a8d62706a2669a8e";
	
	
	//菜单请求
	public static String MENUREQUESTURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	//授权回调
	public static String OAUTHREDIRECTURL = "http://7c17279f.ngrok.io/wx/oAuthRedirectSo";
	
	//授权请求
	public static String OAUTHREQUESTURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=state=wzy_state#wechat_redirect";

	//请求Token
	public static String USERINFO_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	public static String getUserClick() {
		 return String.format(WxGlobal.OAUTHREQUESTURL,WxGlobal.APPID,WxGlobal.OAUTHREDIRECTURL);
	}
}
