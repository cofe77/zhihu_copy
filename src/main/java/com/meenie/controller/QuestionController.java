package com.meenie.controller;

import com.meenie.domain.Question;
import com.meenie.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public ResResult add(@RequestBody Question question) {
        boolean rs = questionService.add(question);
        return new ResResult(rs ? ResCode.ADD_OK : ResCode.ADD_ERR, rs ? "添加成功！" : "添加失败！", rs ? question.getId() : null);
    }

    @PutMapping
    public ResResult update(@RequestBody Question question) {
        boolean rs = questionService.update(question);
        return new ResResult(rs ? ResCode.UPDATE_OK : ResCode.UPDATE_ERR, rs ? "修改成功！" : "", questionService.update(question));
    }

    @DeleteMapping("/{id}")
    public ResResult delete(@PathVariable Long id) {
        boolean rs = questionService.delete(id);
        return new ResResult(rs ? ResCode.DELETE_OK : ResCode.DELETE_ERR, rs ? "删除成功！" : "", questionService.delete(id));
    }

    @GetMapping("/{id}")
    public ResResult getById(@PathVariable Long id) {
        Question question = questionService.getById(id);
        Integer rsCode = question != null ? ResCode.GET_OK : ResCode.GET_ERR;
        String rsMsg = question != null ? "获取用户信息成功！" : "获取用户信息失败！";
        return new ResResult(rsCode, rsMsg, question);
    }

    @GetMapping
    public ResResult getAll() {
        List<Question> questions = questionService.getAll();
        Integer rsCode = questions != null ? ResCode.GET_OK : ResCode.GET_ERR;
        String rsMsg = questions != null ? "获取用户信息成功！" : "获取用户信息失败！";
        return new ResResult(rsCode, rsMsg, questions);
    }
}
