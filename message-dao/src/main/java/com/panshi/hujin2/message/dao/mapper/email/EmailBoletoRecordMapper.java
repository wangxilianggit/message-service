package com.panshi.hujin2.message.dao.mapper.email;

import com.panshi.hujin2.message.dao.model.EmailBoletoRecordDO;

/**
 * @author ycg 2018/11/28 19:24
 */

public interface EmailBoletoRecordMapper {

   void save(EmailBoletoRecordDO emailBoletoRecordDO);

    EmailBoletoRecordDO getOrderNo(String orderNo);
}
