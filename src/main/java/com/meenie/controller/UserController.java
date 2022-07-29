package com.meenie.controller;

import com.meenie.domain.PaginationReq;
import com.meenie.domain.PaginationRes;
import com.meenie.domain.User;
import com.meenie.exception.BusinessException;
import com.meenie.service.UserService;
import com.meenie.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResResult register(@RequestBody User user) {
        if (user.getUsername() == null||"".equals(user.getUsername())) {
            throw new BusinessException(ResCode.BUSINESS_ERR, "用户名不能为空！");
        }
        boolean rs = userService.register(user);
        return new ResResult(rs ? ResCode.ADD_OK : ResCode.ADD_ERR, rs ? "注册成功！" : "注册失败！", rs ? user.getId() : null);
    }

    @PostMapping("/login")
    public ResResult login(@RequestBody User user) {
        User user1 = userService.login(user);
        Map<String,String> map = new HashMap<>();
        map.put("username",user1.getUsername());
        map.put("id", user1.getId().toString());
        String token = JWTUtils.getToken(map);
        Map<String,Object> rs = new HashMap<>();
        rs.put("token",token);
        rs.put("userInfo",user1);
        return new ResResult(user1!=null ? ResCode.ADD_OK : ResCode.ADD_ERR, user1!=null ? "登录成功！" : "登录失败！", rs);
    }

    @PostMapping("/follow")
    public ResResult follow(@RequestBody Map<String, Object> data) {
        long userId = Long.parseLong(data.get("userId").toString());
        long targetUserId = Long.parseLong(data.get("targetUserId").toString());
        boolean rs = userService.toggleFollow(userId,targetUserId);
        return new ResResult(ResCode.ADD_OK,"成功！", rs);
    }

    @PutMapping
    public ResResult update(@RequestBody User user) {
        boolean rs = userService.update(user);
        return new ResResult(rs ? ResCode.UPDATE_OK : ResCode.UPDATE_ERR, rs ? "修改成功！" : "", userService.update(user));
    }

    @DeleteMapping("/{id}")
    public ResResult delete(@PathVariable Long id) {
        boolean rs = userService.delete(id);
        return new ResResult(rs ? ResCode.DELETE_OK : ResCode.DELETE_ERR, rs ? "删除成功！" : "", userService.delete(id));
    }

    @GetMapping("/{id}")
    public ResResult getById(@PathVariable Long id) {
        User user = userService.getById(id);
        Integer rsCode = user != null ? ResCode.GET_OK : ResCode.GET_ERR;
        String rsMsg = user != null ? "获取用户信息成功！" : "获取用户信息失败！";
        return new ResResult(rsCode, rsMsg, user);
    }


    @GetMapping("/getUserInfoByName/{username}")
    public ResResult getByName(@PathVariable String username) {
        User user = userService.getByName(username);
        Integer rsCode = user != null ? ResCode.GET_OK : ResCode.GET_ERR;
        String rsMsg = user != null ? "获取用户信息成功！" : "获取用户信息失败！";
        return new ResResult(rsCode, rsMsg, user);
    }

    @GetMapping
    public ResResult getAll() {
        List<User> users = userService.getAll();
        Integer rsCode = users != null ? ResCode.GET_OK : ResCode.GET_ERR;
        String rsMsg = users != null ? "获取用户信息成功！" : "获取用户信息失败！";
        return new ResResult(rsCode, rsMsg, users);
    }

    @GetMapping("/page")
    public ResResult getList(@RequestBody PaginationReq req) {
        PaginationRes data = userService.getUserList(req.getPageIndex(), req.getPageSize());
        Integer rsCode = data != null ? ResCode.GET_OK : ResCode.GET_ERR;
        String rsMsg = data != null ? "获取用户信息成功！" : "获取用户信息失败！";
        return new ResResult(rsCode, rsMsg, data);
    }
}
