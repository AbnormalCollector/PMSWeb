package com.sy.dao.Impl;

import com.sy.dao.UserDao;
import com.sy.entity.User;
import com.sy.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User selectByLogin(String username, String password) throws Exception {
        List<User> users = DBUtil.executeQuery(User.class, "select * from user where username=? and password=?", username, password);
        if(users.isEmpty()){
            return null;
        }
        return users.get(0);
    }



    @Override
    public List<User> selectUser(User user,int firstIndex, int pageSize) throws Exception{
        List<User> users = null;
        if(user == null || null == user.getUsername()){
            users = DBUtil.executeQuery(User.class, "select * from user ORDER BY createdate DESC limit ?,?", firstIndex, pageSize);
        }else {
            users = DBUtil.executeQuery(User.class,
                    "SELECT * FROM user WHERE username LIKE CONCAT('%',?,'%') and status LIKE CONCAT('%',?,'%') ORDER BY createdate DESC limit ?,?",
                    user.getUsername() == null?"":user.getUsername(),
                    user.getStatus() == null?"":user.getStatus(),
                    firstIndex,pageSize );
        }
        return users;
    }

    @Override
    public long selectCount(User user) throws Exception{
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        if(user == null || null == user.getUsername()){
            preparedStatement = connection.prepareStatement("select count(id) from user");
        }else {
            preparedStatement = connection.prepareStatement("select count(id) from user where username LIKE CONCAT('%',?,'%') and status LIKE CONCAT('%',?,'%')");
            preparedStatement.setString(1,user.getUsername() == null?"":user.getUsername());
            preparedStatement.setString(2,user.getStatus() == null?"":user.getStatus().toString());
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getLong(1);
        }

        return 0;
    }

    @Override
    public int deleteUser(Integer id) throws Exception {
        int i = DBUtil.executeUpdate("delete from user where id = ?", id);
        return i;
    }

    @Override
    public int updateUser(User user,String usedname) throws Exception {
        int i = DBUtil.executeUpdate("update user set username=?,password=?,loginname=?,status=? where username=?",
                user.getUsername(), user.getPassword(), user.getLoginname(), user.getStatus(), usedname);
        return i;
    }

    @Override
    public int updateUser(User user) throws Exception {
        int i = DBUtil.executeUpdate("update user set status=? where username=?", user.getStatus(), user.getUsername());
        return i;
    }

    @Override
    public int insertUser(User user) throws Exception {
        int i = DBUtil.executeUpdate("insert into user(username,password,loginname,status) values(?,?,?,?)",
                user.getUsername(), user.getPassword(), user.getLoginname(), user.getStatus());
        return i;
    }
}
