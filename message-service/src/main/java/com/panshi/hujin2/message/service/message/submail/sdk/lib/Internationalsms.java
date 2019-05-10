package com.panshi.hujin2.message.service.message.submail.sdk.lib;


import com.panshi.hujin2.message.service.message.submail.sdk.lib.base.Sender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 国际短信api
 * @author submail
 *
 */
@Component
public class Internationalsms extends Sender {

	@Value("${submail.i18n.send}")
	private String API_SEND;
	@Value("${submail.i18n.xsend}")
	private String API_XSEND;
	@Value("${submail.i18n.multixsend}")
	private String API_MULTIXSEND;

	@Override
	public String send(Map<String, Object> data) {
		return request(API_SEND, data);
	}

	@Override
	public String xsend(Map<String, Object> data) {
		return request(API_XSEND, data);
	}
	
	@Override
	public String multixsend(Map<String, Object> data) {
		return request(API_MULTIXSEND, data);
	}

	
}
