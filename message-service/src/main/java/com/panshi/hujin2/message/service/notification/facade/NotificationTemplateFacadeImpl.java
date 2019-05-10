package com.panshi.hujin2.message.service.notification.facade;

import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.qo.CountryConfigQO;
import com.panshi.hujin2.message.domain.qo.getui.AppPushBusinessTypeQO;
import com.panshi.hujin2.message.domain.qo.getui.AppPushTemplateQO;
import com.panshi.hujin2.message.domain.qo.getui.TemplateQO;
import com.panshi.hujin2.message.facade.INotificationTemplateFacade;
import com.panshi.hujin2.message.facade.bo.*;
import com.panshi.hujin2.message.service.notification.getui.INotificationTemplateService;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by shenjiankang on 2018/6/29 15:16
 */
@Service
public class NotificationTemplateFacadeImpl implements INotificationTemplateFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationTemplateFacadeImpl.class);

    @Autowired
    private INotificationTemplateService notificationTemplateService;

    @Override
    public BasicResult<List<ApplicationsOutputBO>> queryApplications(Context context) {
        try {
            List<ApplicationsOutputBO> res = notificationTemplateService.queryApplications();
            return BasicResult.ok(res);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return NotificationExceptionUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<Void> addPushTemplate(AppPushTemplateInputBO inputBO, Context context) {
        try {
            if(notificationTemplateService.addPushTemplate(inputBO,context)>0){
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ADD_FAIL.getCode(),
                    MessageFactory.getMsg("G19990003",context.getLocale()));
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return NotificationExceptionUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<Void> updatePushTemplate(AppPushTemplateUpdateBO updateBO, Context context) {
        try {
            notificationTemplateService.updatePushTemplate(updateBO,context);
            return BasicResult.ok();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return NotificationExceptionUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<List<AppPushTemplateOutputBO>> queryPushTemplate(AppPushTemplateQO qo, Context context) {
        try {
            if (qo == null){
                qo = new AppPushTemplateQO();
            }
            List<AppPushTemplateOutputBO> res = notificationTemplateService.queryPushTemplate(qo);
            return BasicResult.ok(res,qo.getPage());
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return NotificationExceptionUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<List<AppPushTemplateOutputBO>> queryTemplate(TemplateQO qo, Context context) {
        try {
            if (qo == null){
                qo = new TemplateQO();
            }
            List<AppPushTemplateOutputBO> res = notificationTemplateService.queryTemplate(qo);
            return BasicResult.ok(res,qo.getPage());
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return NotificationExceptionUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<Void> addBusinessType(AppPushBusinessTypeInputBO inputBO, Context context) {
        try {
            if(notificationTemplateService.addBusinessType(inputBO,context)>0){
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ADD_FAIL.getCode(),
                    MessageFactory.getMsg("G19990003",context.getLocale()));
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return NotificationExceptionUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<Void> updateBusinessType(AppPushBusinessTypeUpdateBO updateBO, Context context) {
        try {
            notificationTemplateService.updateBusinessType(updateBO, context);
            return BasicResult.ok();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return NotificationExceptionUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<List<AppPushBusinessTypeOutputBO>> queryBusinessType(AppPushBusinessTypeQO qo, Context context) {
        try {
            List<AppPushBusinessTypeOutputBO> res = notificationTemplateService.queryBusinessType(qo);
            return BasicResult.ok(res, qo.getPage());
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return NotificationExceptionUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<Void> addCountryConfig(CountryConfigInputBO inputBO, Context context) {
        try {
            if(notificationTemplateService.addCountryConfig(inputBO, context) > 0){
                return BasicResult.ok();
            }
            return BasicResult.error(BasicResultCode.ADD_FAIL.getCode(),
                    MessageFactory.getMsg("G19990003",context.getLocale()));
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return NotificationExceptionUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<Void> updateCountryConfig(CountryConfigUpdateBO updateBO, Context context) {
        try {
            notificationTemplateService.updateCountryConfig(updateBO, context);
            return BasicResult.ok();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return NotificationExceptionUtils.throwDefinedException(e,context);
        }
    }

    @Override
    public BasicResult<List<CountryConfigOutputBO>> queryCountryConfig(CountryConfigQO qo, Context context) {
        try {
            if(qo == null){
                qo = new CountryConfigQO();
            }
            List<CountryConfigOutputBO> res = notificationTemplateService.queryCountryConfig(qo);
            return BasicResult.ok(res,qo.getPage());
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return NotificationExceptionUtils.throwDefinedException(e,context);
        }
    }


}
