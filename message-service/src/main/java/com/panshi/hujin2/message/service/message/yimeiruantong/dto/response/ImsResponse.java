package com.panshi.hujin2.message.service.message.yimeiruantong.dto.response;

import java.io.Serializable;

/**
 * 单条短信发送响应
 * @author Frank
 *
 */
public class ImsResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String imsId;
	
	private String mobile;
	
	private String customImsId;
	
	public ImsResponse(){
		
	}
	
	public ImsResponse(String imsId,String mobile,String customImsId){
		this.imsId = imsId;
		this.mobile = mobile;
		this.customImsId = customImsId;
	}

	public String getImsId() {
		return imsId;
	}

	public void setImsId(String imsId) {
		this.imsId = imsId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCustomImsId() {
		return customImsId;
	}

	public void setCustomImsId(String customImsId) {
		this.customImsId = customImsId;
	}

}
