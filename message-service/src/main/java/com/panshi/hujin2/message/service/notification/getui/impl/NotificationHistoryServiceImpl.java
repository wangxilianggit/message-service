package com.panshi.hujin2.message.service.notification.getui.impl;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.util.DozerUtils;
import com.panshi.hujin2.base.domain.page.Page;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.dao.mapper.notification.AppPushHistoryMapper;
import com.panshi.hujin2.message.dao.model.AppPushHistory;
import com.panshi.hujin2.message.domain.qo.getui.AppPushHistoryQO;
import com.panshi.hujin2.message.facade.bo.AppPushHistoryInputBo;
import com.panshi.hujin2.message.facade.bo.AppPushHistoryOutputBO;
import com.panshi.hujin2.message.facade.bo.AppPushHistoryPagingOutputBO;
import com.panshi.hujin2.message.service.notification.getui.INotificationHistoryService;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create by shenjiankang on 2018/7/7 11:27
 *
 * 推送历史，消息中心
 */
@Service
public class NotificationHistoryServiceImpl implements INotificationHistoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationHistoryServiceImpl.class);

    @Autowired
    private AppPushHistoryMapper historyMapper;


    @Override
    public void insertBatch(List<AppPushHistoryInputBo> list,
                            Context context) {
        if(list !=null && list.size()>0){
            List<AppPushHistory>  pushHistoryList = DozerUtils.convertList(list, AppPushHistory.class);
            historyMapper.insertBatch(pushHistoryList);
        }
    }

    @Override
    public int addPushHistory(AppPushHistoryInputBo inputBo, Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context,inputBo);
        AppPushHistory pushHistory = DozerUtils.convert(inputBo, AppPushHistory.class);
        return historyMapper.insertSelective(pushHistory);

    }

    @Override
    public AppPushHistoryPagingOutputBO queryPushHistoryByUid(ApplicationEnmu appEnmu,
                                                              Integer uid,
                                                              Integer nextId,
                                                              Integer limit,
                                                              Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context, uid,limit, appEnmu);

        AppPushHistoryPagingOutputBO res = new AppPushHistoryPagingOutputBO();

        AppPushHistoryQO qo = new AppPushHistoryQO();
        qo.setAppId(appEnmu.getCode());
        qo.setUserId(uid);
        Integer count = historyMapper.countPushHistory(qo);

        Page page = qo.getPage();
        if(nextId==null){
            page.setCurrentPage(1);
            qo.setNextId(null);
        }else{
            qo.setNextId(nextId);
        }
        page.setPageSize(limit + 2);
        page.setTotalNumber(count);

        List<AppPushHistory> historyList = historyMapper.queryPushHistory(qo);
        if(historyList!=null && historyList.size()>0){
            List<AppPushHistoryOutputBO> historyOutputBOList =
                    DozerUtils.convertList(historyList, AppPushHistoryOutputBO.class);

            List<AppPushHistoryOutputBO> limitOutputBOList = null;
            if(limit < historyList.size()){
                //判断limit之后是不是还有数据
                limitOutputBOList = historyOutputBOList.subList(0,limit);
                res.setNextId(historyOutputBOList.get(limit).getId());
            }else{
                limitOutputBOList = historyOutputBOList;
                res.setNextId(-1);
            }
            //发送时间设置成时间戳
            for (AppPushHistoryOutputBO bo: limitOutputBOList){
                bo.setSendTime(String.valueOf(bo.getCreateTime().getTime()));
            }
            res.setAppPushHistoryOutputBOList(limitOutputBOList);
            return res;
        }

        res.setNextId(-1);
        res.setAppPushHistoryOutputBOList(Collections.emptyList());
        return res;
    }

    @Override
    public Integer queryUnreadMsgNumByUid(ApplicationEnmu appEnmu,Integer uid, Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context,uid,appEnmu);

        AppPushHistoryQO qo = new AppPushHistoryQO();
        qo.setAppId(appEnmu.getCode());
        qo.setUserId(uid);
        qo.setStatus(false);

        Integer count = historyMapper.countPushHistory(qo);
        return  count;
    }

    @Override
    public Integer updateStatusReadByUid(ApplicationEnmu appEnmu,Integer uid, Context context) {
        NotificationExceptionUtils.verifyObjectIsNull(context , uid,appEnmu);
        return historyMapper.updateStatusReadByUid(uid,appEnmu.getCode());
    }

    @Override
    public void batchInsertMsgHistory(ApplicationEnmu appEnum, List<Integer> userIdList, String title, String text, Boolean recordHistory, Context context) {
        try {
            Integer executeNum = 1000;//单次批量处理的数据量
            if(userIdList.size() > executeNum){
                //分批
                for(int i = 0;i<userIdList.size();i += executeNum){
                    List<Integer> uidList = new ArrayList();
                    Integer limit = i+executeNum;
                    uidList = userIdList.subList(i,limit);
                    List<AppPushHistoryInputBo> insertList = new ArrayList<>();
                    for(Integer uid :uidList){
                        if(uid != null){
                            if(recordHistory){
                                //不管发送是否成功失败，都要在消息中心记录
                                AppPushHistoryInputBo historyInputBo = new AppPushHistoryInputBo();
                                historyInputBo.setAppId(appEnum.getCode());
                                historyInputBo.setUserId(uid);
//                        historyInputBo.setBusinessTypeId(businessTypeId);
                                historyInputBo.setTitle(title);
                                historyInputBo.setText(text);
                                historyInputBo.setStatus(false);
                                insertList.add(historyInputBo);
                            }
                        }
                    }
                    insertBatch(insertList, context);
                }
            }else {
                List<AppPushHistoryInputBo> insertList = new ArrayList<>();
                for(Integer uid :userIdList){
                    if(uid != null){
                        if(recordHistory){
                            //不管发送是否成功失败，都要在消息中心记录
                            AppPushHistoryInputBo historyInputBo = new AppPushHistoryInputBo();
                            historyInputBo.setAppId(appEnum.getCode());
                            historyInputBo.setUserId(uid);
//                        historyInputBo.setBusinessTypeId(businessTypeId);
                            historyInputBo.setTitle(title);
                            historyInputBo.setText(text);
                            historyInputBo.setStatus(false);
                            insertList.add(historyInputBo);
                        }
                    }
                }
                insertBatch(insertList, context);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw e;
        }
    }
}
