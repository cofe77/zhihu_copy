package com.meenie.controller;

import com.meenie.common.BaseUserInfo;
import com.meenie.domain.Answer;
import com.meenie.service.AnswerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    public ResResult add(@RequestBody Answer answer){
        answerService.add(answer);
        return new ResResult(ResCode.UPDATE_OK,"回答成功",answer.getId());
    }

    @PostMapping("/updateAnswerMeta")
    public ResResult updateAnswerMeta(@RequestBody Map<String, Object> data){
        long answerId = Long.parseLong(data.get("answerId").toString());
        System.out.println("11111111111111111111");
        System.out.println(answerId);
        System.out.println("11111111111111111111");
        long userId = Long.parseLong(BaseUserInfo.get("id"));
        System.out.println("2222222222222222222");
        System.out.println(userId);
        System.out.println("222222222222222222222");
        String type = data.get("type").toString();
        System.out.println("3333333333333333333");
        System.out.println(answerId);
        System.out.println("333333333333333333333");
        if("agree".equals(type)){
            int change = Integer.parseInt(data.get("value").toString());
            answerService.updateAnswerMeta(answerId,userId,change);
            return new ResResult(ResCode.UPDATE_OK,"修改成功",null);
        }else if("collect".equals(type)){
            answerService.toggleIsCollect(answerId,userId);
            return new ResResult(ResCode.UPDATE_OK,"修改成功",null);
        }else if("like".equals(type)){
            answerService.toggleIsLike(answerId,userId);
            return new ResResult(ResCode.UPDATE_OK,"修改成功",null);
        }
        return new ResResult(ResCode.UPDATE_ERR,"修改失败！",null);
    }
}
