package com.panshi.hujin2.message.service.message.submail.sdk.config;

/**
 * 国际短信配置类
 * @author submail
 *
 */
public class InternationalsmsConfig extends AppConfig{

	//原版本
//	public static final String APP_ID = "internationalsms_appid";
//	public static final String APP_KEY = "internationalsms_appkey";
//	public static final String APP_SIGNTYPE = "md5";

	//修改之后(application.properties读取的配置名)
	public static final String APP_ID = "submail.internationalsms.appid";
	public static final String APP_KEY = "submail.internationalsms.appkey";
	public static final String APP_SIGNTYPE = "submail.encryption";

}
