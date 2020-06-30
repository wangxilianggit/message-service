package com.panshi.hujin2.message.facade.bo.kmi;

import lombok.Data;

/**
 * @author shenJianKang
 * @date 2020/6/29 16:19
 *
 * KMI TOKEN AND BALANCE
 */

@Data
public class KMITokenBalanceInfoBO {

    /**
     *  token
     */
    private String token;

    /**
     *  余额
     */
    private Double balance;

    /**
     *  响应 code
     */
    private String code;

    /**
     *  响应 描述
     */
    private String desc;
}
