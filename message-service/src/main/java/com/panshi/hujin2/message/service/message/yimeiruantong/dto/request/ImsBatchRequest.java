package com.panshi.hujin2.message.service.message.yimeiruantong.dto.request;


import com.panshi.hujin2.message.service.message.yimeiruantong.dto.CustomImsIdAndMobile;

/**
 * 批量短信发送参数
 * 
 * @author Frank
 *
 */
public class ImsBatchRequest extends ImsBaseRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 手机号与自定义SmsId
	 */
	private CustomImsIdAndMobile[] imses;

	/**
	 * 短信内容
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CustomImsIdAndMobile[] getImses() {
		return imses;
	}

	public void setImses(CustomImsIdAndMobile[] imses) {
		this.imses = imses;
	}

}
