package com.meenie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meenie.dao.CommentDao;
import com.meenie.dao.UserDao;
import com.meenie.domain.Comment;
import com.meenie.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Comment> getCommentsByAnswerId(Long answerId) {
        List<Comment> originComments = commentDao.getOriginCommentsByAnswerIdAndType(answerId);
        for (int i = 0; i < originComments.size(); i++) {
            originComments.get(i).setAuthor(userDao.selectById(originComments.get(i).getAuthorId()));
            List<Comment> replyComments = commentDao.getRepliedCommentsByAnswerIdAndType(answerId,originComments.get(i).getId());
            if(replyComments.size()!=0){
                for (int j = 0; j < replyComments.size(); j++) {
                    replyComments.get(j).setAuthor(userDao.selectById(replyComments.get(j).getAuthorId()));
                    replyComments.get(j).setRepliedUser(userDao.selectById(replyComments.get(j).getRepliedUserId()));
                }
                originComments.get(i).setMoreReply(replyComments);
            }else{
                originComments.get(i).setMoreReply(new ArrayList<>());
            }
        }
        return originComments;
    }

    @Override
    public Integer getCommentsCountByAnswerId(Long answerId) {
        return commentDao.getCommentsCountByAnswerId(answerId);
    }

    @Override
    public boolean newComment(Comment comment) {
        commentDao.insert(comment);
        return true;
    }


}
