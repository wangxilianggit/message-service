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
     *  四大运营商目前所有的号码
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


    //测试发送次数放到缓存中的结果是否正确
    public void testSendNum(){
        String key = "test1";

        Integer sendNum = 1;
        Object sendN = MsgUtils.expiryMap.get(key);
        if(sendN != null){
            sendNum = (Integer)sendN + 1;
        }
        Long endTime = MsgUtils.getEndTime();
        //转成毫秒
        endTime = endTime * 1000;

        //日志打印，当天手机号码发送次数
        MsgUtils.printMsgSendLogs(key,sendNum, endTime);
        //放到缓存中
        MsgUtils.expiryMap.put(key, sendNum, endTime);
    }


    // 测试短信批量单发，（相同模板，不同参数）
//    @Test
    public void test1(){
        Context context = new Context();
        context.setLocale(new Locale("zh","CN"));
        List list1 = new ArrayList();
        list1.add("2018年8月2日");

        List list2 = new ArrayList();
        list2.add("1");
        list2.add("2");

        List list3 = new ArrayList();

        List list4 = new ArrayList();
        list4.add("2018 年 8月 3日") ;

        //尊敬的用户，您好！您有一笔借款即将逾期，请于%s前还款。【MoneyRush】
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



    //测试短信批量发送，同模板同参数
//    @Test
    public void test2(){
        Context context = new Context();
        context.setLocale(new Locale("zh","CN"));

        //尊敬的用户，您好！您有一笔借款即将逾期，请于%s前还款。【MoneyRush】
        String templatecode = "201807240410487930003";

        List list = new ArrayList();
        list.add("2018年8月2日");

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


            //todo infobipServiceImpl的分推邏輯
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
//                System.out.println("超过长度了");
//                List listA = list.subList(i,list.size());
//                System.out.println(listA);
//            }
//            k+=j;
//            System.out.println(i+"次循环");
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

        //尊敬的用户，您好！moneyrush已成功放款到您的借款账户 %s，请注意查收。【MoneyRush】
        String templateCode = "201807240359082610002";

        List paramList = new ArrayList();
        paramList.add("5018-8-6");
        chuanglanMessageService.batchSendSameTemplateSameParam(ApplicationEnmu.WU_YOU_DAI,list,templateCode,paramList,context);
    }


    /**
     * 催收日志 短信记录
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
        inputBO.setContent("test 发送内容");
        inputBO.setSendResult("test 发送结果");
        messageFacade.batchMsg(inputBO, context);
    }

    /**
     * 催收 查询 根据orderid
     */
//    @Test
    public void test5(){

        BasicResult<List<UrgentRecallMsgLogOutputBO>> res = messageFacade.queryUrgentRecallMsgLogByOrderId(1, context);
        System.out.println("res.getData() = " + res.getData());
    }

    /**
     * 网页通话记录入库
     */
//    @Test
    public void test6(){
        UrgentRecallCallLogInputBO inputBO = new UrgentRecallCallLogInputBO();
        inputBO.setExtendId("1");
        inputBO.setStartTime("开始时间");
        inputBO.setEndTime("end time");
        inputBO.setFeeTime(2);
        inputBO.setEndDirection(0);
        inputBO.setEndReason(0);
        inputBO.setRecodingurl("www.xxxx.com");
        BasicResult<Void> res = messageFacade.insertCallRecord(inputBO, context);
        System.out.println("res = " + res);
    }
    /**
     * 通过订单id网页通话记录
     */
//    @Test
    public void test7(){
        BasicResult<List<UrgentRecallCallLogOutputBO>>  res = messageFacade.getCallRecordByOrderId(1, context);
        System.out.println("res = " + res);
    }





}
