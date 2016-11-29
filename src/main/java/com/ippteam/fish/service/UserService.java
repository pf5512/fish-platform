package com.ippteam.fish.service;

import com.ippteam.fish.entity.User;

/**
 * Created by pactera on 16/10/24.
 */
public interface UserService {

    User getUserById(Integer id);

    User getUserByUserName(String userName);

    User getUserByEmail(String email);

    User getUserByPhone(String phone);

    User getUserByAccount(String account);

    User login(String account, String pwd);

    User register(User user);

    User update(User user);
}
