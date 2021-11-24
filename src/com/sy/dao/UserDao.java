package com.sy.dao;

import com.sy.entity.User;

import java.util.List;

public interface UserDao {

    User selectByLogin(String username, String password) throws Exception;

    List<User> selectUser(User user,int firstIndex, int pageSize)throws Exception;
    long selectCount(User user)throws Exception;
    int deleteUser(Integer id) throws Exception;
    int updateUser(User user,String usedname)throws Exception;
    int updateUser(User user) throws Exception;
    int insertUser(User user) throws Exception;
}
