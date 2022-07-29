package com.meenie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Question {
    @TableId(type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;
    private String title;
    private String mainPic;
    private String tag;
    private String category;
    private String content;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(exist = false)
    private User author;
    @TableField(exist = false)
    private Integer answerCount;
    @TableField(exist = false)
    private Answer answer;
    @TableField(exist = false)
    private List<Answer> answers;
    private Integer deleted;
}
