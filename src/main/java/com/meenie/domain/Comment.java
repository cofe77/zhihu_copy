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
public class Comment {
    @TableId(type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String content;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long answerId;
    private Integer likeCount;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long repliedUserId;
    private Integer isFeature;
    @TableField(exist = false)
    private User author;
    @TableField(exist = false)
    private User repliedUser;
    @TableField(exist = false)
    private List<Comment> moreReply;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
