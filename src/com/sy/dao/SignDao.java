package com.sy.dao;

import com.sy.entity.Sign;
import com.sy.entity.User;

import java.util.List;

public interface SignDao {
    User selectByLogin(String username) throws Exception;
    List<Sign> selectSign(String start)throws Exception;
    List<Sign> selectSign(String start, String end, int firstIndex, int pageSize)throws Exception;
    long selectCount(String start,String end)throws Exception;
    Sign selectSign(Sign sign) throws Exception;
    int insertSign(Sign sign) throws Exception;
    int updateSign(Sign sign) throws Exception;
}
