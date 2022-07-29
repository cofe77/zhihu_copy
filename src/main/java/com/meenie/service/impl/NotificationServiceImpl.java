package com.meenie.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meenie.common.BaseUserInfo;
import com.meenie.dao.*;
import com.meenie.domain.Notification;
import com.meenie.domain.Question;
import com.meenie.domain.User;
import com.meenie.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private AnswerDao answerDao;
    @Autowired
    private CommentDao commentDao;



    // 1:answer\21:agreeAnswer\22:agreeComment\3:likeAnswer\4:collectAnswer\5:follow
    public List<Notification> getDefaultNotification() {
        Long currentUserId = Long.parseLong(BaseUserInfo.get("id"));
        List<Long> questionIds = questionDao.getQuestionIdsByAuthorId(currentUserId);
        List<Notification> notifications = notificationDao.selectList(new LambdaQueryWrapper<Notification>().in(Notification::getQuestionId,questionIds));
        LambdaQueryWrapper<User> userLqw = new LambdaQueryWrapper<>();
        for (Notification notification : notifications) {
            List<Long> actorsId = notificationDao.getActorsByNotificationId(notification.getId(),currentUserId);
            userLqw.in(User::getId, actorsId);
            Map<String, Object> content = new HashMap<>();
            content.put("actors", userDao.selectList(userLqw));
            content.put("target", questionDao.selectById(notification.getQuestionId()));
            notification.setContent(content);
            notification.setTarget(questionDao.selectById(notification.getQuestionId()));
        }
        return notifications;
    }

    // 1:answer\21:agreeAnswer\22:agreeComment\3:likeAnswer\4:collectAnswer\5:follow

    @Override
    public List<Notification> getFollowNotification() {
        Long currentUserId = Long.parseLong(BaseUserInfo.get("id"));
        List<Notification> notifications = notificationDao.getFollowNotifications(currentUserId);
        LambdaQueryWrapper<User> userLqw = new LambdaQueryWrapper<>();
        for (Notification notification : notifications) {
            Long actorId = notification.getUserId();
            userLqw.eq(User::getId, actorId);
            Map<String, Object> content = new HashMap<>();
            content.put("actors", userDao.selectOne(userLqw));
            content.put("target", null);
            notification.setContent(content);
            notification.setTarget(null);
        }
        return notifications;
    }
    // 21:agreeAnswer\22:agreeComment\3:likeAnswer
    @Override
    public List<Notification> getVoteNotification() {
        Long currentUserId = Long.parseLong(BaseUserInfo.get("id"));
        LambdaQueryWrapper<Notification> lqw = new LambdaQueryWrapper<>();
        List<Notification> notifications = notificationDao.getVoteNotifications(Long.parseLong(BaseUserInfo.get("id")));
        LambdaQueryWrapper<User> userLqw = new LambdaQueryWrapper<>();
        List<String> typeList = new ArrayList<>();
        typeList.add("21");
        typeList.add("22");
        typeList.add("3");
        lqw.in(Notification::getType,typeList);
        for (Notification notification : notifications) {
            List<Long> actorsId = notificationDao.getActorsByNotificationId(notification.getId(),currentUserId);
            userLqw.in(User::getId, actorsId);
            Map<String, Object> content = new HashMap<>();
            content.put("actors", userDao.selectList(userLqw));
            content.put("target", answerDao.selectById(notification.getAnswerId()));
            notification.setContent(content);
            notification.setTarget(questionDao.selectById(notification.getQuestionId()));
        }
        return notificationDao.selectList(lqw);
    }
}
