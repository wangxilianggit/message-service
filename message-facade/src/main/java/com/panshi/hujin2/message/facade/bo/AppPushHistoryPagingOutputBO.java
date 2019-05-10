package com.panshi.hujin2.message.facade.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * create by shenjiankang on 2018/7/7 10:00
 */
@Data
public class AppPushHistoryPagingOutputBO implements Serializable {

    private static final long serialVersionUID = -1240050221530358434L;
    /**
     *  分页limit外，下一条数据的id
     * */
    private Integer nextId;


    List<AppPushHistoryOutputBO> appPushHistoryOutputBOList;
}
