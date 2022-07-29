package com.meenie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meenie.dao.*;
import com.meenie.domain.Answer;
import com.meenie.domain.Dynamic;
import com.meenie.domain.Question;
import com.meenie.domain.User;
import com.meenie.service.QuestionService;
import com.meenie.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserCenterDao userCenterDao;

    @Override
    public boolean add(Question question) {
        questionDao.insert(question);
        Dynamic dynamic = new Dynamic();
        dynamic.setAnswerId(null);
        dynamic.setType(2);
        dynamic.setUserId(question.getAuthorId());
        dynamic.setQuestionId(question.getId());
        userCenterDao.insert(dynamic);
        return true;
    }

    @Override
    public boolean update(Question question) {
        questionDao.updateById(question);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        questionDao.deleteById(id);
        return true;
    }

    @Override
    public Question getById(Long id) {
        Question question = questionDao.selectById(id);
        Long authorId = question.getAuthorId();
        Long questionId = question.getId();
        question.setAuthor(userDao.selectById(authorId));
        if(answerDao.getAnswersCountByQuestionId(questionId)!=0){
            question.setAnswerCount(answerDao.getAnswersCountByQuestionId(questionId));
        }else {
            question.setAnswerCount(0);
        }
        LambdaQueryWrapper<Answer> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Answer::getQuestionId,questionId);
        List<Answer> answers = answerDao.selectList(lqw);
        System.out.println(answers);
        if (answers==null){
            question.setAnswer(null);
        }else{
            for (Answer answer : answers) {
                User author = userDao.selectById(answer.getAuthorId());
                answer.setAuthor(author);
                answer.setExcerpt(StringUtil.subStringByLength(answer.getContent(), 130));
                answer.setCommentsCount(commentDao.getCommentsCountByAnswerId(answer.getId()));
            }
            question.setAnswers(answers);
        }
        return question;
    }

    @Override
    public List<Question> getAll() {
        List<Question> questions = questionDao.selectList(null);
        for (Question question : questions) {
            Long authorId = question.getAuthorId();
            Long questionId = question.getId();
            question.setAuthor(userDao.selectById(authorId));
//            if (answerDao.getAnswersCountByQuestionId(questionId) != 0) {
//                question.setAnswerCount(answerDao.getAnswersCountByQuestionId(questionId));
//            } else {
//                question.setAnswerCount(0);
//            }
            question.setAnswerCount(answerDao.getAnswersCountByQuestionId(questionId));
            Answer answer = answerDao.getMostAgreeAnswer(questionId);
            System.out.println(answer);
            if (answer == null) {
                question.setAnswer(null);
            } else {
                User author = userDao.selectById(answer.getAuthorId());
                answer.setAuthor(author);
                answer.setExcerpt(StringUtil.subStringByLength(answer.getContent(), 130).replaceAll("<([^>]*)>",""));
                answer.setCommentsCount(commentDao.getCommentsCountByAnswerId(answer.getId()));
                List<Map<String, Object>> agree = answerDao.getAgreeByAnswerIdAndUserId(answer.getId(), 1L);
                Integer agreeState = agree.size()>0?(Integer)agree.get(0).get("val"):0;
                answer.setAgreeState(agreeState);
                answer.setIsLike(answerDao.getLike(answer.getId(), 1L).size() == 1);
                answer.setIsCollect(answerDao.getCollect(answer.getId(), 1L).size() == 1);
                question.setAnswer(answer);
            }
        }
        return questions;
    }
}
