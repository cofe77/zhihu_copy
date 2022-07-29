package com.meenie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meenie.dao.EducationDao;
import com.meenie.dao.OccupationDao;
import com.meenie.dao.UserDao;
import com.meenie.domain.Education;
import com.meenie.domain.Occupation;
import com.meenie.domain.User;
import com.meenie.service.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserSettingServiceImpl implements UserSettingService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OccupationDao occupationDao;

    @Autowired
    private EducationDao educationDao;

    @Override
    public void updateUserNick(Map<String, String> userInfo) {

        User user = new User();
        user.setId(Long.parseLong(userInfo.get("id")));
        user.setNick(userInfo.get("nick"));
        userDao.update(user,new LambdaQueryWrapper<User>().eq(User::getId,userInfo.get("id")));
    }

    @Override
    public void updateUserSex(Map<String, String> userInfo) {
        User user = new User();
        user.setId(Long.parseLong(userInfo.get("id")));
        user.setSex(userInfo.get("sex"));
        userDao.update(user,new LambdaQueryWrapper<User>().eq(User::getId,userInfo.get("id")));
    }

    @Override
    public void updateUserSlogan(Map<String, String> userInfo) {
        User user = new User();
        user.setId(Long.parseLong(userInfo.get("id")));
        user.setSlogan(userInfo.get("slogan"));
        userDao.update(user,new LambdaQueryWrapper<User>().eq(User::getId,userInfo.get("id")));
    }

    @Override
    public void updateUserAddress(Map<String, String> userInfo) {
        User user = new User();
        user.setId(Long.parseLong(userInfo.get("id")));
        user.setAddress(userInfo.get("address"));
        userDao.update(user,new LambdaQueryWrapper<User>().eq(User::getId,userInfo.get("id")));
    }

    @Override
    public void updateUserProfession(Map<String, String> userInfo) {
        User user = new User();
        user.setId(Long.parseLong(userInfo.get("id")));
        user.setProfession(userInfo.get("profession"));
        userDao.update(user,new LambdaQueryWrapper<User>().eq(User::getId,userInfo.get("id")));
    }

    @Override
    public void addUserOccupation(Occupation occupation) {
        occupationDao.insert(occupation);
    }

    @Override
    public void deleteUserOccupation(Long id) {
        occupationDao.deleteById(id);
    }

    @Override
    public void addUserEducation(Education education) {
        educationDao.insert(education);
    }

    @Override
    public void deleteUserEducation(Long id) {
        educationDao.deleteById(id);
    }

    @Override
    public void updateUserDesc(Map<String, String> userInfo) {
        User user = new User();
        user.setId(Long.parseLong(userInfo.get("id")));
        user.setUserDesc(userInfo.get("userDesc"));
        userDao.update(user,new LambdaQueryWrapper<User>().eq(User::getId,userInfo.get("id")));
    }
}
