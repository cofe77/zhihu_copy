package com.meenie.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class Notification {

  @JsonSerialize(using = ToStringSerializer.class)
  private Long id;
  private String type;
  @JsonSerialize(using = ToStringSerializer.class)
  private Long userId;
  @JsonSerialize(using = ToStringSerializer.class)
  private Long questionId;
  @JsonSerialize(using = ToStringSerializer.class)
  private Long answerId;
  @JsonSerialize(using = ToStringSerializer.class)
  private Long commentId;
  @TableField(exist = false)
  private Object content;
  @TableField(exist = false)
  private Object target;
  private Date createTime;
  private Integer deleted;

}
