package com.meenie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meenie.domain.Answer;
import com.meenie.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentDao extends BaseMapper<Comment> {

    @Select("select * from comment where answer_id = ${answerId}")
    List<Comment> getCommentsByAnswerId(Long answerId);



    @Select("select * from comment where answer_id = ${answerId} and comment_id = 0")
    List<Comment> getOriginCommentsByAnswerIdAndType(@Param("answerId") Long answerId);


    @Select("select * from comment where answer_id = ${answerId} and comment_id = ${commentId}")
    List<Comment> getRepliedCommentsByAnswerIdAndType(@Param("answerId") Long answerId,@Param("commentId") Long commentId);


    @Select("select count(id) from comment where answer_id = ${answerId} and comment_id = 0")
    Integer getCommentsCountByAnswerId(Long answerId);
}
