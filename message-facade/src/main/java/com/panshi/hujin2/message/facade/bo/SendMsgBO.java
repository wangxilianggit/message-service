package com.panshi.hujin2.message.facade.bo;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.enums.ChannelEnum;
import com.panshi.hujin2.message.domain.enums.MsgTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * @author shenJianKang
 * @date 2020/5/8 14:45
 */
@Data
public class SendMsgBO {

    /**
     *  app枚举
     */
    private ApplicationEnmu applicationEnmu;
    /**
     *  发送目标手机号
     */
    private String phoneNumber;
    /**
     *  短信模板code
     */
    private String templateCode;
    /**
     *  短信替换变量，按短信文案%s顺序
     */
    private List<String> paramList;
    /**
     *  国际化
     */
    private Context context;

    /**
     *  发送渠道
     */
    private ChannelEnum channelEnum;

    /**
     * 短信类型
     * @see MsgTypeEnum
     */
    private Integer msgType;

}
