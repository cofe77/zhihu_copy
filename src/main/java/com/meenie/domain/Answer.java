package com.meenie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class Answer {
    @TableId(type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;
    private String content;
    @TableField(exist = false)
    private String excerpt;
    @TableField(exist = false)
    private Integer agreeState;
    @TableField(exist = false)
    private Boolean isLike;
    @TableField(exist = false)
    private Boolean isCollect;
    private Integer agreeCount;
    @TableField(exist = false)
    private Integer commentsCount;
    @TableField(exist = false)
    private User author;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
