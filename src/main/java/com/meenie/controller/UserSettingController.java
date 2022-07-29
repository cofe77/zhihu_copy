package com.meenie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meenie.dao.OccupationDao;
import com.meenie.dao.UserDao;
import com.meenie.domain.Education;
import com.meenie.domain.Occupation;
import com.meenie.domain.User;
import com.meenie.exception.BusinessException;
import com.meenie.service.OccupationService;
import com.meenie.service.UserService;
import com.meenie.service.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/userSetting")
public class UserSettingController {

    @Autowired
    private UserService userService;

    @Autowired
    private OccupationService occupationService;

    @Autowired
    private UserSettingService userSettingService;

    @PostMapping("/nick")
    public ResResult updateUserNick(@RequestBody Map<String,String> userInfo){
        if(null == userInfo.get("id")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"用户id有误");
        }
        if(null == userInfo.get("nick")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"昵称有误");
        }
        userSettingService.updateUserNick(userInfo);
        return new ResResult(ResCode.UPDATE_OK,"修改成功",null);
    }

    @PostMapping("/sex")
    public ResResult updateUserSex(@RequestBody Map<String,String> userInfo){
        if(null == userInfo.get("id")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"用户id有误");
        }
        if(null == userInfo.get("sex")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"性别有误");
        }
        userSettingService.updateUserSex(userInfo);
        return new ResResult(ResCode.UPDATE_OK,"修改成功",null);
    }


    @PostMapping("/slogan")
    public ResResult updateUserSlogan(@RequestBody Map<String,String> userInfo){
        if(null == userInfo.get("id")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"用户id有误");
        }
        if(null == userInfo.get("slogan")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"请输入用户介绍");
        }
        userSettingService.updateUserSlogan(userInfo);
        return new ResResult(ResCode.UPDATE_OK,"修改成功",null);
    }


    @PostMapping("/address")
    public ResResult updateUserAddress(@RequestBody Map<String,String> userInfo){
        if(null == userInfo.get("id")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"用户id有误");
        }
        if(null == userInfo.get("address")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"地址有误");
        }
        userSettingService.updateUserAddress(userInfo);
        return new ResResult(ResCode.UPDATE_OK,"修改成功",null);
    }

    @PostMapping("/profession")
    public ResResult updateUserProfession(@RequestBody Map<String,String> userInfo){
        if(null == userInfo.get("id")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"用户id有误");
        }
        if(null == userInfo.get("profession")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"行业信息有误");
        }
        userSettingService.updateUserProfession(userInfo);
        return new ResResult(ResCode.UPDATE_OK,"修改成功",null);
    }

    @PostMapping("/occupation")
    public ResResult addUserOccupation(@RequestBody Occupation occupation){
        if(null == occupation.getUserId()){
            throw new BusinessException(ResCode.BUSINESS_ERR,"用户id有误");
        }
        if(null == occupation.getOrganization()){
            throw new BusinessException(ResCode.BUSINESS_ERR,"组织信息有误");
        }
        userSettingService.addUserOccupation(occupation);
        return new ResResult(ResCode.UPDATE_OK,"添加成功",null);
    }


    @DeleteMapping("/occupation/{id}")
    public ResResult deleteUserOccupation(@PathVariable Long id){
        if(null == id){
            throw new BusinessException(ResCode.BUSINESS_ERR,"用户id有误");
        }
        userSettingService.deleteUserOccupation(id);
        return new ResResult(ResCode.UPDATE_OK,"删除成功",null);
    }

    @PostMapping("/education")
    public ResResult addUserEducation(@RequestBody Education education){
        if(null == education.getUserId()){
            throw new BusinessException(ResCode.BUSINESS_ERR,"用户id有误");
        }
        if(null == education.getSchool()){
            throw new BusinessException(ResCode.BUSINESS_ERR,"组织信息有误");
        }
        userSettingService.addUserEducation(education);
        return new ResResult(ResCode.UPDATE_OK,"添加成功",null);
    }

    @DeleteMapping("/education/{id}")
    public ResResult deleteUserEducation(@PathVariable Long id){
        if(null == id){
            throw new BusinessException(ResCode.BUSINESS_ERR,"用户id有误");
        }
        userSettingService.deleteUserEducation(id);
        return new ResResult(ResCode.UPDATE_OK,"删除成功",null);
    }


    @PostMapping("/desc")
    public ResResult updateUserDesc(@RequestBody Map<String,String> userInfo){
        if(null == userInfo.get("id")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"用户id有误");
        }
        if(null == userInfo.get("userDesc")){
            throw new BusinessException(ResCode.BUSINESS_ERR,"请输入用户介绍");
        }
        userSettingService.updateUserDesc(userInfo);
        return new ResResult(ResCode.UPDATE_OK,"修改成功",null);
    }
}
