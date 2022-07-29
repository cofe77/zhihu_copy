package com.meenie.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "user_m2m_dynamic")
public class Dynamic {

  @TableId(type = IdType.NONE)
  @JsonSerialize(using = ToStringSerializer.class)
  private Long id;
  @JsonSerialize(using = ToStringSerializer.class)
  private Long userId;
  private int type;
  @JsonSerialize(using = ToStringSerializer.class)
  private Long questionId;
  @JsonSerialize(using = ToStringSerializer.class)
  private Long answerId;
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

}
