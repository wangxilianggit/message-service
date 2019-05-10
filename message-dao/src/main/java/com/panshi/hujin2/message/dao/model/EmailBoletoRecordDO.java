package com.panshi.hujin2.message.dao.model;

import lombok.Data;

import java.util.Date;

/**
 * @author ycg 2018/11/28 19:25
 */
@Data
public class EmailBoletoRecordDO {
    private Integer id;
    private String orderNo;
    private String boleteCode;
    private String boleteUrl;
    private Date createTime;
    private Date modifyTime;
}
