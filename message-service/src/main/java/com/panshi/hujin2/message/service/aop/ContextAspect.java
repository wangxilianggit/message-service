package com.panshi.hujin2.message.service.aop;

import com.panshi.hujin2.base.service.Context;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author ZhangZhiHao 2018/7/4 8:53
 * 对语言环境统一处理
 */
@Aspect
@Component
public class ContextAspect {

    @Before("execution( * com.panshi.hujin2.message.service.email.facadeimpl..*.*(..)) && args(..,context)")
    public void contextLocal(Context context) {
        LocaleContextHolder.setLocale(context.getLocale());
    }
}