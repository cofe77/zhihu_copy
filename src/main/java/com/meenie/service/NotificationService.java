package com.meenie.service;

import com.meenie.domain.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> getDefaultNotification();

    List<Notification> getFollowNotification();

    List<Notification> getVoteNotification();
}
