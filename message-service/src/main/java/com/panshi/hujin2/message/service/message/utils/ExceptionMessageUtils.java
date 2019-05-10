package com.panshi.hujin2.message.service.message.utils;

import com.panshi.hujin2.base.common.factory.MessageFactory;
import com.panshi.hujin2.base.domain.result.BasicResult;
import com.panshi.hujin2.base.domain.result.BasicResultCode;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.domain.exception.MessageException;
import org.apache.commons.lang.StringUtils;

/**
 * create by shenjiankang on 2018/6/13 15:29
 */
public class ExceptionMessageUtils {

    /**
     * @description:    异常信息封装
     * @param e         异常对象
     * @param context   国际化上下文对象
     * @Author shenjiankang
     * @Date 2018/6/13 15:32
     */
    public static BasicResult throwDefinedException(Exception e, Context context){
        if(e instanceof MessageException){
            return BasicResult.error(BasicResultCode.ERROR.getCode(),
                    e.getMessage());
        }
        return BasicResult.error(BasicResultCode.SYS_EXCEPTION.getCode(),
                MessageFactory.getMsg("G19880001",context.getLocale()));
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
        throw new MessageException(MessageFactory.getMsg("G19880002",context.getLocale()));
    }


    /**
     * @description:    抛出“短信模板不存在”的自定義異常
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/2 18:07
     */
    public static void throwExceptionTemplateNotExist(Context context){
        throw new MessageException(MessageFactory.getMsg("G19880105",context.getLocale()));
    }


    /**
     * @description:    抛出“短信模板已失效”的自定義異常
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/3 14:57
     */
    public static void throwExceptionTemplateLose(Context context){
        throw new MessageException(MessageFactory.getMsg("G19880106",context.getLocale()));
    }

    /**
     * @description:    抛出“替换参数和模板不符”的自定義異常
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/3 16:03
     */
    public static void throwExceptionTemplateReplaceParamInvalid(Context context ){
        throw new MessageException(MessageFactory.getMsg("G19880107",context.getLocale()));
    }

    /**
     * @description:    抛出“修改失败”的自定義異常
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/5 10:12
     */
    public static void throwExceptionUpdateFail(Context context){
        throw new MessageException(MessageFactory.getMsg("G19880004",context.getLocale()));
    }

    /**
     * @description:
     * @param context   抛出“发送失败”的自定義異常
     * @Author shenjiankang
     * @Date 2018/7/5 11:54
     */
    public static void throwExceptionSendFail(Context context){
        throw new MessageException(MessageFactory.getMsg("G19880108",context.getLocale()));
    }

    /**
     * @description:    抛出“数据已存在”的自定義異常
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/12 20:28
     */
    public static void throwExceptionDataExists(Context context){
        throw new MessageException(MessageFactory.getMsg("G19880007",context.getLocale()));
    }

    /**
     * @description:    抛出“參數錯誤”的自定義異常
     * @param context   国际化上下文对象
     * @Author shenjiankang
     * @Date 2018/6/25 17:09
     */
    public static void throwExceptionParamInvalid(Context context){
        throw new MessageException(MessageFactory.getMsg("G19880006",context.getLocale()));
    }

    /**
     * @description:    抛出“该手机号码当日发送次数已用完”的自定義異常
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/15 0:33
     */
    public static void throwExceptionMobileSendNumOverLimit(Context context){
        throw new MessageException(MessageFactory.getMsg("G19880109",context.getLocale()));
    }

    /**
     * @description:    抛出“参数长度过长”的自定義異常
     * @param context
     * @Author shenjiankang
     * @Date 2018/7/31 11:12
     */
    public static void throwExceptionParamTooLong(Context context){
        throw new MessageException(MessageFactory.getMsg("G19880110",context.getLocale()));
    }
}
