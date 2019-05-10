package com.panshi.hujin2.message.service.message.yimeiruantong.dto.request;

import java.io.Serializable;


public class ImsBaseRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * appId
	 */
	private String appId;
	
	/**
	 * 时间戳
	 */
	private String timestamp;
	
	/**
	 * 签名
	 */
	private String sign;
	
	

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
