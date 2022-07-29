package com.meenie.service;

import com.meenie.domain.Education;
import com.meenie.domain.Occupation;

import java.util.Map;

public interface UserSettingService {
    void updateUserNick(Map<String,String> userInfo);
    void updateUserSex(Map<String,String> userInfo);
    void updateUserSlogan(Map<String,String> userInfo);
    void updateUserAddress(Map<String,String> userInfo);
    void updateUserProfession(Map<String,String> userInfo);
    void addUserOccupation(Occupation occupation);
    void deleteUserOccupation(Long id);
    void addUserEducation(Education education);
    void deleteUserEducation(Long id);
    void updateUserDesc(Map<String,String> userInfo);
}
