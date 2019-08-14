package com.panshi.hujin2.message.service.message.ina_sms_operator.bo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shenJianKang
 * @date 2019/8/12 17:59
 */
@Data
@XStreamAlias("PUSH")
public class XMLPushBO implements Serializable {

    private static final long serialVersionUID = 4812700651952196024L;


    @XStreamAlias("STATUS")
    private String status;

    @XStreamAlias("TRANSID")
    private String transId;

    @XStreamAlias("MSG")
    private String msg;

//    @XStreamAlias("")
//    private String ;


}
