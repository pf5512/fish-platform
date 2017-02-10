package com.ippteam.fish.service;

import com.ippteam.fish.dao.OauthMapper;
import com.ippteam.fish.dao.UserMapper;
import com.ippteam.fish.entity.*;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.service.util.ReportServiceImpl;
import com.ippteam.fish.service.util.ReportType;
import com.ippteam.fish.util.Fix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ippteam.fish.util.Final.*;


/**
 * Created by pactera on 16/10/24.
 */
@ReportType("USER")
@Service("UserService")
public class UserServiceImpl extends ReportServiceImpl implements UserService {

    @Autowired
    private UserMapper userDao;
    @Autowired
    private OauthMapper oauthDao;

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

    @Transactional
    public Map login(Oauth oauth) {
        Oauth o = oauthByOauthId(oauth.getOauthId());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("isNew", false);
        if (o == null) {
            User u = new User();
            u.setRegisterWay(REG_WAY_OAUTH);
            User user = register(u);
            oauthBind(oauth, user);
            map.put("isNew", true);
            map.put("user", user);
            return map;
        }
        map.put("user", getUserById(o.getUid()));
        return map;
    }

    public Oauth oauthByOauthId(String oauthId) {
        OauthExample oauthExample = new OauthExample();
        oauthExample.createCriteria().andOauthIdEqualTo(oauthId);
        List<Oauth> oauths = oauthDao.selectByExample(oauthExample);
        return Fix.list(oauths, 0);
    }

    public Oauth oauthBind(Oauth oauth, User user) {
        oauth.setUid(user.getId());
        oauthDao.insert(oauth);

        OauthExample oauthExample = new OauthExample();
        oauthExample.createCriteria().
                andOauthIdEqualTo(oauth.getOauthId()).
                andUidEqualTo(user.getId());
        List<Oauth> oauths = oauthDao.selectByExample(oauthExample);
        return Fix.list(oauths, 0);
    }

    public User register(User user) {
        String account = null;
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
        } else if (REG_WAY_OAUTH.equals(regWay)) {
            user.setUserName(null);
            user.setPhone(null);
            user.setEmail(null);
        } else {
            return null;
        }

        // 验证是否已经被注册(用户名、邮箱、手机号注册时)
        if (!REG_WAY_OAUTH.equals(regWay)) {
            if (this.getUserByAccount(account) != null) {
                return null;
            }
        }

        user.setManager(false);
        user.setBanned(false);
        int row = userDao.insert(user);
        if (row <= 0) {
            return null;
        }
        return user;
    }

    public User update(User user) {
        Integer id = user.getId();
        userDao.updateByPrimaryKey(user);
        return userDao.selectByPrimaryKey(id);
    }

    public void banned(String id) {
        User user = this.getUserById(Integer.parseInt(id));
        user.setBanned(true);
        userDao.updateByPrimaryKey(user);
    }
}