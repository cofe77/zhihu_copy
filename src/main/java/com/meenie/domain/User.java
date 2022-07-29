package com.meenie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String username;
    private String nick;
    private Integer age;
    private String sex;
    private String userDesc;
    private String slogan;
    private String avatar;
    @TableField(fill = FieldFill.INSERT)
    private Date joinTime;
    private String profession;
    private String address;
    @TableField(select = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pwd;
    @TableField(exist = false)
    private boolean follow;
    @TableField(exist = false)
    private List<Occupation> occupations;
    @TableField(exist = false)
    private List<Education> educations;
    private Integer deleted;
}
