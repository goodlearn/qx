package com.thinkgem.jeesite.common.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;

import com.ckfinder.connector.ServletContextFactory;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("jeesite.properties");

	/**
	 * 任务表名称
	 */
	public static final String MOTOR_CHECK_SPOT_ITEM_1 = "发动机点检单一";
	public static final String FITTER_CHECK_SPOT_ITEM_1 = "汽修车间220T卡车钳工周检点检卡";
	public static final String SF31904C_CS_ITEM = "汽修车间SF31904C卡车点检卡";
	public static final String ITEM_220T_ZX_BY = "220T自卸卡车保养单（电气部分）";
	public static final String SF31904C_BY_ITEM = "SF31904卡车保养单（电气部分）";
	public static final String ITEM_220T_DG_DJ_BY = "220T卡车电工周点检卡（电气部分）";
	public static final String ITEM_SF31904_KC_DG_DJ = "SF31904卡车电工周点检卡（电气部分）";
	public static final String ITEM_108T_2000H_BY = "108T卡车2000H及以上级别保养单(机械部分)";
	public static final String ITEM_MT_440 = "MT4400卡车钳工周检分区";
	public static final String ITEM_220T_QG_DJ = "220T卡车钳工点检分区";
	public static final String ITEM_QX2B_MT_4400_DJ = "汽修二班MT4400保养责任分区";
	public static final String ITEM_QX2B_830_BY = "汽修二班830E保养责任分区";
	public static final String ITEM_108T_330_BY = "108T卡车330小时保养单(机械部分)";
	public static final String ITEM_108T_660_BY = "108T卡车660小时保养单(机械部分)";
	public static final String ITEM_108T_1000_BY = "108T卡车1000小时保养单(机械部分)";
	
	public static final String SF31904_CS_DICT = "sf31904cCsItem";
	public static final String ZX_BY_220T_DICT = "item220tZxBy";
	public static final String SF31904_BY_DICT = "sf31904ByItem";
	public static final String DG_DJ_220T_DICT = "item220DgDj";
	public static final String SF31904_KC_DG_DJ = "itemSf31904KcDgDj";
	public static final String ITEM_108T_2000H_BY_DICT = "item108t2000hBy";
	public static final String ITEM_MT_440_DICT = "itemMt440Zjfq";
	public static final String ITEM_220T_QG_DJ_DICT = "item220tQgDj";
	public static final String ITEM_QX2B_MT_4400_DJ_DICT = "itemQx2bMt4400Dj";
	public static final String ITEM_QX2B_830_BY_DICT = "itemQx2b830eBy";
	public static final String ITEM_108T_330_BY_DICT = "item108t330By";
	public static final String ITEM_108T_660_BY_DICT = "item108t660By";
	public static final String ITEM_108T_1000_BY_DICT = "item108t1000By";
	
	public static final String DEFAULT_ID_SYS_MANAGER = "1";//系统管理员默认ID
	public static final int TIE_DATE_NUM = 30;//微信绑定时间
	
	/**
	 * 所有工种Id
	 */
	public static final String ALL_WK = "all";
	public static final String ALL_WK_NAME = "所有工种";
	
	/**
	 * 测试微信
	 */
	public static final String TEST_WX_OPEN_ID = null;
	
	/**
	 * 存放图片信息路径
	 */
	public static final String WMW_IMAGE_PATH = "/userfiles/1/files/sys/wmw/2018/03";
	
	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";
	
	public final static String GET_METHOD = "GET";
	public final static String POST_METHOD = "POST";
	
	/**
	 * 手机验证码前缀
	 */
	public final static String PREFIX_MOBLIE_CODE = "mobile_";
	public final static int MOBILE_TIMES = 4;
	public final static int MOBILE_CODE_SIZE = 4;
	
	public static long MOBILE_CODE_TOME_OUT() {
		return System.currentTimeMillis()+1000*60*5;
	} 
	
	public static long WX_QREORD_TOME_OUT() {
		return System.currentTimeMillis()+1000*60*30;
	} 
	
	public static long WX_CODE_TOME_OUT() {
		return System.currentTimeMillis()+1000*60*30;
	} 
	
	public static int WX_CODE_TIME_OUT_INT() {
		return (int) (WX_CODE_TOME_OUT()/1000);
	}
	/**
	 * 微信消息类型
	 */
	/** 
     * 返回消息类型：文本 
     */  
    public static final String WX_RESP_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 返回消息类型：音乐 
     */  
    public static final String WX_RESP_MESSAGE_TYPE_MUSIC = "music";  
  
    /** 
     * 返回消息类型：图文 
     */  
    public static final String WX_RESP_MESSAGE_TYPE_NEWS = "news";  
  
    /** 
     * 请求消息类型：文本 
     */  
    public static final String WX_REQ_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 请求消息类型：图片 
     */  
    public static final String WX_REQ_MESSAGE_TYPE_IMAGE = "image";  
  
    /** 
     * 请求消息类型：链接 
     */  
    public static final String WX_REQ_MESSAGE_TYPE_LINK = "link";  
  
    /** 
     * 请求消息类型：地理位置 
     */  
    public static final String WX_REQ_MESSAGE_TYPE_LOCATION = "location";  
  
    /** 
     * 请求消息类型：音频 
     */  
    public static final String WX_REQ_MESSAGE_TYPE_VOICE = "voice";  
  
    /** 
     * 请求消息类型：推送 
     */  
    public static final String WX_REQ_MESSAGE_TYPE_EVENT = "event";  
  
    /** 
     * 事件类型：subscribe(订阅) 
     */  
    public static final String WX_EVENT_TYPE_SUBSCRIBE = "subscribe";  
  
    /** 
     * 事件类型：unsubscribe(取消订阅) 
     */  
    public static final String WX_EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";  
  
    /** 
     * 事件类型：CLICK(自定义菜单点击事件) 
     */  
    public static final String WX_EVENT_TYPE_CLICK = "CLICK"; 
	
	
	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}
	
	/**
	 * 在修改系统用户和角色时是否同步到Activiti
	 */
	public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return "true".equals(dm) || "1".equals(dm);
	}
    
	/**
	 * 页面获取常量
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		String dir = getConfig("userfiles.basedir");
		if (StringUtils.isBlank(dir)){
			try {
				dir = ServletContextFactory.getServletContext().getRealPath("/");
			} catch (Exception e) {
				return "";
			}
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
//		System.out.println("userfiles.basedir: " + dir);
		return dir;
	}
	
    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }
	
}
