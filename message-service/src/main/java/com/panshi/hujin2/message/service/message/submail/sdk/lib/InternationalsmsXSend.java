package com.panshi.hujin2.message.service.message.submail.sdk.lib;

import com.panshi.hujin2.message.service.message.submail.sdk.config.AppConfig;
import com.panshi.hujin2.message.service.message.submail.sdk.lib.base.ISender;
import com.panshi.hujin2.message.service.message.submail.sdk.lib.base.SenderWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * internationalsms/xsend 是 SUBMAIL 的国际短信 API。
 * @author submail
 *
 */
@Component
public class InternationalsmsXSend extends SenderWapper {

	@Autowired
	private AppConfig config;

	@Autowired
	private Internationalsms internationalsms;

	public static final String TO = "to";
	public static final String PROJECT = "project";
	public static final String VARS = "vars";
	
	public void addTo(String to) {
		requestData.addWithComma(TO, to);;
	}
	
	
	public void addProject(String project) {
		requestData.addWithComma(PROJECT, project);;
	}
	
	public void addVars(String key,String val){
		requestData.addWithJson(VARS,key,val);
	}

	
	public String xsend(){
		return internationalsms.xsend(requestData);
	}
	
	
}
	
	
	
	


