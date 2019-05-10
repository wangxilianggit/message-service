package com.panshi.hujin2.message.service.message.yimeiruantong.dto;

import java.io.Serializable;

public class CustomImsIdAndMobile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String customImsId;
	
	private String mobile;
	
	public CustomImsIdAndMobile(){
		
	}
	
	public CustomImsIdAndMobile(String customImsId,String mobile){
		this.customImsId = customImsId;
		this.mobile = mobile;
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

}
