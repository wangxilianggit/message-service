package com.panshi.hujin2.message.service.email.emailhtml;

/**
 * @author ycg 2018/11/29 17:35
 * <p>
 * 借款审核通过
 * 风控审核通过，实时发送
 */
public class EmailTemplate1 {
    public static String getHtml1(EmailTemplateEntity emailTemplateEntity) {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html><html style=\"width:720px;max-width:720px;margin:0 auto;position:relative;\">");
        sb.append("<head><meta charset=\"utf-8\"><meta content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no\" name=\"viewport\"></head>");
        sb.append("<body><div class=\"wrapper\" style=\"width:720px;max-width:720px;background-color: rgba(255,255,255,1);font-size:18px;margin:0 30px;color:rgba(88,88,88,1)\">");
        sb.append("<div class=\"header\" style=\"margin:30px auto 0;display: flex;justify-content: center;\">");
        sb.append("<div class=\"img-logo\" style=\"width:27px;height:27px;display: block;position: relative;\"><img style=\"width:100%;height:100%;display: block;\" src=\"https://s3-sa-east-1.amazonaws.com/hujin2-s1/email/img_3.png\" alt=\"\"></div>");
        sb.append("<span class=\"title\" style=\"width:63px;height:16px;font-size:18px;font-family:HKGrotesk-SemiBoldItalic;font-weight:normal;color:rgba(0,180,239,1);line-height:27px;margin-left:6px;\"> ");
        sb.append(emailTemplateEntity.getAppName());
        sb.append(" </span></div><div class=\"content\" style=\"font-size:16px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(88,88,88,1);line-height:23px;margin:24px auto 0;\"><p>Temos uma ótima notícia, <span> ");
        // 用户姓名
        sb.append(emailTemplateEntity.getUserName());
        sb.append("!<br/> O seu pedido já foi aprovado! Agora basta inserir seus dados de pagamento no aplicativo para que seu empréstimo seja concluído.");
        sb.append("</p></div><div class=\"content-table\" style=\"display: block;max-width: 720px;\">");
        sb.append("<h2 class=\"table-title\" style=\"font-size:16px;font-family:HKGrotesk-Medium;font-weight:500;color:rgba(0,180,239,1);line-height:23px;margin:20px auto 13px;\">");
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

    /**
     * 标题
     * @param appName
     * @return
     */
    public static String getSubject1(String appName) {
        String subject = "O seu pedido de empréstimo foi aprovado!";
        return subject;
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
        temp.setOverDueDays(1);
        temp.setDueDays(25);
        //temp.setLoanInterest(0.1);
        temp.setTechnologyChargeRate(0.1);
        temp.setAuditChargeRate(0.1);
        temp.setDayInterest(0.1);
        temp.setOtherExpenses(125.0);
        temp.setOverDueFine(10.2);
        System.out.println(getHtml1(temp));
    }
}
