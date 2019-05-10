package com.panshi.hujin2.message.service.message.yimeiruantong.dto;

import java.io.Serializable;

public class CustomImsIdAndMobileAndContent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String customImsId;
	
	private String mobile;
	
	private String content;

	public CustomImsIdAndMobileAndContent(){
		
	}
	
	public CustomImsIdAndMobileAndContent(String customImsId,String mobile,String content){
		this.customImsId = customImsId;
		this.mobile = mobile;
		this.content = content;
	}
	
	public String getCustomImsId() {
		return customImsId;
	}

	public void setCustomImsId(String customImsId) {
		this.customImsId = customImsId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
