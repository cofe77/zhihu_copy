package com.meenie.controller;

import com.meenie.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userCenter")
public class UserCenterController {

    @Autowired
    private UserCenterService userCenterService;

    @GetMapping("/dynamic/{username}")
    public ResResult getDynamicByUserName(@PathVariable("username") String username){
        return new ResResult(ResCode.GET_OK,"获取成功",userCenterService.getUserDynamic(username));
    }
}
