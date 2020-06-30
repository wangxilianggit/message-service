package com.panshi.hujin2.message.facade.bo;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import lombok.Data;

import java.util.List;

/**
 * @author shenJianKang
 * @date 2020/6/24 17:46
 *
 * 批量发送自定义营销短信 BO
 */

@Data
public class BatchSendSelfDefinedMsgBO {


    private ApplicationEnmu applicationEnmu;

    /**
     *  手机号list
     */
    private List<String> phoneNumberList;

    /**
     *  短信内容
     */
    private String msgContent;

//    /**
//     *  发送类型（区分应用发送，还是营销短信批量发送） 1-应用发送；2-营销批量发送；
//     */
//    private Integer sendType;

    /**
     *  任务表直接id  ； 暂时kmi批量发送 需要该字段入库
     */
    private Integer MarketingSmsTaskRecordPrimaryKey;
}
