package com.panshi.hujin2.message.service.message.facade;

import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.qo.CountryConfigQO;
import com.panshi.hujin2.message.domain.qo.MsgBusinessTypeQO;
import com.panshi.hujin2.message.domain.qo.MsgTemplateCountryQO;
import com.panshi.hujin2.message.domain.qo.MsgTemplateQO;
import com.panshi.hujin2.message.facade.IMsgTemplateFacade;
import com.panshi.hujin2.message.facade.bo.*;
import com.panshi.hujin2.message.service.message.IMsgDBService;
import com.panshi.hujin2.message.service.message.utils.ExceptionMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by shenjiankang on 2018/6/29 11:07
 */
@Service
public class MsgTemplateFacadeImpl implements IMsgTemplateFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgTemplateFacadeImpl.class);

    @Autowired
    private IMsgDBService msgDBService;

    @Override
    public BasicResult<Void> addMsgBusinessType(MsgBusinessTypeInputBO inputBO, Context context) {
        try {
            if (msgDBService.addMsgBusinessType(inputBO, context) > 0) {
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ADD_FAIL.getCode(),
                    MessageFactory.getMsg("G19880003", context.getLocale()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> updateMsgBusinessType(MsgBusinessTypeUpdateBO updateBO, Context context) {
        try {
            msgDBService.updateMsgBusinessType(updateBO, context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<List<MsgBusinessTypeOutputBO>> queryMsgBusinessType(MsgBusinessTypeQO qo, Context context) {
        try {
            if (qo == null) {
                qo = new MsgBusinessTypeQO();
            }
            List<MsgBusinessTypeOutputBO> res = msgDBService.queryMsgBusinessType(qo);
            return BasicResult.ok(res, qo.getPage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> addMsgTemplate(MsgTemplateInputBO inputBO, Context context) {
        try {
            if (msgDBService.addMsgTemplate(inputBO, context) > 0) {
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ADD_FAIL.getCode(),
                    MessageFactory.getMsg("G19880003", context.getLocale()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> updateMsgTemplate(MsgTemplateUpdateBO updateBO, Context context) {
        try {
            msgDBService.updateMsgTemplate(updateBO, context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<List<MsgTemplateOutputBO>> queryMsgTemplate(MsgTemplateQO qo, Context context) {
        try {
            if (qo == null) {
                qo = new MsgTemplateQO();
            }
            List<MsgTemplateOutputBO> res = msgDBService.queryTemplate(qo);
            return BasicResult.ok(res, qo.getPage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> addMsgTemplateCountry(MsgTemplateCountryInputBO inputBO, Context context) {
        try {
            if (msgDBService.addMsgTemplateCountry(inputBO, context) > 0) {
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ADD_FAIL.getCode(),
                    MessageFactory.getMsg("G19880003", context.getLocale()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> updateMsgTemplateCountry(MsgTemplateCountryUpdateBO updateBO, Context context) {
        try {
            msgDBService.updateMsgTemplateCountry(updateBO, context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<List<MsgTemplateCountryOutputBO>> queryMsgTemplateCountry(MsgTemplateCountryQO qo,
                                                                                 Context context) {
        try {
            if (qo == null) {
                qo = new MsgTemplateCountryQO();
            }
            List<MsgTemplateCountryOutputBO> res = msgDBService.queryMsgTemplateCountry(qo);
            return BasicResult.ok(res, qo.getPage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> addCountryConfig(CountryConfigInputBO inputBO, Context context) {
        try {
            if (msgDBService.addCountryConfig(inputBO, context) > 0) {
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ADD_FAIL.getCode(),
                    MessageFactory.getMsg("G19880003", context.getLocale()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Void> updateCountryConfig(CountryConfigUpdateBO updateBO, Context context) {
        try {
            msgDBService.updateCountryConfig(updateBO, context);
            return BasicResult.ok();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<List<CountryConfigOutputBO>> queryCountryConfig(CountryConfigQO qo, Context context) {
        try {
            if (qo == null) {
                qo = new CountryConfigQO();
            }
            List<CountryConfigOutputBO> res = msgDBService.queryCountryConfig(qo);
            return BasicResult.ok(res, qo.getPage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<List<ApplicationsOutputBO>> queryApplications(Context context) {
        try {
            List<ApplicationsOutputBO> res = msgDBService.queryApplications();
            return BasicResult.ok(res);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Integer> addInfobipMsgReports(InfobipMsgReportsInputBO inputBO, Context context) {
        try {
            Integer count = msgDBService.addInfobipMsgReports(inputBO, context);
            return BasicResult.ok(count);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Integer> addMsgResponseResult(MsgResponseResultInputBO inputBO, Context context) {
        try {
            Integer count = msgDBService.addMsgResponseResult(inputBO, context);
            return BasicResult.ok(count);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }

    @Override
    public BasicResult<Integer> addSubmailMsgSubhook(SubmailMsgSubhookInputBO inputBO, Context context) {
        try {
            Integer count = msgDBService.addSubmailMsgSubhook(inputBO, context);
            return BasicResult.ok(count);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExceptionMessageUtils.throwDefinedException(e, context);
        }
    }


}
