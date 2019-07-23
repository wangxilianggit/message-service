package com.panshi.hujin2.message.dao.mapper.message;

import com.panshi.hujin2.message.dao.model.KmiTokenLogDO;
import com.panshi.hujin2.message.dao.model.KmiTokenLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KmiTokenLogMapper {
    long countByExample(KmiTokenLogExample example);

    int deleteByExample(KmiTokenLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(KmiTokenLogDO record);

    int insertSelective(KmiTokenLogDO record);

    List<KmiTokenLogDO> selectByExample(KmiTokenLogExample example);

    KmiTokenLogDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") KmiTokenLogDO record, @Param("example") KmiTokenLogExample example);

    int updateByExample(@Param("record") KmiTokenLogDO record, @Param("example") KmiTokenLogExample example);

    int updateByPrimaryKeySelective(KmiTokenLogDO record);

    int updateByPrimaryKey(KmiTokenLogDO record);
}