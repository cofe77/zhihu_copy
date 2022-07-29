package com.meenie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meenie.domain.Answer;
import lombok.val;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AnswerDao extends BaseMapper<Answer> {
    @Select("select * from answer where question_id = ${questionId} order by agree_count desc limit 1")
    Answer getMostAgreeAnswer(Long questionId);


    @Select("select count(*) from answer where question_id = ${questionId}")
    Integer getAnswersCountByQuestionId(Long questionId);

    @Update("update answer set agree_count = agree_count + ${changeVal} where id = ${answerId}")
    void updateAnswerMeta(@Param("answerId") Long answerId,@Param("changeVal") Integer changeVal);

    @Insert("insert into user_m2m_agree (user_id, answer_id, val,update_time) value (${userId},${answerId},${val},now())")
    void addAgree(@Param("answerId") Long answerId,@Param("userId") Long userId,@Param("val") Integer val);

    @Select("select * from user_m2m_agree where answer_id = ${answerId} and user_id = ${userId}")
    List<Map<String,Object>> getAgreeByAnswerIdAndUserId(@Param("answerId") Long answerId, @Param("userId") Long userId);

    @Update("update user_m2m_agree set val = ${val} where answer_id = ${answerId} and user_id = ${userId}")
    void updateAgreeByAnswerId(@Param("answerId") Long answerId, @Param("userId") Long userId,@Param("val") Integer val);

    @Select("select * from user_m2m_collected where answer_id = ${answerId} and user_id = ${userId}")
    List<Map<String,Object>> getCollect(@Param("answerId") Long answerId, @Param("userId") Long userId);

    @Delete("delete from user_m2m_collected where answer_id = ${answerId} and user_id = ${userId}")
    void deleteCollect(@Param("answerId") Long answerId, @Param("userId") Long userId);

    @Insert("insert into user_m2m_collected (answer_id,user_id,update_time) value (${answerId},${userId},now())")
    void addCollect(@Param("answerId") Long answerId, @Param("userId") Long userId);

    @Select("select * from user_m2m_like where answer_id = ${answerId} and user_id = ${userId}")
    List<Map<String,Object>> getLike(@Param("answerId") Long answerId, @Param("userId") Long userId);

    @Delete("delete from user_m2m_like where answer_id = ${answerId} and user_id = ${userId}")
    void deleteLike(@Param("answerId") Long answerId, @Param("userId") Long userId);

    @Insert("insert into user_m2m_like (answer_id,user_id,update_time) value (${answerId},${userId},now())")
    void addLike(@Param("answerId") Long answerId, @Param("userId") Long userId);
}
