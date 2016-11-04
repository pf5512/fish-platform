package com.ippteam.fish.service;

import com.ippteam.fish.dao.UserMapper;
import com.ippteam.fish.entity.*;
import com.ippteam.fish.entity.User;
import org.apache.catalina.*;
import org.apache.commons.codec.language.bm.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.ippteam.fish.util.Final.REG_WAY_EMAIL;
import static com.ippteam.fish.util.Final.REG_WAY_PHONE;
import static com.ippteam.fish.util.Final.REG_WAY_USERNAME;


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


    public User getUserByUserName(String userName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(userName);

        List<User> users = userDao.selectByExample(userExample);
        return users.get(0);
    }

    public User getUserByEmail(String email) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email);

        List<User> users = userDao.selectByExample(userExample);
        return users.get(0);
    }

    public User getUserByPhone(String phone) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPhoneEqualTo(phone);

        List<User> users = userDao.selectByExample(userExample);
        return users.get(0);
    }

    public boolean login(String userName, String pwd) {
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


    public boolean registerByEmail(String email, String password) {
        Date curDate = new Date();

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setCreateTime(curDate);
        user.setUpdateTime(curDate);
        user.setRegisterTime(curDate);
        user.setRegisterWay(REG_WAY_EMAIL);
//        user.setRegisterIp();

        if (userDao.insert(user) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean registerByPhone(String phone, String password) {
        Date curDate = new Date();

        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setCreateTime(curDate);
        user.setUpdateTime(curDate);
        user.setRegisterTime(curDate);
        user.setRegisterWay(REG_WAY_PHONE);
//        user.setRegisterIp();

        if (userDao.insert(user) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean registerByUserName(String userName, String password) {
        Date curDate = new Date();

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setCreateTime(curDate);
        user.setUpdateTime(curDate);
        user.setRegisterTime(curDate);
        user.setRegisterWay(REG_WAY_USERNAME);
//        user.setRegisterIp();

        if (userDao.insert(user) == 1) {
            return true;
        } else {
            return false;
        }
    }
}