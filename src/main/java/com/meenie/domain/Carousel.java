package com.meenie.domain;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

@Data
public class Carousel {

  @TableId(type = IdType.NONE)
  @JsonSerialize(using = ToStringSerializer.class)
  private Long id;
  private String src;
  private int enable;
  private String title;
  private String href;
  @TableField(jdbcType = JdbcType.NUMERIC)
  private Integer imageIdx;
  @TableField(fill = FieldFill.INSERT)
  private Date updateTime;

}
