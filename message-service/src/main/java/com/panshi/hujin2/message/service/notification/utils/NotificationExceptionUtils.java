package com.panshi.hujin2.message.service.notification.utils;

import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.exception.NotificationException;
import org.apache.commons.lang.StringUtils;

/**
 * create by shenjiankang on 2018/6/13 15:29
 */
public class NotificationExceptionUtils {

    /**
     * @description:    异常信息封装
     * @param e         异常对象
     * @param context   国际化上下文对象
     * @Author shenjiankang
     * @Date 2018/6/13 15:32
     */
    public static BasicResult throwDefinedException(Exception e, Context context){
        if(e instanceof NotificationException){
            return BasicResult.error(BasicResultCode.ERROR.getCode(),
                    e.getMessage());
        }
        return BasicResult.error(BasicResultCode.SYS_EXCEPTION.getCode(),
                MessageFactory.getMsg("G19990001",context.getLocale()));
    }

    /**
     * @description:    校驗引用類型對象是否爲空
     * @param context   國際化上下文
     * @param param     可變個數的引用類型對象
     * @Author shenjiankang
     * @Date 2018/6/25 16:19
     */
    public static void verifyObjectIsNull(Context context,Object... param){
        for(int i=0;i<param.length;i++){
            if(param[i] == null){
                throwExceptionParamIsNull(context);
            }
        }
    }

    /**
     * @description:    校驗字符串對象是否爲空，非“null”字符串
     * @param context   國際化上下文
     * @param param     可變個數的引用類型對象
     * @Author shenjiankang
     * @Date 2018/6/25 16:25
     */
    public static void verifyStringIsBlank(Context context,String... param){
        for(int i=0;i<param.length;i++){
            if(StringUtils.isBlank(param[i]) || "null".equals(param[i])){
                throwExceptionParamIsNull(context);
            }
        }
    }

    /**
     * @description:    抛出“參數爲空”的自定義異常
     * @param context   国际化上下文对象
     * @Author shenjiankang
     * @Date 2018/6/25 16:31
     */
    public static void throwExceptionParamIsNull(Context context){
        throw new NotificationException(MessageFactory.getMsg("G19990002",context.getLocale()));
    }

    /**
     * @description:    抛出“參數錯誤”的自定義異常
     * @param context   国际化上下文对象
     * @Author shenjiankang
     * @Date 2018/6/25 17:09
     */
    public static void throwExceptionParamInvalid(Context context){
        throw new NotificationException(MessageFactory.getMsg("G19990006",context.getLocale()));
    }

    /**
     * @description:    抛出"数据不存在"的自定义异常
     * @param context
     * @Author shenjiankang
     * @Date 2018/6/26 16:10
     */
    public static void throwExceptionDataNotExist(Context context){
        throw new NotificationException(MessageFactory.getMsg("G19990010",context.getLocale()));
    }

    /**
     * @description:    抛出"新增失败"的自定义异常
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/2 18:03
     */
    public static void throwExceptionAddFail(Context context){
        throw new NotificationException(MessageFactory.getMsg("G19990003",context.getLocale()));
    }

    /**
     * @description:    抛出“数据已存在”的自定義異常
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/12 20:28
     */
    public static void throwExceptionDataExists(Context context){
        throw new NotificationException(MessageFactory.getMsg("G19990007",context.getLocale()));
    }

    /**
     * @description:    无效的clientId
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/27 15:40
     */
    public static void throwExceptionCidInvalid(Context context){
        throw new NotificationException(MessageFactory.getMsg("G19990105",context.getLocale()));
    }

    /**
     * @description:    替换参数和消息模板不符
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/27 15:39
     */
    public static void throwExceptionTemplateReplaceParamInvalid(Context context){
        throw new NotificationException(MessageFactory.getMsg("G19990103",context.getLocale()));
    }


    /**
     * @description:    抛出“参数长度过长”的自定義異常
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/31 11:12
     */
    public static void throwExceptionParamTooLong(Context context){
        throw new NotificationException(MessageFactory.getMsg("G19990106",context.getLocale()));
    }

    /**
     * @description:    抛出“消息模板不存在”的自定義異常
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/2 18:07
     */
    public static void throwExceptionTemplateNotExist(Context context){
        throw new NotificationException(MessageFactory.getMsg("G19990102",context.getLocale()));
    }
}
