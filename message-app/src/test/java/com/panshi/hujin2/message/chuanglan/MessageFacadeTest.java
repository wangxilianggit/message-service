package com.panshi.hujin2.message.chuanglan;


import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.base.service.utils.ContextUtils;
import com.panshi.hujin2.message.facade.IMessageFacade;
import com.panshi.hujin2.message.facade.IMsgTemplateFacade;
import com.panshi.hujin2.message.facade.INotificationHistoryFacade;
import com.panshi.hujin2.message.facade.bo.*;
import com.panshi.hujin2.message.service.message.IMsgDBService;
import com.panshi.hujin2.message.service.message.ISendMsgService;
import com.panshi.hujin2.message.service.message.utils.MsgUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * create by shenjiankang on 2018/6/20 15:22
 */
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
public class MessageFacadeTest {

    @Autowired
    private IMessageFacade messageFacade;

    @Autowired
    private IMsgDBService msgDBService;

    @Autowired
    @Qualifier("chuanglanService")
    private ISendMsgService chuanglanMessageService;

    @Autowired
    @Qualifier("submailSerivce")
    private ISendMsgService submailMessageService;

    @Autowired
    @Qualifier("infobipService")
    private ISendMsgService infobipSerivce;

    @Autowired
    private IMsgTemplateFacade templateFacade;

    private Context context = new Context();


    @Autowired
    private INotificationHistoryFacade notificationHistoryFacade;

//    @Test
    public void test11(){
        ApplicationEnmu applicationEnmu = ApplicationEnmu.BR_Eloan;
        BasicResult<AppPushHistoryPagingOutputBO> appPush = notificationHistoryFacade.queryPushHistoryByUid(applicationEnmu, 1, 2, 3, ContextUtils.getDefaultContext());
        System.out.println(appPush);

    }

    /**
     *  ????????????????????????????????????
     *
     * Claro:
     * + 5511993367723
     * +5511990150742
     *
     * oi:
     * +5511940410157
     *
     * tim:
     * +5511983525890
     *
     * vivo:
     * +5511942884159
     *
     */


    //??????????????????????????????????????????????????????
    public void testSendNum(){
        String key = "test1";

        Integer sendNum = 1;
        Object sendN = MsgUtils.expiryMap.get(key);
        if(sendN != null){
            sendNum = (Integer)sendN + 1;
        }
        Long endTime = MsgUtils.getEndTime();
        //????????????
        endTime = endTime * 1000;

        //?????????????????????????????????????????????
        MsgUtils.printMsgSendLogs(key,sendNum, endTime);
        //???????????????
        MsgUtils.expiryMap.put(key, sendNum, endTime);
    }


    // ????????????????????????????????????????????????????????????
//    @Test
    public void test1(){
        Context context = new Context();
        context.setLocale(new Locale("zh","CN"));
        List list1 = new ArrayList();
        list1.add("2018???8???2???");

        List list2 = new ArrayList();
        list2.add("1");
        list2.add("2");

        List list3 = new ArrayList();

        List list4 = new ArrayList();
        list4.add("2018 ??? 8??? 3???") ;

        //??????????????????????????????????????????????????????????????????%s???????????????MoneyRush???
        Map<String, List> paramMap = new HashMap<>();
        //paramMap.put("8613777400292",list1);
        paramMap.put("8613777400291",list2);
        paramMap.put("8613777400293",list3);
        //paramMap.put("8613989801412",list4);
        List<String> res = messageFacade.batchSendSameTemplateDiffParam(ApplicationEnmu.WU_YOU_DAI,
                paramMap,
                "201807240410487930003",
                context).getData();
        System.out.println("res = " + res);
    }



    //?????????????????????????????????????????????
//    @Test
    public void test2(){
        Context context = new Context();
        context.setLocale(new Locale("zh","CN"));

        //??????????????????????????????????????????????????????????????????%s???????????????MoneyRush???
        String templatecode = "201807240410487930003";

        List list = new ArrayList();
        list.add("2018???8???2???");

        List phoneNumbnerList = new ArrayList();
        //zzh
        phoneNumbnerList.add("8613937368224");
        phoneNumbnerList.add("8618557538128");
        //ycg
        phoneNumbnerList.add("8613989801412");
        //
        phoneNumbnerList.add("8613777400292");


        messageFacade.batchSendSameTemplateSameParam(ApplicationEnmu.PAN_GUAN_JIA,phoneNumbnerList,templatecode,list,context);
    }


            //todo infobipServiceImpl???????????????
    //    public static void main(String[] args){
//        List<Integer> list = new ArrayList<>();
//        for (int i =0;i<10;i++){
//            list.add(i);
//        }
//
//        int j = 3;
//        int k = j;
//        for (int i =0;i<list.size();i+=j){
//
//            if (k <= list.size()){
//                List listA = list.subList(i,k);
//
//                System.out.println(listA);
//
//
//            }else{
//                //
//                System.out.println("???????????????");
//                List listA = list.subList(i,list.size());
//                System.out.println(listA);
//            }
//            k+=j;
//            System.out.println(i+"?????????");
//
//        }
//    }



//    @Test
    public void test3(){
        Context context = new Context();
        context.setLocale(new Locale("zh","CN"));

        List<String> list = new ArrayList<>();
        list.add("8613989801412");//ycg
        list.add("8613777400292");

        //???????????????????????????moneyrush???????????????????????????????????? %s????????????????????????MoneyRush???
        String templateCode = "201807240359082610002";

        List paramList = new ArrayList();
        paramList.add("5018-8-6");
        chuanglanMessageService.batchSendSameTemplateSameParam(ApplicationEnmu.WU_YOU_DAI,list,templateCode,paramList,context);
    }


    /**
     * ???????????? ????????????
     */
//    @Test
    public void test4(){
        UrgentRecallMsgLogInputBO inputBO = new UrgentRecallMsgLogInputBO();
        inputBO.setApplicationEnmu(ApplicationEnmu.BR_SIMPLE_LOAN);
        inputBO.setOrderId(1);
        inputBO.setOperatorId(1);
        inputBO.setOperatorName("test sjk");

        List<String> phoneNumbers = Stream.of("137777","138888").collect(Collectors.toList());
        inputBO.setPhoneNumbers(phoneNumbers);
        inputBO.setContent("test ????????????");
        inputBO.setSendResult("test ????????????");
        messageFacade.batchMsg(inputBO, context);
    }

    /**
     * ?????? ?????? ??????orderid
     */
//    @Test
    public void test5(){

        BasicResult<List<UrgentRecallMsgLogOutputBO>> res = messageFacade.queryUrgentRecallMsgLogByOrderId(1, context);
        System.out.println("res.getData() = " + res.getData());
    }

    /**
     * ????????????????????????
     */
//    @Test
    public void test6(){
        UrgentRecallCallLogInputBO inputBO = new UrgentRecallCallLogInputBO();
        inputBO.setExtendId("1");
        inputBO.setStartTime("????????????");
        inputBO.setEndTime("end time");
        inputBO.setFeeTime(2);
        inputBO.setEndDirection(0);
        inputBO.setEndReason(0);
        inputBO.setRecodingurl("www.xxxx.com");
        BasicResult<Void> res = messageFacade.insertCallRecord(inputBO, context);
        System.out.println("res = " + res);
    }
    /**
     * ????????????id??????????????????
     */
//    @Test
    public void test7(){
        BasicResult<List<UrgentRecallCallLogOutputBO>>  res = messageFacade.getCallRecordByOrderId(1, context);
        System.out.println("res = " + res);
    }





}
