package com.panshi.hujin2.message.facade.bo.niuXin;

import lombok.Data;

/**
 * @author shenJianKang
 * @date 2020/6/30 9:54
 *
 * 牛信 余额
 */

@Data
public class NiuXinBalanceInfoBO {

    /**
     *  余额
     */
    private Double balance;

    /**
     * 信用余额
     */
    private Double creditBalance;

    /**
     * 结果编码
     */
    private String code;

    /**
     *  接口返回的结果
     */
    private String resultStr;

}
