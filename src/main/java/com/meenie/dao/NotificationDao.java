package com.meenie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meenie.domain.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationDao extends BaseMapper<Notification> {

    @Select("select user_id from notification_o2m_actor where notification_id = ${notificationId} and user_id != ${currentUserId}")
    List<Long> getActorsByNotificationId(@Param("notificationId") Long notificationId,@Param("currentUserId") Long currentUserId);

    @Insert("insert into notification_o2m_actor (notification_id,user_id) values (${notificationId},${userId})")
    void addActor(@Param("notificationId") Long notificationId,@Param("userId") Long userId);

    @Select("select * from notification where question_id = ${questionId} and type = '1'")
    List<Notification> getDefaultNotifications(@Param("questionId") Long questionId);

    @Select("select * from notification where user_id = ${userId} and type = '5'")
    List<Notification> getFollowNotifications(@Param("questionId") Long userId);

    @Select("select * from notification where user_id = ${userId} and type = '5'")
    List<Notification> getVoteNotifications(@Param("questionId") Long userId);
}
