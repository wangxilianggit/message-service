package com.panshi.hujin2.message.service.message.yimeiruantong.dto.request;


import com.panshi.hujin2.message.service.message.yimeiruantong.dto.CustomImsIdAndMobileAndContent;

public class ImsPersonalityRequest extends ImsBaseRequest {

	private static final long serialVersionUID = 1L;

	private CustomImsIdAndMobileAndContent[] imses;

	public CustomImsIdAndMobileAndContent[] getImses() {
		return imses;
	}

	public void setImses(CustomImsIdAndMobileAndContent[] imses) {
		this.imses = imses;
	}

	

}
