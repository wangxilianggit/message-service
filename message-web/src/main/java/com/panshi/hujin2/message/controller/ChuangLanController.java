package com.panshi.hujin2.message.controller;

import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.facade.bo.MsgResponseResultInputBO;
import com.panshi.hujin2.message.service.message.IMsgDBService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * create by shenjiankang on 2018/6/22 14:17
 *
 * 创蓝回调
 */
@RestController
@RequestMapping("chuanglan")
public class ChuangLanController {

    private static Logger LOGGER = LoggerFactory.getLogger(ChuangLanController.class);

    @Autowired
    private IMsgDBService msgDBService;
    /**
     * @description:    国际短信发送结果回调
     * @param request
     * @Author shenjiankang
     * @Date 2018/6/23 10:25
     */
    @RequestMapping("/intMsgCallBack")
    public void intMsgReportCallBack(HttpServletRequest request){
        try {
            //receiver：接收状态报告验证的用户名（不是账户名），是按照用户要求配置的名称，默认为空

            LOGGER.info("--------创蓝短信回调--------");

            String receiver = request.getParameter("receiver");
            //pswd：接收状态报告验证的密码，默认为空
            String pswd = request.getParameter("pswd");
            //msgid：提交短信时平台返回的msgid
            String msgid = request.getParameter("msgid");
            //reportTime:格式YYMMDDhhmm，其中YY=年份的最后两位（00-99），MM=月份（01-12），DD=日（01-31），hh=小时（00-23），mm=分钟（00-59）
            // 网关平台返回的时间 有时差.网关不同，格式有偏差，以具体返回格式为准
            String reportTime = request.getParameter("reportTime");
            //mobile：接收短信的手机号码
            String mobile = request.getParameter("mobile");//todo 测试多手机号发送的 数据结构
            //status：状态字符串 具体含义参见状态报告状态码
            String status = request.getParameter("status");

            LOGGER.info("文本短信发送结果");
            LOGGER.info("receiver: ---------------[{}]",receiver);
            LOGGER.info("pswd: -------------------[{}]",pswd);
            LOGGER.info("msgid: ------------------[{}]",msgid);
            LOGGER.info("reportTime: -------------[{}]",reportTime);
            LOGGER.info("mobile: -----------------[{}]",mobile);
            LOGGER.info("status: -----------------[{}]",status);

            MsgResponseResultInputBO inputBO = new MsgResponseResultInputBO();

            if(StringUtils.isNotBlank(receiver) && !"null".equals(receiver)){
                inputBO.setReceiver(receiver);
            }
            if(StringUtils.isNotBlank(pswd) && !"null".equals(pswd)){
                inputBO.setPswd(pswd);
            }
            if(StringUtils.isNotBlank(msgid)){
                inputBO.setMsgId(msgid);
            }
            inputBO.setReportTime(reportTime);
            inputBO.setMobile(mobile);

            inputBO.setStatus(status);

            //默认设置中文语言
            Context context = new Context();
            context.setLocale(Locale.CHINA);
            //记录文本短信发送结果
            int count = msgDBService.addMsgResponseResult(inputBO,context);

            LOGGER.info("--------创蓝短信发送响应回掉成功入库:[{}] 条数据",count);

        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
    }

}
