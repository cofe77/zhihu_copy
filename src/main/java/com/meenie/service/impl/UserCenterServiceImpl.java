package com.meenie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meenie.dao.*;
import com.meenie.domain.Answer;
import com.meenie.domain.Dynamic;
import com.meenie.domain.Question;
import com.meenie.domain.User;
import com.meenie.service.QuestionService;
import com.meenie.service.UserCenterService;
import com.meenie.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserCenterServiceImpl implements UserCenterService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserCenterDao userCenterDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Override
    public void addUserDynamic(Dynamic dynamic) {
        userCenterDao.insert(dynamic);
    }

    @Override
    public List<Map<String,Object>> getUserDynamic(String username) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername,username);
        User user = userDao.selectOne(lqw);
        List<Map<String,String>> userDynamics = userCenterDao.getUserDynamicByUserId(user.getId());
        List<Map<String,Object>> dynamicList = new ArrayList<>(userDynamics.size());
        for (int i = 0; i < userDynamics.size(); i++) {
            Map<String, String> userDynamic = userDynamics.get(i);
            Map<String, Object> dynamic = new HashMap<>();
            Question question = questionDao.selectById(userDynamic.get("question_id"));
            if(userDynamic.get("type").equals("2")){
                question.setAnswer(null);
            }else{
                Answer answer = answerDao.selectById(userDynamic.get("answer_id"));
                User author = userDao.selectById(answer.getAuthorId());
                answer.setAuthor(author);
                answer.setExcerpt(StringUtil.subStringByLength(answer.getContent(), 130));
                answer.setCommentsCount(commentDao.getCommentsCountByAnswerId(answer.getId()));
                question.setAnswer(answer);
            }
            dynamic.put("type",userDynamic.get("type"));
            dynamic.put("createTime",userDynamic.get("create_time"));
            dynamic.put("question",question);
            dynamicList.add(i,dynamic);
        }
        return dynamicList;
    }
}
