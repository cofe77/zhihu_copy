package com.meenie.service;

import com.meenie.domain.Question;

import java.util.List;

public interface QuestionService {
    boolean add(Question question);

    boolean update(Question question);

    boolean delete(Long id);

    Question getById(Long id);

    List<Question> getAll();
}
