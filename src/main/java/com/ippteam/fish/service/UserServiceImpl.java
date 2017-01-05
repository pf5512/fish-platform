package com.ippteam.fish.service;

import com.ippteam.fish.controller.UserController;
import com.ippteam.fish.dao.UserMapper;
import com.ippteam.fish.entity.*;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.service.util.ReportServiceImpl;
import com.ippteam.fish.util.Fix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ippteam.fish.util.Final.*;


/**
 * Created by pactera on 16/10/24.
 */

@Service("UserService")
public class UserServiceImpl extends ReportServiceImpl implements UserService {

    @Autowired
    private UserMapper userDao;

    public User getUserById(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }


    public User getUserByUserName(String userName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(userName);

        List<User> users = userDao.selectByExample(userExample);
        return Fix.list(users, 0);
    }

    public User getUserByEmail(String email) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email);

        List<User> users = userDao.selectByExample(userExample);
        return Fix.list(users, 0);
    }

    public User getUserByPhone(String phone) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPhoneEqualTo(phone);

        List<User> users = userDao.selectByExample(userExample);
        return Fix.list(users, 0);
    }

    public User getUserByAccount(String account) {
        UserExample userExample = new UserExample();
        userExample.or().andUserNameEqualTo(account);
        userExample.or().andPhoneEqualTo(account);
        userExample.or().andEmailEqualTo(account);

        List<User> users = userDao.selectByExample(userExample);
        return Fix.list(users, 0);
    }

    public User login(String account, String pwd) {
        User user = this.getUserByAccount(account);
        if (user == null || !user.getPassword().equals(pwd)) {
            return null;
        }
        return user;
    }

    public User register(User user) {
        String account;
        String regWay = user.getRegisterWay();

        if (REG_WAY_USERNAME.equals(regWay)) {
            account = user.getUserName();
            user.setEmail(null);
            user.setPhone(null);
        } else if (REG_WAY_EMAIL.equals(regWay)) {
            account = user.getEmail();
            user.setUserName(null);
            user.setPhone(null);
        } else if (REG_WAY_PHONE.equals(regWay)) {
            account = user.getPhone();
            user.setUserName(null);
            user.setEmail(null);
        } else {
            return null;
        }

        // 验证是否已经被注册
        if (this.getUserByAccount(account) != null) {
            return null;
        }

        int row = userDao.insert(user);
        if (row <= 0) {
            return null;
        }
        return this.getUserByAccount(account);
    }

    public User update(User user) {
        Integer id = user.getId();
        userDao.updateByPrimaryKey(user);
        return userDao.selectByPrimaryKey(id);
    }

    public void banned(String id) {

    }
}