package com.meenie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meenie.domain.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionDao extends BaseMapper<Question> {

    @Select("select id from question where author_id = ${authorId}")
    List<Long> getQuestionIdsByAuthorId(Long authorId);
}
