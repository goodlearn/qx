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
	
	//发送模板小希URL
	public static String TMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
	
	//JSAPITICKET
	public static String JS_API_TICKET_REQ_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

	
	//菜单请求
	public static String MENUREQUESTURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	//授权回调
	public static String OAUTHREDIRECTURL = "http://x.xlhtszgh.cn/kd/wx/getPersonIndex";
	
	//授权请求
	public static String OAUTHREQUESTURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=state=wzy_state#wechat_redirect";
	public static String OAUTHREQUESTURL_USERINFO = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=state=wzy_state#wechat_redirect";

	//请求Token
	public static String USERINFO_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	//微信模板
	public static String TEMPLATE_Msg_1 = "DQjKDzP4EQqrA6r_abDDYJjyNZ9071tuDls2DeNrJZA";
	public static String TEMPLATE_Msg_2 = "43WT2ikE7JLLBMcBRPqZyW_HgLiDjpoX6X7h05_Mscw";
	//微信模板颜色
	public static String TEMPLATE_Msg_COLOR_1 = "#173177";
	
	public static String TOP_Msg_COLOR_1 = "#000000";
	
	//测试OPEN_ID
	public static String TEST_OPEN_ID = "ouboC0mSlX4erWMYpj67sSLLdksU";
	//ouboC0t-H5ie5jHxb9ECPsvnY7Ow
	//ouboC0n6QJOO1OcgwoeNh4XNCfjk
	//ouboC0mSlX4erWMYpj67sSLLdksU

	
	public static String getUserClick(String redirectUrl,boolean isBase) {
		if(isBase) {
			return String.format(WxGlobal.OAUTHREQUESTURL,WxGlobal.APPID,redirectUrl);
		}else {
			return String.format(WxGlobal.OAUTHREQUESTURL_USERINFO,WxGlobal.APPID,redirectUrl);
		}
	}
}
