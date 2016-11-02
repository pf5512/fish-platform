package com.ippteam.fish.service;

import com.ippteam.fish.dao.UserMapper;
import com.ippteam.fish.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by pactera on 16/10/24.
 */

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userDao;

    public User getUserById(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }


    public Boolean login(String userName, String pwd) {
        UserExample userExample = new UserExample();
        userExample.or().andUserNameEqualTo(userName);

        List<User> users = userDao.selectByExample(userExample);
        if (users.size() > 0) {
            User user = users.get(0);
            if (user != null && user.getPassword().equals(pwd)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Boolean register(User user) {
        if (userDao.insert(user) == 1) {
            return true;
        } else {
            return false;
        }
    }
}