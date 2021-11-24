package com.sy.service;

import com.sy.entity.User;
import com.sy.util.Result;

import java.util.List;

public interface UserService {
    Result findByLogin(String username, String password);



    Result findUser(User user, String pageNow, String pageSize);

    Result removeUser(String[] array);

    Result removeUser(String id);

    Result modifyUser(User user,String usedname);

    Result modifyUser(User user);

    Result addUser(User user);
}
