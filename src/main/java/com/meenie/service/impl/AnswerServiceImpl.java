package com.meenie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meenie.common.BaseUserInfo;
import com.meenie.dao.*;
import com.meenie.domain.Answer;
import com.meenie.domain.Dynamic;
import com.meenie.domain.Notification;
import com.meenie.domain.User;
import com.meenie.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserCenterDao userCenterDao;

    @Autowired
    private NotificationDao notificationDao;

    @Override
    public Answer getById() {
        return null;
    }

    public Answer getByQuestionIdWithAuthor(Long questionId) {
        Answer answer = answerDao.selectById(questionId);
        User author = userDao.selectById(answer.getAuthorId());
        answer.setAuthor(author);
        return answer;
    }

    @Override
    public boolean add(Answer answer) {
        Long answerId = answer.getId();
        Long questionId = answer.getQuestionId();
        Long authorId = answer.getAuthorId();
        answerDao.insert(answer);
        List<Dynamic> dynamics = userCenterDao.selectList(new LambdaQueryWrapper<Dynamic>().eq(Dynamic::getQuestionId, questionId).eq(Dynamic::getUserId,answerId));
        if(dynamics.size()!=1){//说明发起add的用户没有回答过这个问题
            Dynamic dynamic = new Dynamic();
            dynamic.setAnswerId(answerId);
            dynamic.setType(1);
            dynamic.setUserId(authorId);
            dynamic.setQuestionId(questionId);
            userCenterDao.insert(dynamic);
            // 通过questionId在notification表中查询最新的通知
            Notification notification = notificationDao.selectOne(
                    new LambdaQueryWrapper<Notification>()
                    .eq(Notification::getQuestionId,questionId)
                    .eq(Notification::getType,"1")
                    .orderByDesc(Notification::getCreateTime));
            // 通过questionId在dynamic表中查询所有回答过问题的user的count；
            int answerCount = userCenterDao.selectList(new LambdaQueryWrapper<Dynamic>().eq(Dynamic::getQuestionId,questionId)).size();
            if(notification != null){// 如果有
                // 通过questionId在dynamic表中查询所有回答过问题的user的count；
                // 每5个人生成一条新通知
                if(answerCount%5==1){// 如果count对5取余为1，新增一条notification并新增一条actor；
                    Notification newNotification = new Notification();
                    newNotification.setType("1");
                    newNotification.setQuestionId(questionId);
                    notificationDao.insert(newNotification);
                    notificationDao.addActor(newNotification.getId(),authorId);
                }else{// 根据最新notificationId追加一条actor
                    notificationDao.addActor(notification.getId(),authorId);
                }
            }else{// 如果没有，说明count等于0，新增一条notification并新增一条actor；
                Notification newNotification = new Notification();
                newNotification.setType("1");
                newNotification.setQuestionId(questionId);
                notificationDao.insert(newNotification);
                notificationDao.addActor(newNotification.getId(),authorId);
            }
        }
        return true;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean updateAnswerMeta(Long answerId,Long userId,Integer actionVal) {
        try {
            List<Map<String,Object>> agree = answerDao.getAgreeByAnswerIdAndUserId(answerId,userId);
            if(agree.size()==1){//如果之前点过赞
                Integer agreeState = (Integer) agree.get(0).get("val");//获取目前的赞状态：1（agree）；0（nothing）；-1（disagree）
                int val;//最终的状态
                int changeVal;//要计算的状态偏移量
                if(agreeState==1){//如果目前的赞状态为1
                    val = actionVal == 1 ? 0 : -1;//计算最终的状态：如果继续agree，最终状态为0；如果disagree；最终状态为-1
                    changeVal = -1 ;//计算偏移量：无论如何操作，这里都要-1
                }else if(agreeState==0){//如果目前的赞状态为0
                    val = actionVal;//计算最终的状态：如果agree，最终状态为1；如果disagree；最终状态为-1
                    changeVal = actionVal == 1 ? 1 : 0;//计算偏移量：如果agree，状态+1；如果disagree；状态不变
                }else{//如果目前的点赞状态为-1
                    val = actionVal == 1 ? 1 : 0;//计算最终的状态：如果agree，最终状态为1；如果disagree；最终状态为0
                    changeVal = actionVal == 1 ? 1 : 0;//计算偏移量：如果agree，状态+1；如果disagree；状态不变
                }
                answerDao.updateAnswerMeta(answerId,changeVal);//使用偏移量更新answer表中agree_count值
                answerDao.updateAgreeByAnswerId(answerId,userId,val);//使用最终状态修改user_m2m_agree表中记录
                if(val == 1){
                    Dynamic dynamic = new Dynamic();
                    dynamic.setAnswerId(answerId);
                    dynamic.setType(4);
                    dynamic.setUserId(userId);
                    dynamic.setQuestionId(answerDao.selectById(answerId).getQuestionId());
                    userCenterDao.insert(dynamic);
                }
                return true;
            }else{//如果之前没有点过赞
                answerDao.updateAnswerMeta(answerId,actionVal == 1 ? 1 : 0);//使用偏移量更新answer表中agree_count值
                answerDao.addAgree(answerId,userId,actionVal);//user_m2m_agree表中新增记录
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public boolean toggleIsCollect(Long answerId, Long userId) {
        try {
            List<Map<String,Object>> collect = answerDao.getCollect(answerId, userId);
            if (collect.size() == 1) {
                System.out.println(3333);
                answerDao.deleteCollect(answerId, userId);
            } else {
                answerDao.addCollect(answerId, userId);
                Dynamic dynamic = new Dynamic();
                dynamic.setAnswerId(answerId);
                dynamic.setType(3);
                dynamic.setUserId(userId);
                dynamic.setQuestionId(answerDao.selectById(answerId).getQuestionId());
                userCenterDao.insert(dynamic);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean toggleIsLike(Long answerId, Long userId) {
        try {
            List<Map<String,Object>> like = answerDao.getLike(answerId, userId);
            if (like.size() == 1) {
                answerDao.deleteLike(answerId, userId);
                return true;
            } else {
                answerDao.addLike(answerId, userId);
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }
}
