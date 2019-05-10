package com.panshi.hujin2.message.service.message.submail.sdk.utils;



import com.panshi.hujin2.message.service.message.submail.sdk.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 这个类通过加载app_config.properties文件创建配置对象并获取值，包括创建
 * MailConfig，MessageConfig，VoiceConfig,InternationalsmsConfig,MobiledataConfig
 * @see AppConfig
 * @see MailConfig
 * @see MessageConfig
 * @see VoiceConfig
 * @see InternationalsmsConfig
 * @see MobiledataConfig
 * @author submail
 *
 */
@Component
public class ConfigLoader {

	@Autowired
	protected AppConfig config;

	//i18n
	@Value("${submail.internationalsms.appid}")
	private String i18n_appId;

	@Value("${submail.internationalsms.appkey}")
	private String i18n_appKey;

	@Value("${submail.encryption}")
	private String i18n_encryption;

	//邮箱
	@Value("${mail.appid}")
	private String mailAppId;

	@Value("${mail.appkey}")
	private String mailAppKey;

	@Value("${mail.normal}")
	private String mailNormal;




	/**
	 * enum define two kinds of configuration.
	 * */
	public enum ConfigType {
		Mail, Message,Voice,Internationalsms,Mobiledata
	};

	/**
	 * 外部类的静态方法，可以通过加载文件创建配置。
	 * 
	 * @param type
	 *            ConfigType
	 * @return If the type is ConfigType#Mail,return the instance of
	 *         {@link MailConfig}. And,if the type is ConfigType#Message,return
	 *         the instance of {@link MessageConfig}.
	 * */
	public AppConfig load(ConfigType type) {
		switch (type) {
		case Mail:
			return createMailConfig();
		case Message:
			return createMessageConfig();
		case Voice:
			return createVoiceConfig();
		case Internationalsms:
			return createInternationalsmsConfig();
		case Mobiledata:
			return createMobiledataConfig();
		default:
			return null;
		}
	}

	/**
	 * 邮件
	 */
	private AppConfig createMailConfig() {
		AppConfig config = new MailConfig();
		config.setAppId(mailAppId);
		config.setAppKey(mailAppKey);
		config.setSignType(mailNormal);
		return config;
	}

	private AppConfig createMessageConfig() {
		//短信配置类  该功能不用，暂时先注释
		AppConfig config = new MessageConfig();
//		config.setAppId(getPros().get(MessageConfig.APP_ID));
//		config.setAppKey(getPros().get(MessageConfig.APP_KEY));
//		config.setSignType(getPros().get(MessageConfig.APP_SIGNTYPE));
		return config;
	}
	
	private  AppConfig createVoiceConfig() {
		//语音配置文件  该功能不用，暂时先注释
		AppConfig config = new VoiceConfig();
//		config.setAppId(getPros().get(VoiceConfig.APP_ID));
//		config.setAppKey(getPros().get(VoiceConfig.APP_KEY));
//		config.setSignType(getPros().get(VoiceConfig.APP_SIGNTYPE));
		return config;
	}
	
	private  AppConfig createInternationalsmsConfig() {
		//AppConfig config = new InternationalsmsConfig();
		config.setAppId(i18n_appId);
		config.setAppKey(i18n_appKey);
		config.setSignType(i18n_encryption);
		return config;
	}
	
	private  AppConfig createMobiledataConfig() {
		//手机流量配置文件  该功能不用，暂时先注释
		AppConfig config = new MobiledataConfig();
//		config.setAppId(getPros().get(MobiledataConfig.APP_ID));
//		config.setAppKey(getPros().get(MobiledataConfig.APP_KEY));
//		config.setSignType(getPros().get(MobiledataConfig.APP_SIGNTYPE));
		return config;
	}

}
