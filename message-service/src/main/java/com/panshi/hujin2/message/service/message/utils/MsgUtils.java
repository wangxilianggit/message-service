package com.panshi.hujin2.message.service.message.utils;

import com.panshi.hujin2.aws.utils.ExpiryMap;
import com.panshi.hujin2.base.service.Context;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by shenjiankang on 2018/6/20 14:24
 */
public class MsgUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgUtils.class);

    public static ExpiryMap expiryMap = new ExpiryMap();

    /**
     * @description         查询‘text’中包含几个‘symbol’
     * @param text
     * @param symbol
     * @Author shenjiankang
     * @Date 2018/6/20 16:14
     */
    public static Integer getContainNum(String text,String symbol){
        if(StringUtils.isBlank(text) || StringUtils.isBlank(symbol)){
            return 0;
        }
        int num = text.length();
        int subtrahend = text.replaceAll(symbol, "").length();
        return (num-subtrahend)/symbol.length();
    }

    /**
     * @description:        替换掉原本的动态字符，返回真正发送的消息内容（如果匹配参数不正确，抛出异常）
     * @param template      短信模板
     * @param symbol        替换字符
     * @param paramList     替换的参数列表
     * @param context       国际化上下文
     * @Author shenjiankang
     * @Date 2018/7/3 16:30
     */
    public static String getSendContent(String template,
                                         String symbol,
                                         List<String> paramList,
                                         Context context){
        Integer num = getContainNum(template,symbol);
        if(num != 0){
            if(paramList == null){
                ExceptionMessageUtils.throwExceptionParamIsNull(context);
            }else if(num.equals(paramList.size())){
                Object[] paramArr = paramList.toArray();
                template = String.format(template,paramArr);
            }else{
                ExceptionMessageUtils.throwExceptionTemplateReplaceParamInvalid(context);
            }
        }
        return template;
    }

    /**
     * @description:        替换掉原本的动态字符，返回真正发送的消息内容（如果匹配参数不正确，返回null）
     * @param template
     * @param symbol
     * @param paramList
     * @Author shenjiankang
     * @Date 2018/8/2 10:36
     */
    public static String getSendContent(String template,
                                        String symbol,
                                        List<String> paramList){
        Integer num = getContainNum(template,symbol);
        if(num != 0){
            if(paramList == null){
                return null;
            }else if(num.equals(paramList.size())){
                Object[] paramArr = paramList.toArray();
                template = String.format(template,paramArr);
            }else{
                return null;
            }
        }
        return template;
    }


    /**
     * @description:            验证手机号码每日是否已经超过发送次数限额,没用超过限制返回true
     * @param phoneNumber       手机号
     * @param everydaySendNum   每日限额次数
     * @Author shenjiankang
     * @Date 2018/7/15 0:24
     */
    public static boolean checkSendNum(String phoneNumber,Integer everydaySendNum){
        //已经发送的次数
        Integer sendNum = 0;
        Object sendN = MsgUtils.expiryMap.get(phoneNumber);

        LOGGER.info("--------开始检查手机发送次数--------");
        if(sendN != null){
            sendNum = (Integer)sendN;
            LOGGER.info("--------手机号[{}]当日已经发送[{}]次，总限制条数[{}]次",phoneNumber,sendNum,everydaySendNum);
        }else {
            LOGGER.info("--------手机号码[{}]当日第一次发送，总限制条数[{}]次",phoneNumber,everydaySendNum);
            return true;
        }

        if(sendNum >= everydaySendNum){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 获取到当天结束还有多少秒 (注意：返回的单位是秒)
     * @return
     */
    public static Long getEndTime(){
        Calendar curDate = Calendar.getInstance();
        Calendar nextDayDate = new GregorianCalendar(curDate.get(Calendar.YEAR),
                                                    curDate.get(Calendar.MONTH),
                                            curDate.get(Calendar.DATE)+1, 0, 0, 0);
        return (nextDayDate.getTimeInMillis() - curDate.getTimeInMillis())/1000;
    }


    /**
     * 发送短信日志输出
     * phoneNumber      手机号
     * sendNum          当天已经发送的次数
     * endTime          当天剩余时间（ms  毫秒）
     */
    public static void printMsgSendLogs(String phoneNumber,Integer sendNum, Long endTime){
        double  b = 3600 * 1000;
        LOGGER.info("--------发送完成。手机号[{}]当日已经发送[{}]次，离0点结束剩余时间[{}]小时，",phoneNumber,sendNum,endTime/b);

        //获取当前时间
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(currentDate);
        LOGGER.info("--------当前时间  [{}]，",dateStr);

        LOGGER.info("--------当天剩余时间毫秒数  [{}]s，",endTime);
    }



    //根据自定义占位符替换模板参数
    /**
     * @description:        Date转字符串
     * @param date          时间参数
     * @param format        需要转行的格式 默认
     * @Author shenjiankang
     * @Date 2018/7/27 15:09
     */
    public static String dateToString(Date date, String format){
        if(date == null){
            return "";
        }
        if(StringUtils.isBlank(format)){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String dateStr = dateFormat.format(date);
        return dateStr;
    }




    /**
     * @description:        正则匹配出 字符串文本中需要取出的元素
     * @param text          字符串文本
     * @param regular       正则表达式
     * @Author shenjiankang
     * @Date 2018/7/27 15:10
     */
    public static List<String> extractContentByRegular(String text, String regular){
        List list = new ArrayList();
        Pattern p = Pattern.compile(regular);
        Matcher m = p.matcher(text);
        while (m.find()){
            list.add(m.group().substring(1,m.group().length()));
        }
        return list;
    }


    /**
     * @description:        替换模板中的自定义占位符
     * @param sourceText    没替换之前的模板
     * @param regular       正则表达式
     * @param paramList     替换参数list
     * @param context       国际化上下文对象
     * @Author shenjiankang
     * @Date 2018/7/27 16:24
     */
    public static String replaceTemplate(String sourceText,String regular,List paramList, Context context){
        //原文本中的自定义占位符
        List<String> resList = extractContentByRegular(sourceText,regular);

        //判断模板中有没有占位符，如果有，和替换的参数是否相等。
        if(paramList == null){
            if(resList.size()>0){
                //如果自定义占位符大于0，替换参数list为空的话 报错
                ExceptionMessageUtils.throwExceptionParamInvalid(context);
            }else{
                return sourceText;
            }
        }else {
            if(paramList.size() != resList.size()){
                ExceptionMessageUtils.throwExceptionTemplateReplaceParamInvalid(context);
            }
        }

        String replaceText = "";
        for (int i =0; i<paramList.size(); i++){
            Object obj = paramList.get(i);
            if(obj instanceof java.util.Date){
                String strFormat = resList.get(i);
                //格式化Date
                String dateStr = dateToString((java.util.Date)obj, strFormat);
                paramList.set(i,dateStr);
            }
            //拼接字符串
            if("".equals(replaceText)){
                replaceText = sourceText.replace("{"+(resList.get(i))+"}", String.valueOf(paramList.get(i)));
            }else{
                replaceText= replaceText.replace("{"+(resList.get(i))+"}", String.valueOf(paramList.get(i)));
            }
            LOGGER.info("第[{}]次 拼接后 ：[{}] ",i,replaceText );
        }
        return replaceText;
    }


}
