package com.ippteam.fish.dao;

import com.ippteam.fish.entity.User;
import org.springframework.stereotype.Repository;

@Repository("IUser")
public interface IUserDao {
    public User getUserByID(Integer id);

    public User getUserByUserName(String userName);

    public Integer insert(User user);
}