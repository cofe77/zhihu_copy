package com.meenie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meenie.common.BaseUserInfo;
import com.meenie.controller.ResCode;
import com.meenie.dao.EducationDao;
import com.meenie.dao.OccupationDao;
import com.meenie.dao.UserDao;
import com.meenie.domain.Education;
import com.meenie.domain.Occupation;
import com.meenie.domain.PaginationRes;
import com.meenie.domain.User;
import com.meenie.exception.BusinessException;
import com.meenie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private EducationDao educationDao;
    @Autowired
    private OccupationDao occupationDao;

    public boolean register(User user) {
        System.out.println(user);
        userDao.insert(user);
        return true;
    }

    public boolean update(User user) {
        userDao.updateById(user);
        return true;
    }

    public boolean delete(Long id) {
        userDao.deleteById(id);
        return true;
    }

    public boolean toggleFollow(Long userId, Long targetUserId) {
        List<Map<String,Object>> follow = userDao.getFollow(userId,targetUserId);
        if(follow.size()==1){
            userDao.cancelFollow(userId,targetUserId);
        }else{
            userDao.addFollow(userId,targetUserId);
        }
        return userDao.getFollow(userId,targetUserId).size()==1;
    }

    public User getById(Long id) {
        User user = userDao.selectById(id);
        long userId = Long.parseLong(BaseUserInfo.get("id"));
        List<Map<String, Object>> follow = userDao.getFollow(userId, id);
        user.setFollow(follow.size()==1);
        user.setEducations(educationDao.selectList(new LambdaQueryWrapper<Education>().eq(Education::getUserId,id)));
        user.setOccupations(occupationDao.selectList(new LambdaQueryWrapper<Occupation>().eq(Occupation::getUserId,id)));
        return user;
    }

    public List<User> getAll() {
        return userDao.selectList(null);
    }

    public PaginationRes getUserList(Long pageIndex, Long pageSize) {
        Page<User> page = new Page<>(pageIndex,pageSize);
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();

        lqw.select(User.class,tableFieldInfo -> !tableFieldInfo.getColumn().equals("pwd"));

        List<User> records = userDao.selectPage(page, lqw).getRecords();
        System.out.println(records);
        PaginationRes res = new PaginationRes();
        res.setTotal(page.getTotal());
        res.setPageSize(pageSize);
        res.setData(records);
        res.setPageIndex(pageIndex);
        return res;
    }

    @Override
    public User login(User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername,user.getUsername()).eq(User::getPwd,user.getPwd());
        User user1 = userDao.selectOne(lqw);
        user1.setFollow(false);
        user1.setEducations(educationDao.selectList(new LambdaQueryWrapper<Education>().eq(Education::getUserId,user1.getId())));
        user1.setOccupations(occupationDao.selectList(new LambdaQueryWrapper<Occupation>().eq(Occupation::getUserId,user1.getId())));
        return user1;
    }

    @Override
    public User getByName(String username) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername,username.trim());
        User user = userDao.selectOne(lqw);
        long userId = Long.parseLong(BaseUserInfo.get("id"));
        List<Map<String, Object>> follow = userDao.getFollow(userId, user.getId());
        user.setFollow(follow.size()==1);
        user.setEducations(educationDao.selectList(new LambdaQueryWrapper<Education>().eq(Education::getUserId,user.getId())));
        user.setOccupations(occupationDao.selectList(new LambdaQueryWrapper<Occupation>().eq(Occupation::getUserId,user.getId())));
        return user;
    }
}
