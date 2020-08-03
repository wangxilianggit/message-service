package com.panshi.hujin2.message.controller.test;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.base.service.utils.ContextUtils;
import com.panshi.hujin2.message.controller.test.bo.TianYiHongOTPCallbackBO;
import com.panshi.hujin2.message.domain.enums.MsgTemplateCodeEnum;
import com.panshi.hujin2.message.domain.enums.MsgTypeEnum;
import com.panshi.hujin2.message.facade.IMessageFacade;
import com.panshi.hujin2.message.facade.bo.SendMsgBO;
import com.panshi.hujin2.message.service.message.ISendMsgService;
import com.panshi.hujin2.message.service.message.yimeiruantong.util.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenJianKang
 * @date 2020/7/29 14:20
 */

@RestController
//@RequestMapping("/tianyihong")
public class TestTianYiHongController {


    @Autowired
    @Qualifier("tianyihongService")
    private ISendMsgService tianyihongService;

    @Autowired
    private IMessageFacade iMessageFacade;


    @GetMapping("/test2")
    public String test2(){
        return "success";
    }

    @RequestMapping("/callback")
    public String callback(@RequestBody TianYiHongOTPCallbackBO callbackBO){
        System.out.println("callbackBO = " + callbackBO);
        return "success";
    }

    @RequestMapping("test1/{content}/{phoneNumber}")
    public void test1(@PathVariable String content,@PathVariable String phoneNumber){
        Context context = ContextUtils.getDefaultContext();
        ApplicationEnmu applicationEnmu = ApplicationEnmu.INA_KAS_INS;
        //String phoneNumber = "13777400292";
        String sendText = "hello";
        sendText = content;
        String msgTemplateCode = "";
        List<String> paramList = new ArrayList<>();
        boolean res = tianyihongService.sendInternationalMsg(applicationEnmu,phoneNumber,sendText, ContextUtils.getDefaultContext(),0);
        System.out.println("res = " + res);


        //测试 短信渠道轮换 发送
//        SendMsgBO sendMsgBO = new SendMsgBO();
//        sendMsgBO.setApplicationEnmu(applicationEnmu);
//        sendMsgBO.setPhoneNumber(phoneNumber);
//        sendMsgBO.setTemplateCode(msgTemplateCode);
//        sendMsgBO.setParamList(paramList);
//        sendMsgBO.setContext(context);
//        BasicResult<Void> basicResult = iMessageFacade.sendInternationalMsg(sendMsgBO);
    }


    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("account");
        sb.append("pwd");
        sb.append("20181109231202");
        String signMd5 = Md5.md5(sb.toString().getBytes());
        System.out.println("signMd5 = " + signMd5);
    }


}
