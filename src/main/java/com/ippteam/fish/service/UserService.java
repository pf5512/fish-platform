package com.ippteam.fish.service;

import com.ippteam.fish.entity.User;
import com.ippteam.fish.service.util.ReportService;

/**
 * Created by pactera on 16/10/24.
 */
public interface UserService extends ReportService {

    User getUserById(Integer id);

    User getUserByUserName(String userName);

    User getUserByEmail(String email);

    User getUserByPhone(String phone);

    User getUserByAccount(String account);

    User login(String account, String pwd);

    User register(User user);

    User update(User user);
}
