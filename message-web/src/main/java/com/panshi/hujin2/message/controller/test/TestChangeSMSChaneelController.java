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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
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



    @GetMapping("/test1/{phoneNumber}")
    public void test(@PathVariable String phoneNumber){
        Map<String,Integer> suMap = SuccessivelySendMap.successivelySendMap;
        ISendMsgService sendMsgService = null;
        //String phoneNumber = "111";

        //TODO: 2020/5/12 18:13 by ShenJianKang ??????????????????
        List<SmsChannelConfigDO> configDOS = smsChannelConfigMapper.queryAllValid();
        if(CollectionUtils.isEmpty(configDOS)){
            System.out.println(" ????????????????????????????????? " );
            return ;
        }

        configDOS.sort((o1, o2)->{
            return o1.getPriority().compareTo(o2.getPriority());
        });

        System.out.println("??????????????????");
        for(SmsChannelConfigDO configDO:configDOS){
            System.out.println("configDO.getMsgChannel() = " + configDO.getMsgChannel());
        }

//      ISendMsgService firstSendMsgService = getMsgChannel(configDOS.get(0).getMsgChannel());

        //String firstChannel = null;
        String lastChannel = null;
        //key:??????channel  value:?????????channel
        Map<String, String> rankMap = new HashMap<>();
        for(int i=0;i<configDOS.size();i++){
            String currChannel = configDOS.get(i).getMsgChannel();
            //ChannelEnum channelEnum1 = ChannelEnum.getByText(currChannel);
            if((i+1) == configDOS.size()){
                //??????????????????
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
        System.out.println("rankMap = " + rankMap);

        //???????????????????????????kmi otp???????????????otp?????????kmi ???????????????
        if(suMap.size()==0){
            //sendMsgService = getMsgSendInstance(sendMsgChannel);
            //sendMsgService = KMIService;
            //?????????????????????
            sendMsgService = getMsgChannel(configDOS.get(0).getMsgChannel());
        }else{
            //???????????????
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


            boolean isFirst = false;
            ChannelEnum channelEnum = null;
            if(suMap.containsKey(phoneNumber)){
                Integer channelCode = suMap.get(phoneNumber);
                LOGGER.info("channelCode: "+channelCode);
                channelEnum = ChannelEnum.getByCode(channelCode);
                LOGGER.info("channelEnum: "+channelEnum);
                if(channelEnum == null){
                    //???????????????????????????????????????????????????
                    //channelEnum = ChannelEnum.KMI;
                    isFirst = true;
                }
            }else {
                //channelEnum = ChannelEnum.KMI;
                isFirst = true;
            }
            String nextChannel = null;
            if(isFirst){
                nextChannel = "-1";
            }else {
                nextChannel = rankMap.get(channelEnum.getText());
            }
            LOGGER.info("nextChannel"+ nextChannel);
            if(StringUtils.isBlank(nextChannel)){
                sendMsgService = getMsgChannel(configDOS.get(0).getMsgChannel());
            }else {
                if(nextChannel.equals("-1")){
                    //???????????????
                    //sendMsgService = getMsgChannel(lastChannel);
                    sendMsgService = getMsgChannel(configDOS.get(0).getMsgChannel());
                }else {
                    sendMsgService = getMsgChannel(nextChannel);
                }
            }

        }


        //???????????????:???kmi otp???????????????otp?????????kmi ???????????????
        //???????????????
//            if(sendMsgService instanceof KMIServiceImpl){
//                suMap.put(phoneNumber,ChannelEnum.NIU_XIN.getCode());
//            }else if(sendMsgService instanceof NXServiceImpl){
//                suMap.put(phoneNumber,ChannelEnum.KMI_LONGNUMBER.getCode());
//            }else if(sendMsgService instanceof KMILongnumberServiceImpl){
//                suMap.put(phoneNumber,ChannelEnum.KMI.getCode());
//            }else {
//                suMap.put(phoneNumber,ChannelEnum.KMI.getCode());
//            }

        //??????????????? //TODO: 2020/5/13 0:30 by ShenJianKang  ????????????????????? ????????????channelType ???suMap.put(phoneNumber,currChannel);
        if(sendMsgService instanceof KMIServiceImpl){
            suMap.put(phoneNumber, ChannelEnum.KMI.getCode());
            System.out.println(" = kmi ?????? " + phoneNumber);
        }else if(sendMsgService instanceof NXServiceImpl){
            suMap.put(phoneNumber,ChannelEnum.NIU_XIN.getCode());
            System.out.println(" = ?????? ?????? " + phoneNumber);
        }else if(sendMsgService instanceof KMILongnumberServiceImpl){
            suMap.put(phoneNumber,ChannelEnum.KMI_LONGNUMBER.getCode());
            System.out.println(" = kmi ?????? ?????? " + phoneNumber);
        }else {
            suMap.put(phoneNumber,ChannelEnum.KMI.getCode());
            System.out.println(" = kmi ?????? " + phoneNumber);
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
