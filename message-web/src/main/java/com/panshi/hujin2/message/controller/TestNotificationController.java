package com.panshi.hujin2.message.controller;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.facade.INotificationPushFacade;
import com.panshi.hujin2.message.facade.bo.AppPushTemplateOutputBO;
import com.panshi.hujin2.message.service.notification.getui.INotificationTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * create by shenjiankang on 2018/7/13 20:32
 */
@RestController
public class TestNotificationController {

    @Autowired
    private INotificationPushFacade pushFacade;

    @Autowired
    private INotificationTemplateService templateService;


    private Context context = new Context();



    @RequestMapping("test1")
    public String test1(){
        return "success";
    }

    //测试获取默认模板
//    @RequestMapping("get_default_send_template")
    public AppPushTemplateOutputBO get_default_send_template(String language, String country){
        Context context = new Context();
        context.setLocale(new Locale(language,country));
        String templateCode = "201807240832276920005";

        return templateService.getPushTemplate(ApplicationEnmu.WU_YOU_DAI,
                                        templateCode,
                                        context
                                        );
    }


    //个推推送(盘点借)
    @RequestMapping("push")
    public void test(Integer appId,Integer userId) {
//        context.setLocale(Locale.CHINA);//中文
        context.setLocale(new Locale("pt", "BR"));//西班牙文
        List<String> list = new ArrayList<>();
        //Integer userId = 100060;

        List<String> paramList = new ArrayList<>();
        paramList.add("中国工商银行 - 6226232");
        paramList.add("20,00元");
        ApplicationEnmu applicationEnmu = null;
        switch (appId){
            case 1:
                applicationEnmu = ApplicationEnmu.PAN_GUAN_JIA;
                break;
            case 2:
                applicationEnmu = ApplicationEnmu.WU_YOU_DAI;
                break;
            case 3:
                applicationEnmu = ApplicationEnmu.BR_Eloan;
                break;
            case 4:
                applicationEnmu = ApplicationEnmu.BR_MY_LOAN;
                break;
            case 5:
                applicationEnmu = ApplicationEnmu.BR_FLASH_LOAN;
                break;
            case 6:
                applicationEnmu = ApplicationEnmu.BR_SIMPLE_LOAN;
                break;
            case 7:
                applicationEnmu = ApplicationEnmu.MX_I_LOAN;
                break;
            case 8:
                applicationEnmu = ApplicationEnmu.MX_MONEYR;
                break;
            case 9:
                applicationEnmu = ApplicationEnmu.MX_M_LOAN;
                break;
            case 10:
                applicationEnmu = ApplicationEnmu.MX_Y_LOAN;
                break;
            case 11:
                applicationEnmu = ApplicationEnmu.MX_U_LOAN;
                break;
            case 12:
                applicationEnmu = ApplicationEnmu.MX_HI_LOAN;
                break;
            case 13:
                applicationEnmu = ApplicationEnmu.BR_WOWLOAN;
                break;
            case 14:
                applicationEnmu = ApplicationEnmu.BR_FREELOAN;
                break;
        }
        try {
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(applicationEnmu,
                    userId,
                    "201807141000142300002",
                    paramList,
                    true,
                    true,
                    context);

            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(applicationEnmu,
                    userId,
                    "201807141005218640003",
                    paramList,
                    true,
                    true,
                    context);

            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(applicationEnmu,
                    userId,
                    "201807141009176870004",
                    paramList,
                    true,
                    true,
                    context);

            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(applicationEnmu,
                    userId,
                    "201807141025227960006",
                    paramList,
                    true,
                    true,
                    context);

            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(applicationEnmu,
                    userId,
                    "201807141027521310007",
                    paramList,
                    true,
                    true,
                    context);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    //个推推送(无忧贷)
//    @RequestMapping("testWuYouDai")
    public void testWuYouDai(Integer userId) {
        context.setLocale(Locale.CHINA);//中文
//        context.setLocale(new Locale("es", "MX"));//西班牙文

        try {
            //尊敬的用户，您于%s通过借款审核，获得%s比索的提现额度。
            List<String> paramList1 = new ArrayList<>();
            paramList1.add("14:18 01-08-2018");
            paramList1.add("888");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.WU_YOU_DAI,
                    userId,
                    "201807240602293290001",
                    paramList1,
                    true,
                    context);

            //尊敬的用户，您未通过借款审核，请如实修改后重新提交审核。
            List<String> paramList2 = new ArrayList<>();
//            paramList2.add("");
//            paramList2.add("");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.WU_YOU_DAI,
                    userId,
                    "201807240748028020002",
                    paramList2,
                    true,
                    context);

            //尊敬的用户，您于%s成功提现%s比索，财务正在放款中。
            List<String> paramList3 = new ArrayList<>();
            paramList3.add("14:18 01-08-2018");
            paramList3.add("111");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.WU_YOU_DAI,
                    userId,
                    "201807240803004690001",
                    paramList3,
                    true,
                    context);

            //尊敬的用户，您有一笔借款即将逾期，请尽快还款。
            List<String> paramList4 = new ArrayList<>();
//            paramList4.add("");
//            paramList4.add("");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.WU_YOU_DAI,
                    userId,
                    "201807240808375350002",
                    null,
                    true,
                    context);

            //尊敬的用户，您有一笔借款已逾期%s天，请尽快还款。
            List<String> paramList5 = new ArrayList<>();
            paramList5.add("5");
//            paramList5.add("");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.WU_YOU_DAI,
                    userId,
                    "201807240813436880003",
                    paramList5,
                    true,
                    context);

            //尊敬的用户，您于%s成功还款%s比索。
            List<String> paramList6 = new ArrayList<>();
            paramList6.add("14:18 01-08-2018");
            paramList6.add("222");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.WU_YOU_DAI,
                    userId,
                    "201807240821042700004",
                    paramList6,
                    true,
                    context);

            //尊敬的用户，您于%s成功还款%s比索，剩余%s比索未还，请尽快还款。
            List<String> paramList7 = new ArrayList<>();
            paramList7.add("14:18 01-08-2018");
            paramList7.add("3333");
            paramList7.add("5555");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.WU_YOU_DAI,
                    userId,
                    "201807240832276920005",
                    paramList7,
                    true,
                    context);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }





    //个推推送(巴西eloan)
    @RequestMapping("breloan")
    public void testBrEloan(Integer userId) {
        context.setLocale(Locale.CHINA);//中文
//        context.setLocale(new Locale("es", "MX"));//西班牙文
        context.setLocale(new Locale("pt","BR"));

        try {
            //尊敬的用户，您于%s通过借款审核，获得%s雷尔的提现额度。
            List<String> paramList1 = new ArrayList<>();
            paramList1.add("14:18 01-08-2018");
            paramList1.add("888");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.BR_Eloan,
                    userId,
                    "201808240843095510001",
                    paramList1,
                    true,
                    context);

            //尊敬的用户，您未通过借款审核，请如实修改后重新提交审核。
            List<String> paramList2 = new ArrayList<>();
//            paramList2.add("");
//            paramList2.add("");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.BR_Eloan,
                    userId,
                    "201808240844513880002",
                    paramList2,
                    true,
                    context);

            //尊敬的用户，您于%s成功提现%s雷尔。
            List<String> paramList3 = new ArrayList<>();
            paramList3.add("14:18 01-08-2018");
            paramList3.add("111");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.BR_Eloan,
                    userId,
                    "201808240846398830003",
                    paramList3,
                    true,
                    context);

            //尊敬的用户，您有一笔借款即将逾期，请尽快还款。
            List<String> paramList4 = new ArrayList<>();
//            paramList4.add("");
//            paramList4.add("");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.BR_Eloan,
                    userId,
                    "201808240847298360004",
                    null,
                    true,
                    context);

            //尊敬的用户，您有一笔借款已逾期%s天，请尽快还款。
            List<String> paramList5 = new ArrayList<>();
            paramList5.add("5");
//            paramList5.add("");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.BR_Eloan,
                    userId,
                    "201808240848198760005",
                    paramList5,
                    true,
                    context);

            //尊敬的用户，您于%s成功还款%s雷尔。
            List<String> paramList6 = new ArrayList<>();
            paramList6.add("14:18 01-08-2018");
            paramList6.add("222");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.BR_Eloan,
                    userId,
                    "201808240849037520006",
                    paramList6,
                    true,
                    context);

            //尊敬的用户，您于%s成功还款%s雷尔，剩余%s雷尔未还，请尽快还款。
            List<String> paramList7 = new ArrayList<>();
            paramList7.add("14:18 01-08-2018");
            paramList7.add("3333");
            paramList7.add("5555");
            Thread.sleep(1000);
            pushFacade.pushMessageToSingle(ApplicationEnmu.BR_Eloan,
                    userId,
                    "201808240849537380007",
                    paramList7,
                    true,
                    context);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
