package com.panshi.hujin2.message.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author tianyh 
 * @Description:回调servlet
 */
@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {

	private static final long serialVersionUID = -8529247129492069689L;
    private static Logger LOGGER = LoggerFactory.getLogger(CallbackServlet.class);
	private static final String CHARSET = "UTF-8";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding(CHARSET);
		resp.setCharacterEncoding(CHARSET);
		PrintWriter pw = null;
		String result = "error";
		try {
			pw = resp.getWriter();
			/** 获取请求参数 **/
			BufferedReader in = new BufferedReader(new InputStreamReader(req.getInputStream(), CHARSET));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			in.close();
			String postContent = buffer.toString();
			
			// 回调示例: {"code": "0", "error":"", "msgid":"17041010383624511"}
			LOGGER.info("接收到参数：" + postContent);
			// TODO 此处是客户需要处理的逻辑代码
			result = "OK";

		} catch (Exception e) {
			LOGGER.error("callback接口出现异常:" + e.getMessage(), e);
			e.printStackTrace();
		} finally {
			LOGGER.info("HTTP请求结果：" + result);
			pw.print(result);
			pw.flush();
			pw.close();
		}
	}

}
