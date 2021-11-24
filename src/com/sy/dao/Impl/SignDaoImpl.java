package com.sy.dao.Impl;

import com.sy.dao.SignDao;
import com.sy.entity.Sign;
import com.sy.entity.User;
import com.sy.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SignDaoImpl implements SignDao {
    @Override
    public User selectByLogin(String username) throws Exception {
        List<User> users = DBUtil.executeQuery(User.class, "select * from user where username=?", username);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<Sign> selectSign(String start) throws Exception {
        List<Sign> signs = null;
        if(start == null || "".equals(start) || "NaN-NaN-NaN".equals(start)){
            signs = DBUtil.executeQuery(Sign.class, "select * from sign");
        }else {
           signs = DBUtil.executeQuery(Sign.class,"select * from sign where createtime > ?",start);
        }
        return signs;
    }

    @Override
    public List<Sign> selectSign(String start, String end, int firstIndex, int pageSize) throws Exception {
        List<Sign> signs = null;
        if (start == null || end == null || "".equals(start) || "".equals(end)) {
            signs = DBUtil.executeQuery(Sign.class, "select * from sign limit ?,?", firstIndex, pageSize);
        } else {
            signs = DBUtil.executeQuery(Sign.class,
                    "SELECT * FROM sign s where s.createtime > ? and s.createtime < ? limit ?,?",
                    start, end, firstIndex, pageSize);
        }
        return signs;
    }

    @Override
    public long selectCount(String start, String end) throws Exception {
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        if (start == null || end == null || "".equals(start) || "".equals(end)) {
            preparedStatement = connection.prepareStatement("select count(id) from sign");
        } else {
            preparedStatement = connection.prepareStatement("select count(id) from sign s where s.createtime > ? and s.createtime < ?");
            preparedStatement.setString(1, start);
            preparedStatement.setString(2, end);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getLong(1);
        }
        return 0;
    }


    @Override
    public Sign selectSign(Sign sign) throws Exception {

        if (sign != null) {
            List<Sign> signs = DBUtil.executeQuery(Sign.class, "select * from sign where uid=? and createtime=?", sign.getUid(), sign.getCreatetime());
            if (signs.size() != 0) {
                return signs.get(0);
            }
        }
        return null;
    }

    @Override
    public int insertSign(Sign sign) throws Exception {
        int i = DBUtil.executeUpdate("insert into sign(createtime,start,end,uid) values(?,?,?,?)", sign.getCreatetime(), sign.getStart(),sign.getEnd(),sign.getUid());
        return i;
    }

    @Override
    public int updateSign(Sign sign) throws Exception {
        int i = DBUtil.executeUpdate("update sign set end=? where uid=? and createtime=?", sign.getEnd(), sign.getUid(), sign.getCreatetime());
        return i;
    }
}
