package com.meenie.service;

import com.meenie.domain.Answer;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AnswerService {

    Answer getById();

    Answer getByQuestionIdWithAuthor(Long questionId);

    boolean add(Answer answer);

    boolean delete();

    boolean update();

    boolean updateAnswerMeta(Long answerId,Long userId,Integer changeVal);

    boolean toggleIsCollect(Long answerId, Long userId);

    boolean toggleIsLike(Long answerId, Long userId);
}
