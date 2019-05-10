package com.panshi.hujin2.message.service.message.yimeiruantong.dto.request;


/**
 * 单条短信发送参数
 * @author Frank
 *
 */
public class ImsSingleRequest extends ImsBaseRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 电话号码
	 */
	private String mobile;
	
	/**
	 * 短信内容
	 */
	private String content;
	
	/**
	 * 自定义imsid
	 */
	private String customImsId;
	

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

	public String getCustomImsId() {
		return customImsId;
	}

	public void setCustomImsId(String customImsId) {
		this.customImsId = customImsId;
	}


}
