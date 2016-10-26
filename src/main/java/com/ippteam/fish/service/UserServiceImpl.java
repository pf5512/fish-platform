package com.ippteam.fish.service;

import com.ippteam.fish.dao.IUserDao;
import com.ippteam.fish.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pactera on 16/10/24.
 */

@Service("UserServiceImpl")
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    public User getUserById(Integer id) {
        return userDao.getUserByID(id);
    }


    public Boolean login(String userName, String pwd) {
        User user = userDao.getUserByUserName(userName);
        if (user != null && user.getPassword().equals(pwd)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean register(User user) {
        if (userDao.insert(user) == 1) {
            return true;
        } else {
            return false;
        }
    }
}