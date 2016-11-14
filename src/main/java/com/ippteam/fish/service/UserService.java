package com.ippteam.fish.service;

import com.ippteam.fish.entity.User;
import sun.jvm.hotspot.types.basic.BasicOopField;

/**
 * Created by pactera on 16/10/24.
 */
public interface UserService {

    User getUserById(Integer id);

    User getUserByUserName(String userName);

    User getUserByEmail(String email);

    User getUserByPhone(String phone);

    boolean login(String account, String pwd);

    boolean registerByEmail(String email, String password);

    boolean registerByPhone(String phone, String password);

    boolean registerByUserName(String userName, String password);

}
