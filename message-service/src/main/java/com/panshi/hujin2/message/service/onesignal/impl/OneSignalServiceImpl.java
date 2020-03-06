package com.panshi.hujin2.message.service.onesignal.impl;

import com.panshi.hujin2.base.common.enmu.ApplicationEnmu;
import com.panshi.hujin2.base.common.enmu.ClientType;
import com.panshi.hujin2.base.service.Context;
import com.panshi.hujin2.message.dao.mapper.onesignal.UserOneSignalRelationMapper;
import com.panshi.hujin2.message.dao.model.onesignal.UserOneSignalRelationDO;
import com.panshi.hujin2.message.dao.model.onesignal.UserOneSignalRelationExample;
import com.panshi.hujin2.message.service.notification.utils.NotificationExceptionUtils;
import com.panshi.hujin2.message.service.onesignal.IOneSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenJianKang
 * @date 2020/1/9 16:32
 */
@Service
public class OneSignalServiceImpl implements IOneSignalService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserOneSignalRelationMapper oneSignalRelationMapper;


    @Override
    public int insertUserClientRelation(ApplicationEnmu appEnmu, Integer userId, String clientId, ClientType clientType, Context context) {
        try {
            LOGGER.debug("绑定 用户id[{}]; paylerId[{}]",userId,clientId);

            NotificationExceptionUtils.verifyObjectIsNull(context,userId,clientType,appEnmu);
            //clinetid如果用户拒绝了消息通知，clientToken为空
            NotificationExceptionUtils.verifyStringIsBlank(context,clientId);

            if(!clientType.equals(ClientType.ANDROID) && !clientType.equals(ClientType.IOS)){
                NotificationExceptionUtils.throwExceptionParamInvalid(context);
            }

            // 判断clienttoken是否有和别userid绑定，存在的话解绑之前的记录
            UserOneSignalRelationExample queryTokenExample =new UserOneSignalRelationExample();
            UserOneSignalRelationExample.Criteria queryCidCriteria = queryTokenExample.createCriteria();

            queryCidCriteria.andAppIdEqualTo(appEnmu.getCode());
            queryCidCriteria.andUserIdNotEqualTo(userId);//非当前绑定userid
            queryCidCriteria.andClientIdEqualTo(clientId);
            queryCidCriteria.andStatusEqualTo(1);
            List<UserOneSignalRelationDO> clienttokenExistList = oneSignalRelationMapper.selectByExample(queryTokenExample);
            if(clienttokenExistList!=null && clienttokenExistList.size()>0){
                //批量更新解绑
                List<Integer> idList = new ArrayList<>();
                for(UserOneSignalRelationDO  clienttokenExistDO: clienttokenExistList){
                    idList.add(clienttokenExistDO.getId());
                }
                LOGGER.debug("--------用户[{}] clienttoken历史相同存在 [{}] 个 pids:[{}]",userId,idList.size(),idList.toString());
                //更新之前存在，且是激活状态的clienttoken为失效
                if(idList.size()>0){
                    int resCount = oneSignalRelationMapper.updateClientTokenLose(idList,appEnmu.getCode());
                    LOGGER.debug("--------更新 [{}] 条记录为失效",resCount);
                }
            }

            //查询用户id是否已经存在
            UserOneSignalRelationExample userClientRelationExample = new UserOneSignalRelationExample();
            UserOneSignalRelationExample.Criteria criteria = userClientRelationExample.createCriteria();
            criteria.andAppIdEqualTo(appEnmu.getCode());
            criteria.andUserIdEqualTo(userId);
            List<UserOneSignalRelationDO> userClientRelationDOList = oneSignalRelationMapper.selectByExample(userClientRelationExample);

            if(userClientRelationDOList!=null && userClientRelationDOList.size()>0){
                //有记录，更新状态为有效
                UserOneSignalRelationDO updateDo = new UserOneSignalRelationDO();
                updateDo.setId(userClientRelationDOList.get(0).getId());
                updateDo.setClientId(clientId);
                updateDo.setPhoneType(clientType.getType());
                updateDo.setStatus(1);
                LOGGER.debug("用户[{}] oneSignal clientId激活成功(update)",userId);
                return oneSignalRelationMapper.updateByPrimaryKeySelective(updateDo);
            }else{
                //没有绑定则新增一条记录
                UserOneSignalRelationDO oneSignalRelationDO = new UserOneSignalRelationDO();
                oneSignalRelationDO.setAppId(appEnmu.getCode());
                oneSignalRelationDO.setUserId(userId);
                oneSignalRelationDO.setClientId(clientId);
                oneSignalRelationDO.setPhoneType(clientType.getType());
                oneSignalRelationDO.setStatus(1);
                LOGGER.debug("用户[{}] oneSignal clientId激活成功(insert)",userId);
                return oneSignalRelationMapper.insertSelective(oneSignalRelationDO);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int unbindClientRelation(ApplicationEnmu appEnmu, Integer userId, String clientId, ClientType clientType, Context context) {
        try {
            LOGGER.debug("解绑 用户id[{}]    clienttoken[{{}}]",userId,clientId);
            //校验userid 和手机系统类型
            NotificationExceptionUtils.verifyObjectIsNull(context,userId,clientType,appEnmu);
            //校验clientId
            NotificationExceptionUtils.verifyStringIsBlank(context,clientId);
            //校验手机操作系统类型
            if(!clientType.equals(ClientType.ANDROID) && !clientType.equals(ClientType.IOS)){
                //错误的手机系统类型
                NotificationExceptionUtils.throwExceptionParamInvalid(context);
            }
            //查询该信息是否存在
            UserOneSignalRelationExample example = new UserOneSignalRelationExample();
            UserOneSignalRelationExample.Criteria criteria = example.createCriteria();
            criteria.andAppIdEqualTo(appEnmu.getCode());
            criteria.andUserIdEqualTo(userId);
            criteria.andClientIdEqualTo(clientId);
            criteria.andPhoneTypeEqualTo(clientType.getType());
            List<UserOneSignalRelationDO> relationDOList = oneSignalRelationMapper.selectByExample(example);
            if(relationDOList!=null && relationDOList.size()>0){
                UserOneSignalRelationDO updateDo = new UserOneSignalRelationDO();
                updateDo.setStatus(0);
                updateDo.setId(relationDOList.get(0).getId());
                LOGGER.debug("用户[{}]退出登录，oneSignal 解绑的id号[{}]",userId,relationDOList.get(0).getId());
                return oneSignalRelationMapper.updateByPrimaryKeySelective(updateDo);
            }
            return 0;
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }
}
