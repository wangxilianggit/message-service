package com.panshi.hujin2.message.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 生成规则: 固定30位数字串，前17位为精确到毫秒的时间yyyyMMddhhmmssSSS，后3位为自增数字
 */
public class TransactionIdBuilder {

    private static int index = 1;

    /**
     * 生成20位唯一数
     *
     * @return
     */
    public synchronized static String getTransactionId(){
        StringBuilder transaction_id = new StringBuilder();
        transaction_id.append(getDataStr());
        String indexStr = String.valueOf(index);
        for (int i = 0; i < 4-indexStr.length(); i++) {
            transaction_id.append("0");
        }
        transaction_id.append(indexStr);
        index++;
        return transaction_id.toString();
    }

    private static String getDataStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        return sdf.format(new Date());

    }

}
