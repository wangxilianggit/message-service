package com.panshi.hujin2.message.dao.mapper.fcm;

import com.panshi.hujin2.message.dao.model.fcm.UserFcmRelationDO;
import com.panshi.hujin2.message.dao.model.fcm.UserFcmRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserFcmRelationMapper {
    long countByExample(UserFcmRelationExample example);

    int deleteByExample(UserFcmRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserFcmRelationDO record);

    int insertSelective(UserFcmRelationDO record);

    List<UserFcmRelationDO> selectByExample(UserFcmRelationExample example);

    UserFcmRelationDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserFcmRelationDO record, @Param("example") UserFcmRelationExample example);

    int updateByExample(@Param("record") UserFcmRelationDO record, @Param("example") UserFcmRelationExample example);

    int updateByPrimaryKeySelective(UserFcmRelationDO record);

    int updateByPrimaryKey(UserFcmRelationDO record);

    int updateClientTokenLose(@Param("list")List<Integer> idList,  @Param("appId")Integer code);
}