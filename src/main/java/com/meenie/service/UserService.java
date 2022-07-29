package com.meenie.service;

import com.meenie.domain.PaginationRes;
import com.meenie.domain.User;

import java.util.List;

public interface UserService {
    boolean register(User user);

    boolean update(User user);

    boolean delete(Long id);
    boolean toggleFollow(Long userId,Long targetUserId);

    User getById(Long id);

    List<User> getAll();

    PaginationRes getUserList(Long pageIndex, Long pageSize);

    User login(User user);

    User getByName(String username);
}
