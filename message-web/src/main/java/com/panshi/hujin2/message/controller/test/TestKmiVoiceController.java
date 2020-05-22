package com.panshi.hujin2.message.controller.test;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.utils.ContextUtils;
import com.panshi.hujin2.message.service.message.ISendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenJianKang
 * @date 2020/5/21 17:20
 *
 * 测试KMI语音短信
 */
@RestController
@RequestMapping("test/kmiVoice")
public class TestKmiVoiceController {

    @Autowired
    @Qualifier("KMIVoiceServiceImpl")
    private ISendMsgService KMIVoiceServiceImpl;


//    @GetMapping("/test1/{phoneNumber}")
    public void test(@PathVariable String phoneNumber){
        KMIVoiceServiceImpl.sendInternationalMsg(ApplicationEnmu.VI_CASH_DOG,
                phoneNumber,
                "111111",
                ContextUtils.getDefaultContext(),
                1);
    }
}
