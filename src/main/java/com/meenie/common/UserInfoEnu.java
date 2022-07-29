package com.meenie.common;

import lombok.Getter;

@Getter
public enum UserInfoEnu {
    username("username"),
    pwd("pwd");
    private final String code;

    UserInfoEnu(String code){
        this.code = code;
    }

}
