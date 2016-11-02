package com.ippteam.fish.service;

import com.ippteam.fish.entity.User;

/**
 * Created by pactera on 16/10/24.
 */
public interface UserService {

    User getUserById(Integer id);

    Boolean login(String userName, String pwd);

    Boolean register(User user);

}
