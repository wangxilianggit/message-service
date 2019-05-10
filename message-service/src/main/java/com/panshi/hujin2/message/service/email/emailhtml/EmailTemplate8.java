package com.panshi.hujin2.message.service.email.emailhtml;

import java.util.ArrayList;

/**
 * @author ycg 2018/11/29 17:35
 *
 * 账单已经逾期（分期）
 * 还款日当天10：00
 */
public class EmailTemplate8 {
    public static String getHtml8(EmailTemplateEntity emailTemplateEntity, String boletoUrl){

        RepaymentPlan repaymentPlan = emailTemplateEntity.getRepaymentPlanList().get(0);
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
        sb.append("! informamos que venceu o prazo de pagamento do seu empréstimo.");
        sb.append(" Recomendamos que realize o pagamento o mais rápido possível para assim evitar o acúmulo de moras por atraso de 60% ao mês e negativação do seu CPF no SPC.");
        sb.append("</div><div class=\"content-table\" style=\"display: block;max-width: 720px;\">");
        sb.append("<h2 class=\"table-title\" style=\"font-size:16px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(0,180,239,1);line-height:23px;margin:20px auto 13px;\">");
        // 借款订单表头
        sb.append("Proposta de empréstimo");
        sb.append("</h2><ul style=\"display: block;border:1px solid rgba(191,191,191,1);border-radius:10px;padding:10px;\">");
        sb.append("<li style=\"display:block;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:28px;overflow: hidden;\">");
        sb.append("<label style=\"float:left;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:23px;\">");
        // 订单编号
        sb.append("Número de proposta");
        sb.append("</label><p style=\"float:right;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(88,88,88,1);line-height:23px;margin:0;padding:0;border:none;\">");
        sb.append(emailTemplateEntity.getOrderNo());
        sb.append("</p></li><li style=\"display:block;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:28px;overflow: hidden;\">");
        sb.append("<label style=\"float:left;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:23px;\">");
        // 应还期数1/3
        sb.append("Parcela");
        sb.append("</label><p style=\"float:right;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(88,88,88,1);line-height:23px;margin:0;padding:0;border:none;\">");
        sb.append(String.valueOf(repaymentPlan.getCurrentPeriod())+"/"+String.valueOf(repaymentPlan.getTotalPeriod()));
        sb.append("</p></li><li style=\"display:block;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:28px;overflow: hidden;\">");
        sb.append("<label style=\"float:left;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:23px;\">");
        // 应还日期
        sb.append("Data de vencimento");
        sb.append("</label><p style=\"float:right;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(88,88,88,1);line-height:23px;margin:0;padding:0;border:none;\">");
        sb.append(repaymentPlan.getPlanRepaymentDateStr());
        sb.append("</p></li><li style=\"display:block;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:28px;overflow: hidden;\">");
        sb.append("<label style=\"float:left;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1)line-height:23px;\">");
        // 应还金额
        sb.append("Valor a ser pago");
        sb.append("</label><p style=\"float:right;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(88,88,88,1);line-height:23px;margin:0;padding:0;border:none;\">R$ ");
        sb.append(BrMoneyFormatUtil.formatMoney(repaymentPlan.getCurrentRepay()));
        sb.append("</p></li><li style=\"display:block;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:28px;overflow: hidden;\">");
        sb.append("<label style=\"float:left;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:23px;\">");
        // 融资金额
        sb.append("Valor financiado");
        sb.append("</label><p style=\"float:right;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(88,88,88,1);line-height:23px;margin:0;padding:0;border:none;\">R$ ");
        sb.append(BrMoneyFormatUtil.formatMoney(repaymentPlan.getPayMoney()));
        sb.append("</p></li><li style=\"display:block;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:28px;overflow: hidden;\">");
        sb.append("<label style=\"float:left;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:23px;\">");
        // 借款利息
        sb.append("Juros");
        sb.append("</label><p style=\"float:right;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(88,88,88,1);line-height:23px;margin:0;padding:0;border:none;\">R$ ");
        sb.append(BrMoneyFormatUtil.formatMoney(repaymentPlan.getInterestMoney()));
        sb.append("</p></li><li style=\"display:block;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(130,129,129,1);line-height:28px;overflow: hidden;\">");
        // 逾期金额
        sb.append("Despesas por atraso");
        sb.append("</label><p style=\"float:right;font-size:15px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(88,88,88,1);line-height:23px;margin:0;padding:0;border:none;\">R$ ");
        sb.append(BrMoneyFormatUtil.formatMoney(repaymentPlan.getOverDueFine()));
        sb.append("</p></li></ul></div><div class=\"code-content\" style=\"margin:25px auto;font-size:16px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(88,88,88,1);line-height:23px;\">");

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
        sb.append("</div>");

        // 公共底
        sb.append("<div class=\"down-content\" style=\"margin:27px auto 16px;\">");
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


    public static String getSubject8(String appName) {
        String subject = "O seu empréstimo em MyLoan já venceu o prazo";
        return subject;
    }

    public static void main(String[] args) {
        EmailTemplateEntity temp = new EmailTemplateEntity();
        temp.setOrderNo("sda31d3a21d3a21d3");
        temp.setAppName("elaon");
        temp.setUserName("wangsi");
        temp.setBoleteCode("d1qw615q6d1q6w15d61");
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
        temp.setRepaymentPlanList(list);
        System.out.println(getHtml8(temp,"http://asdhad.com"));
    }
}
