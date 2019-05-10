package com.panshi.hujin2.message.service.email.emailhtml;

import java.util.ArrayList;

/**
 * @author ycg 2018/11/29 17:35
 *
 * 发送Boleto到邮箱
 * 用户实时操作发送
 */
public class EmailTemplate5 {
    public static String getHtml5(EmailTemplateEntity emailTemplateEntity, String boletoUrl){
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html><html style=\"width:720px;max-width:720px;margin:0 auto;position:relative;\">");
        sb.append("<head><meta charset=\"utf-8\"><meta content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no\" name=\"viewport\"></head>");
        sb.append("<body><div class=\"wrapper\" style=\"width:720px;max-width:720px;background-color: rgba(255,255,255,1);font-size:18px;margin:0 30px;color:rgba(88,88,88,1)\">");
        sb.append("<div class=\"header\" style=\"margin:30px auto 0;display: flex;justify-content: center;\">");
        sb.append("<div class=\"img-logo\" style=\"width:27px;height:27px;display: block;position: relative;\"><img style=\"width:100%;height:100%;display: block;\" src=\"https://s3-sa-east-1.amazonaws.com/hujin2-s1/email/img_3.png\" alt=\"\"></div>");
        sb.append("<span class=\"title\" style=\"width:63px;height:16px;font-size:18px;font-family:HKGrotesk-SemiBoldItalic;font-weight:normal;color:rgba(0,180,239,1);line-height:27px;margin-left:6px;\"> ");
        // App name
        sb.append(emailTemplateEntity.getAppName());
        sb.append(" </span></div><div class=\"content\" style=\"font-size:16px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(88,88,88,1);line-height:23px;margin:24px auto 0;\"><p>Olá, <span> ");
        // 用户姓名
        sb.append(emailTemplateEntity.getUserName());
        sb.append("! O seu boleto para realizar o pagamento do mês ");
        // 还款月份
        String repaymentDateStr = emailTemplateEntity.getRepaymentDate();
        String month = repaymentDateStr.split("/")[1];//repaymentDateStr
        sb.append(month);
        sb.append(" já foi enviado por e-mail, por favor, confirme o recebimento.");
        sb.append("</div><div class=\"content-table\" style=\"display: block;max-width: 720px;\">");

        sb.append("<span class=\"bol-title\" style=\"font-size:16px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(0,180,239,1);line-height:23px;text-align: center;width:100%;display: block;\">");
        sb.append("Utilize o boleto para realizar o pagamento");
        sb.append("</span><div class=\"code-box\" style=\"width:270px;height:96px;border:1px solid rgba(191,191,191,1);margin:10px auto;\"><img style=\"width:100%;height:100%;display:block\" id=\"repayQr_qr\" src=\"");
        sb.append(boletoUrl);
        sb.append("\" alt=\"\"></div></div>");
        sb.append("<div style=\"margin:10px auto;text-align: center;max-width:540px; font-size:14px;\">");
        sb.append(emailTemplateEntity.getBoleteCode());
        sb.append("</div><div class=\"tips\" style=\"font-size:14px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(254,134,107,1);line-height:19px;\">");
        sb.append("Atenção: <br/>O ");
        sb.append(emailTemplateEntity.getAppName());
        sb.append(" não solicita nenhum tipo de pagamento antecipado para solicitação e liberação de empréstimos. ");
        sb.append("Caso tenha dúvidas, entre em contato com nossa Central de Atendimento, de segunda a sexta, das 9h às 19h. Telefone: +55（11）2124-3485");
        sb.append("</div><div class=\"down-content\" style=\"margin:27px auto 16px;\">");
        sb.append("<div class=\"code-box2\" style=\"width:107px;height:106px;overflow: hidden;margin: 0 auto;\">");
        sb.append("<img style=\"width:100%;height:100%;overflow:hidden;display:block;\" src=\"https://s3-sa-east-1.amazonaws.com/hujin2-s1/email/img_1.png\" alt=\"\"></div>");
        sb.append("<span class=\"code-txt\" style=\"width:100%;text-align: center;display: block;font-size:14px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(88,88,88,1);line-height:23px;\">");
        sb.append("Escaneie o QR code para baixar MyLoan");
        sb.append("</span></div><div class=\"down-btn\" style=\"width:120px;height:60px;margin:11px auto 19px;\"><a href=\"https://play.google.com/store/apps/details?id=com.ctj.swk.Myloan\">");
        sb.append("<img style=\"display: block;width:100%;height:100%;\" src=\"https://s3-sa-east-1.amazonaws.com/hujin2-s1/email/img_2.png\" alt=\"\"></a></div>");
        sb.append("<div class=\"footer\" style=\"margin:20px auto;font-size:13px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(190,190,190,1);line-height:23px;\">");
        sb.append("<p style=\" margin:0 auto;\"><span style=\"display: block;text-align: center;\">");
        sb.append("Panda do Brasil Tecnologia Financeira LTDA Av Pres Juscelino Kubitscheck 1455,4 andar");
        sb.append("</span><span style=\"display: block;text-align: center;\"><a href=\"http://home.pandafintech.com.br/\">Site oficial</a> | <a href=\"http://home.pandafintech.com.br/privacy?lang=pt_PT\">Política de privacidade</a></span></p></div></div></body></html>");

        return sb.toString();
    }


    public static String getSubject5(String appName) {
        return "Utilizar boleto para realizar pagamento";
    }
    public static void main(String[] args) {
        EmailTemplateEntity temp = new EmailTemplateEntity();
        temp.setOrderNo("sda31d3a21d3a21d3");
        temp.setAppName("elaon");
        temp.setUserName("wangsi");
        temp.setBoleteCode("d1qw615q6d1q6w15d61");
        temp.setRepaymentDate("12/12/2018");
        temp.setAccountCard("12313132132132");
        temp.setPayDate("12/12/2018");
        temp.setLoanMoney(2000.0);
        temp.setLoanMonths(3);
        temp.setCurrentRepay(12365.0);
        //temp.setLoanInterest(0.1);
        temp.setTechnologyChargeRate(0.1);
        temp.setAuditChargeRate(0.1);
        temp.setDayInterest(0.1);
        temp.setOtherExpenses(125.0);
        ArrayList<RepaymentPlan> list = new ArrayList<RepaymentPlan>();
        RepaymentPlan paln = new RepaymentPlan();
        paln.setCurrentPeriod(1);
        paln.setTotalPeriod(3);
        paln.setPlanRepaymentDateStr("12/12/2018");
        paln.setCurrentRepay(123.0);
        paln.setPayMoney(124.0);
        paln.setOverDueFine(56.0);
        paln.setInterestMoney(10.0);
        list.add(paln);

        paln = new RepaymentPlan();
        paln.setCurrentPeriod(1);
        paln.setTotalPeriod(3);
        paln.setPlanRepaymentDateStr("12/12/2018");
        paln.setCurrentRepay(123.0);
        paln.setPayMoney(124.0);
        paln.setOverDueFine(56.0);
        paln.setInterestMoney(10.0);
        list.add(paln);

        paln = new RepaymentPlan();
        paln.setCurrentPeriod(1);
        paln.setTotalPeriod(3);
        paln.setPlanRepaymentDateStr("12/12/2018");
        paln.setCurrentRepay(123.0);
        paln.setPayMoney(124.0);
        paln.setOverDueFine(56.0);
        paln.setInterestMoney(10.0);
        list.add(paln);

        temp.setRepaymentPlanList(list);
        System.out.println(getHtml5(temp,"http://asdhad.com"));
    }
}
