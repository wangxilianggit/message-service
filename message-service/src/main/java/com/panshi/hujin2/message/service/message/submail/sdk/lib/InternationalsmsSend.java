package com.panshi.hujin2.message.service.message.submail.sdk.lib;

import com.panshi.hujin2.message.service.message.submail.sdk.lib.base.SenderWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * internationalsms/send 是 SUBMAIL 的国际短信 API。 internationalsms/send 和短信 API 共享短信模板，
 * 当使用 internationalsms/send API 提交短信时，SUBMAIL 会与您账户中短信模板进行匹配，当匹配成功后，
 * 短信立即发送
 *
 * @author submail
 */
@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
//@Scope(value = WebApplicationContext.SCOPE_REQUEST)
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InternationalsmsSend extends SenderWapper {

    @Autowired
    private Internationalsms internationalsms;

    private String TO = "to";
    private String CONTENT = "content";


    public void addTo(String to) {
        requestData.addWithComma(TO, to);
    }

    public void addContent(String content) {
        requestData.addWithComma(CONTENT, content);
    }

    public String send() {
        return internationalsms.send(requestData);
    }


}
	
	
	
	


