package com.panshi.hujin2.message.domain.qo.getui;
import com.panshi.hujin2.message.domain.qo.BaseQO;
import lombok.Data;

/**
 * create by shenjiankang on 2018/7/6 20:49
 */
@Data
public class AppPushHistoryQO extends BaseQO {

    /**
     *  主键id
     */
    private Integer id;
    /**
     *  app应用id
     */
    private Integer appId;
    /**
     *  用户id
     */
    private Integer userId;
    /**
     *  所属的业务类型id
     */
    private Integer businessTypeId;
    /**
     *  通知栏标题
     */
    private String title;
    /**
     *  通知栏内容
     */
    private String text;
    /**
     *  0--未读；1--已读
     */
    private Boolean status;

    /**
     * 下一个起始行id
     * */
    private Integer nextId;

}
