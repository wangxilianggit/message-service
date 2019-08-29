package com.panshi.hujin2.message.service.notification.fcm.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.dao.mapper.fcm.UserFcmRelationMapper;
import com.panshi.hujin2.message.dao.model.fcm.UserFcmRelationDO;
import com.panshi.hujin2.message.dao.model.fcm.UserFcmRelationExample;
import com.panshi.hujin2.message.service.notification.fcm.FCMPushService;
import com.panshi.hujin2.message.service.notification.getui.impl.NotificationPushServiceImpl;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * create by shenjiankang on 2018/7/7 11:24
 */
@Service
public class FCMPushServiceImpl implements FCMPushService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationPushServiceImpl.class);
 @Autowired
    UserFcmRelationMapper userFcmRelationMapper;
    @Override
    public int insertUserClientRelation(ApplicationEnmu appEnmu, Integer userId, String clientToken, ClientType clientType, Context context) {
        try {
            LOGGER.debug("绑定 用户id[{}]    clientToken[{{}}]",userId,clientToken);

            NotificationExceptionUtils.verifyObjectIsNull(context,userId,clientType,appEnmu);
            //clinetid如果用户拒绝了消息通知，clientToken为空
            NotificationExceptionUtils.verifyStringIsBlank(context,clientToken);

            if(!clientType.equals(ClientType.ANDROID) && !clientType.equals(ClientType.IOS)){
                NotificationExceptionUtils.throwExceptionParamInvalid(context);
            }

            // 判断clienttoken是否有和别userid绑定，存在的话解绑之前的记录
            UserFcmRelationExample queryTokenExample =new UserFcmRelationExample();
                       //UserClientRelationExample queryCidExample = new UserClientRelationExample();
            UserFcmRelationExample.Criteria queryCidCriteria = queryTokenExample.createCriteria();

            //todo 重新构建实体类
            queryCidCriteria.andAppIdEqualTo(appEnmu.getCode());
            queryCidCriteria.andUserIdNotEqualTo(userId);//非当前绑定userid
            queryCidCriteria.andClientTokenEqualTo(clientToken);
            queryCidCriteria.andStatusEqualTo(1);
            List<UserFcmRelationDO> clienttokenExistList = userFcmRelationMapper.selectByExample(queryTokenExample);
            if(clienttokenExistList!=null && clienttokenExistList.size()>0){
                //批量更新解绑
                List<Integer> idList = new ArrayList<>();
                for(UserFcmRelationDO  clienttokenExistDO: clienttokenExistList){
                    idList.add(clienttokenExistDO.getId());
                }
                LOGGER.debug("--------用户[{}] clienttoken历史相同存在 [{}] 个 pids:[{}]",userId,idList.size(),idList.toString());
                //更新之前存在，且是激活状态的clienttoken为失效
                if(idList.size()>0){
                    int resCount = userFcmRelationMapper.updateClientTokenLose(idList,appEnmu.getCode());
                    LOGGER.debug("--------更新 [{}] 条记录为失效",resCount);
                }
            }

            //查询用户id是否已经存在
            UserFcmRelationExample userClientRelationExample = new UserFcmRelationExample();
            UserFcmRelationExample.Criteria criteria = userClientRelationExample.createCriteria();
            criteria.andAppIdEqualTo(appEnmu.getCode());
            criteria.andUserIdEqualTo(userId);
            List<UserFcmRelationDO> userClientRelationDOList = userFcmRelationMapper.selectByExample(userClientRelationExample);

            if(userClientRelationDOList!=null && userClientRelationDOList.size()>0){
                //有记录，更新状态为有效
//                    UserClientRelationDO resDo = userClientRelationDOList.get(0);
//                    String dbClientId = "";
//                    if(resDo!=null){
//                        dbClientId = resDo.getClientId();
//                    }
//                    if(dbClientId!=null && dbClientId.equals(clientToken)){
//                        //clienttoken相同，不需要更新
//                    }

                UserFcmRelationDO updateDo = new UserFcmRelationDO();
                updateDo.setId(userClientRelationDOList.get(0).getId());
                updateDo.setClientToken(clientToken);
                updateDo.setPhoneType(clientType.getType());
                updateDo.setStatus(1);
                LOGGER.debug("用户[{}]clientToken激活成功(update)",userId);
                return userFcmRelationMapper.updateByPrimaryKeySelective(updateDo);
            }else{
                //没有绑定则新增一条记录
                UserFcmRelationDO fcmRelationDO = new UserFcmRelationDO();
                fcmRelationDO.setAppId(appEnmu.getCode());
                fcmRelationDO.setUserId(userId);
                fcmRelationDO.setClientToken(clientToken);
                fcmRelationDO.setPhoneType(clientType.getType());
                fcmRelationDO.setStatus(1);
                LOGGER.debug("用户[{}]clientToken激活成功(insert)",userId);
                return userFcmRelationMapper.insertSelective(fcmRelationDO);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int unbindClientRelation(ApplicationEnmu appEnmu, Integer userId, String clientToken, ClientType clientType, Context context) {
        try {
            LOGGER.debug("解绑 用户id[{}]    clienttoken[{{}}]",userId,clientToken);
            //校验userid 和手机系统类型
            NotificationExceptionUtils.verifyObjectIsNull(context,userId,clientType,appEnmu);
            //校验clientId
            NotificationExceptionUtils.verifyStringIsBlank(context,clientToken);
            //校验手机操作系统类型
            if(!clientType.equals(ClientType.ANDROID) && !clientType.equals(ClientType.IOS)){
                //错误的手机系统类型
                NotificationExceptionUtils.throwExceptionParamInvalid(context);
            }
            //查询该信息是否存在
            UserFcmRelationExample example = new UserFcmRelationExample();
            UserFcmRelationExample.Criteria criteria = example.createCriteria();
            criteria.andAppIdEqualTo(appEnmu.getCode());
            criteria.andUserIdEqualTo(userId);
            criteria.andClientTokenEqualTo(clientToken);
            criteria.andPhoneTypeEqualTo(clientType.getType());
            List<UserFcmRelationDO> relationDOList = userFcmRelationMapper.selectByExample(example);
            if(relationDOList!=null && relationDOList.size()>0){
                UserFcmRelationDO updateDo = new UserFcmRelationDO();
                updateDo.setStatus(0);
                updateDo.setId(relationDOList.get(0).getId());
                LOGGER.debug("用户[{}]退出登录，解绑的id号[{}]",userId,relationDOList.get(0).getId());
                return userFcmRelationMapper.updateByPrimaryKeySelective(updateDo);
            }
            return 0;
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }
}
