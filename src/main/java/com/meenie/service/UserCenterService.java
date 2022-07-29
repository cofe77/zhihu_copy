package com.meenie.service;

import com.meenie.domain.Dynamic;

import java.util.List;
import java.util.Map;

public interface UserCenterService {
    void addUserDynamic(Dynamic dynamic);
    List<Map<String,Object>> getUserDynamic(String username);
}
