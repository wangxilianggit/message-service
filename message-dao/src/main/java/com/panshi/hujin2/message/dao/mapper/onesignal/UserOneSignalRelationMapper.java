package com.panshi.hujin2.message.dao.mapper.onesignal;

import com.panshi.hujin2.message.dao.model.onesignal.UserOneSignalRelationDO;
import com.panshi.hujin2.message.dao.model.onesignal.UserOneSignalRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserOneSignalRelationMapper {
    long countByExample(UserOneSignalRelationExample example);

    int deleteByExample(UserOneSignalRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserOneSignalRelationDO record);

    int insertSelective(UserOneSignalRelationDO record);

    List<UserOneSignalRelationDO> selectByExample(UserOneSignalRelationExample example);

    UserOneSignalRelationDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserOneSignalRelationDO record, @Param("example") UserOneSignalRelationExample example);

    int updateByExample(@Param("record") UserOneSignalRelationDO record, @Param("example") UserOneSignalRelationExample example);

    int updateByPrimaryKeySelective(UserOneSignalRelationDO record);

    int updateByPrimaryKey(UserOneSignalRelationDO record);


    /**
     *  自定义方法
     */
    int updateClientTokenLose(@Param("list") List<Integer> idList, @Param("appId") Integer code);

    /**
     *@Description:     根据appid和userid获取最后一条记录
     *@Param:  * @param appId
     * @param userId
     *@Author: shenJianKang
     *@date: 2020/1/9 16:40
     */
    UserOneSignalRelationDO queryLastRecordByUserId(@Param("appId") Integer appId, @Param("userId") Integer userId);
}