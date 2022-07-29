package com.meenie.controller;


import com.meenie.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/answer")
    public ResResult getDefaultNotification(){
        return new ResResult(ResCode.GET_OK,"获取成功",notificationService.getDefaultNotification());
    }
    @GetMapping("/follow")
    public ResResult getFollowNotification(){
        return new ResResult(ResCode.GET_OK,"获取成功",notificationService.getFollowNotification());
    }
    // 获取  21:agreeAnswer\22:agreeComment\3:likeAnswer
    @GetMapping("/vote")
    public ResResult getAgreeNotification(){
        return new ResResult(ResCode.GET_OK,"获取成功",notificationService.getVoteNotification());
    }
}
