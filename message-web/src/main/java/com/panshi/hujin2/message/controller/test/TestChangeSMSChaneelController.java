package com.panshi.hujin2.message.controller.test;

import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.message.common.utils.SuccessivelySendMap;
import com.panshi.hujin2.message.dao.mapper.message.SmsChannelConfigMapper;
import com.panshi.hujin2.message.dao.model.SmsChannelConfigDO;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.service.message.ISendMsgService;
import com.panshi.hujin2.message.service.message.kmi.KMILongnumberServiceImpl;
import com.panshi.hujin2.message.service.message.kmi.KMIServiceImpl;
import com.panshi.hujin2.message.service.message.nx.NXServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenJianKang
 * @date 2020/5/13 9:36
 */

@Slf4j
@RestController
@RequestMapping("test/changeSMSChannel")
public class TestChangeSMSChaneelController {

    @Autowired
    @Qualifier("KMIService")
    private ISendMsgService KMIService;

    @Autowired
    @Qualifier("inaSMSService")
    private ISendMsgService inaSMSService;

    @Autowired
    @Qualifier("nxService")
    private ISendMsgService nxService;

    @Autowired
    @Qualifier("KMILongnumberService")
    private ISendMsgService KMILongnumberService;

    @Autowired
    private SmsChannelConfigMapper smsChannelConfigMapper;



//    @GetMapping("/test1/{phoneNumber}")
    public void test(@PathVariable String phoneNumber){
        Map<String,Integer> suMap = SuccessivelySendMap.successivelySendMap;
        ISendMsgService sendMsgService = null;
        //String phoneNumber = "111";

        //TODO: 2020/5/12 18:13 by ShenJianKang 切换短信通道
        List<SmsChannelConfigDO> configDOS = smsChannelConfigMapper.queryAllValid();
        if(CollectionUtils.isEmpty(configDOS)){
            System.out.println(" 没有可以发送的短信渠道 " );
            return ;
        }

        configDOS.sort((o1, o2)->{
            return o1.getPriority().compareTo(o2.getPriority());
        });


//                ISendMsgService firstSendMsgService = getMsgChannel(configDOS.get(0).getMsgChannel());

        //String firstChannel = null;
        String lastChannel = null;
        //key:本次channel  value:下一次channel
        Map<String, String> rankMap = new HashMap<>();
        for(int i=0;i<configDOS.size();i++){
            String currChannel = configDOS.get(i).getMsgChannel();
            //ChannelEnum channelEnum1 = ChannelEnum.getByText(currChannel);
            if((i+1) == configDOS.size()){
                //最后一次循环
                rankMap.put(currChannel,"-1");
                lastChannel = currChannel;
            }else {
//                        if(i == 0){
//                            firstChannel = currChannel;
//                        }
                String nextChannel =  configDOS.get(i+1).getMsgChannel();
                //ChannelEnum channelEnum2 = ChannelEnum.getByText(nextChannel);
                rankMap.put(currChannel, nextChannel);
            }
        }

        //按照通道发送顺序：kmi otp短信、牛信otp短信、kmi 营销短信，
        if(suMap.size()==0){
            //sendMsgService = getMsgSendInstance(sendMsgChannel);
            //sendMsgService = KMIService;
            //第一个发送渠道
            sendMsgService = getMsgChannel(configDOS.get(0).getMsgChannel());
        }else{
            //写死的版本
//                    Integer resType = suMap.get(phoneNumber);
//                    if(resType!=null){
//                        if(ChannelEnum.KMI.getCode() == resType){
//                            sendMsgService = KMIService;
//                        }else if(ChannelEnum.NIU_XIN.getCode() == resType){
//                            sendMsgService = nxService;
//                        }else if(ChannelEnum.KMI_LONGNUMBER.getCode() == resType){
//                            sendMsgService = KMILongnumberService;
//                        } else {
//                            sendMsgService = KMIService;
//                        }
//                    }else{
//                        sendMsgService = KMIService;
//                    }
            Integer channelCode = suMap.get(phoneNumber);
            ChannelEnum channelEnum = ChannelEnum.getByCode(channelCode);
            String nextChannel = rankMap.get(channelEnum.getText());
            if(StringUtils.isBlank(nextChannel)){
                sendMsgService = getMsgChannel(configDOS.get(0).getMsgChannel());
            }else {
                if(nextChannel.equals("-1")){
                    //取最后一个
                    //sendMsgService = getMsgChannel(lastChannel);
                    sendMsgService = getMsgChannel(configDOS.get(0).getMsgChannel());
                }else {
                    sendMsgService = getMsgChannel(nextChannel);
                }
            }

        }


        //发送优先级:：kmi otp短信、牛信otp短信、kmi 营销短信，
        //写死的版本
//            if(sendMsgService instanceof KMIServiceImpl){
//                suMap.put(phoneNumber,ChannelEnum.NIU_XIN.getCode());
//            }else if(sendMsgService instanceof NXServiceImpl){
//                suMap.put(phoneNumber,ChannelEnum.KMI_LONGNUMBER.getCode());
//            }else if(sendMsgService instanceof KMILongnumberServiceImpl){
//                suMap.put(phoneNumber,ChannelEnum.KMI.getCode());
//            }else {
//                suMap.put(phoneNumber,ChannelEnum.KMI.getCode());
//            }

        //动态的版本 //TODO: 2020/5/13 0:30 by ShenJianKang  也可以直接记录 本次发送channelType ：suMap.put(phoneNumber,currChannel);
        if(sendMsgService instanceof KMIServiceImpl){
            suMap.put(phoneNumber, ChannelEnum.KMI.getCode());
            System.out.println(" = kmi 发送" );
        }else if(sendMsgService instanceof NXServiceImpl){
            suMap.put(phoneNumber,ChannelEnum.NIU_XIN.getCode());
            System.out.println(" = 牛信 发送" );
        }else if(sendMsgService instanceof KMILongnumberServiceImpl){
            suMap.put(phoneNumber,ChannelEnum.KMI_LONGNUMBER.getCode());
            System.out.println(" = kmi 营销 发送" );
        }else {
            suMap.put(phoneNumber,ChannelEnum.KMI.getCode());
            System.out.println(" = kmi 发送" );
        }

    }


    public ISendMsgService getMsgChannel(String msgChannel){
        ISendMsgService sendMsgService = null;
        if(ChannelEnum.KMI.getText().equals(msgChannel)){
            sendMsgService = KMIService;
        }else if(ChannelEnum.NIU_XIN.getText().equals(msgChannel)){
            sendMsgService = nxService;
        }else if(ChannelEnum.KMI_LONGNUMBER.getText().equals(msgChannel)){
            sendMsgService = KMILongnumberService;
        }

        if(sendMsgService == null){
            sendMsgService = KMIService;
        }
        return sendMsgService;
    }
}
