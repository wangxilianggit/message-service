package com.panshi.hujin2.message.service.message.yimeiruantong.util.http;

/**
 * Http 请求实体<byte[]>
 * 
 * @author Frank
 *
 */
public class HttpRequestBytes extends HttpRequest<byte[]> {

	/**
	 * 
	 * @param httpParams
	 *            请求参数
	 */
	public HttpRequestBytes(HttpRequestParams<byte[]> httpParams) {
		super(httpParams, new HttpRequestPraserBytes());
	}

}
