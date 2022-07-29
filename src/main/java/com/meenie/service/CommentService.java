package com.meenie.service;

import com.meenie.domain.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByAnswerId(Long answerId);

    Integer getCommentsCountByAnswerId(Long answerId);

    boolean newComment(Comment comment);
}
