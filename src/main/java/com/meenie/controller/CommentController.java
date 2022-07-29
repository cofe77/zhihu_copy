package com.meenie.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.meenie.domain.Answer;
import com.meenie.domain.Comment;
import com.meenie.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @GetMapping("/getCommentsByAnswerId/{id}")
    public ResResult getCommentsByAnswerId(@PathVariable Long id){
        return new ResResult(ResCode.GET_OK,"获取信息成功！",commentService.getCommentsByAnswerId(id));
    }

    @PostMapping
    public ResResult newComment(@RequestBody Comment comment){
        commentService.newComment(comment);
        return new ResResult(ResCode.ADD_OK,"添加成功！",comment.getId());
    }
}
