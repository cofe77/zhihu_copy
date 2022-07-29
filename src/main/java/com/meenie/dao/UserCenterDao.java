package com.meenie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meenie.domain.Dynamic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserCenterDao extends BaseMapper<Dynamic> {

    @Select("select * from user_m2m_dynamic where user_id = ${userId}")
    List<Map<String,String>> getUserDynamicByUserId(Long userId);
}
