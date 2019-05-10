package com.panshi.hujin2.message.service.message.yimeiruantong.dto.request;


/**
 * 状态报告获取请求参数
 *
 */
public class ImsReportRequest extends ImsBaseRequest {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 请求数量<br/>
	 * 最大500
	 */
	private int number = 500;

	public int getNumber() {
		if(number <= 0 || number > 500){
			number = 500;
		}
		return number;
	}

	public void setNumber(int number) {
		if(number > 500){
			number = 500;
		}
		this.number = number;
	}

	
}
