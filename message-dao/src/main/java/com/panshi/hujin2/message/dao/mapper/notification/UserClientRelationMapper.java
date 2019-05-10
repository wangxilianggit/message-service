package com.panshi.hujin2.message.dao.mapper.notification;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserClientRelationMapper {
    long countByExample(UserClientRelationExample example);

    int deleteByExample(UserClientRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserClientRelation record);

    int insertSelective(UserClientRelation record);

    List<UserClientRelation> selectByExample(UserClientRelationExample example);

    UserClientRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserClientRelation record, @Param("example") UserClientRelationExample example);

    int updateByExample(@Param("record") UserClientRelation record, @Param("example") UserClientRelationExample example);

    int updateByPrimaryKeySelective(UserClientRelation record);

    int updateByPrimaryKey(UserClientRelation record);

    /**
     * 以下为自定义方法
     **/

    /**
     * @description          根据用户id列表获取clientid列表
     * @param userIdList     用户id列表
     * @Author shenjiankang
     * @Date 2018/6/25 19:01
     */
//    List<String> queryClientIdByUserIdList(@Param("list") List<Integer> userIdList);

    /**
     * @description         根据id更新记录status为失效状态
     * @param list          主键id list
     * @Author shenjiankang
     * @Date 2018/6/25 14:55
     */
    int updateClientIdLose(@Param("list") List<Integer> list, @Param("appId") Integer appId);
}