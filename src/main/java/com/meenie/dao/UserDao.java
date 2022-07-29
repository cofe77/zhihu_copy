package com.meenie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meenie.domain.Education;
import com.meenie.domain.Occupation;
import com.meenie.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select * from follow where user_id = ${targetUserId} and follower_id = ${userId}")
    List<Map<String, Object>> getFollow(@Param("userId") Long userId, @Param("targetUserId") Long targetUserId);

    @Delete("delete from follow where user_id = ${targetUserId} and follower_id = ${userId}")
    void cancelFollow(@Param("userId") Long userId, @Param("targetUserId") Long targetUserId);

    @Insert("insert into follow (user_id, follower_id) values (${targetUserId},${userId})")
    void addFollow(@Param("userId") Long userId, @Param("targetUserId") Long targetUserId);
}
