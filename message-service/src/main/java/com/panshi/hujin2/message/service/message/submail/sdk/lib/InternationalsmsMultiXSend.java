package com.panshi.hujin2.message.service.message.submail.sdk.lib;

import com.panshi.hujin2.message.service.message.submail.sdk.config.AppConfig;
import com.panshi.hujin2.message.service.message.submail.sdk.lib.base.ISender;
import com.panshi.hujin2.message.service.message.submail.sdk.lib.base.SenderWapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * internationalsms/multixsend 是 SUBMAIL 的国际短信一对多（即1条API请求发送多个号码，
 * 并可以灵活控制每个联系人的文本变量）和群发 API 。
 * @author submail
 *
 */
@Component
public class InternationalsmsMultiXSend extends SenderWapper {

	@Autowired
	protected AppConfig config;

	@Autowired
	private Internationalsms internationalsms;

	public static final String TO = "to";
	public static final String PROJECT = "project";
	public static final String  Vars= "vars";
	public static final String  MULTI= "multi";
	
	public void addTo(String to) {
		requestData.addWithComma(TO, to);
	}
	
	
	public void addProject(String project) {
		requestData.addWithComma(PROJECT, project);
	}
	
	
	public void addVars(JSONObject json){	
	    requestData.setVarJson(json);
	}
	
	public void addMulti(String toval){

		requestData.addMulti(Vars, TO, toval,MULTI);
	}

	public String multixsend(){
		return internationalsms.multixsend(requestData);
	}

	
}
	
	
	
	


