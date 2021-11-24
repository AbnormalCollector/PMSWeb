package com.sy.dao;


import com.sy.entity.Notice;

import java.util.List;

public interface NoticeDao {

    List<Notice> selectNotice(Notice notice, int firstIndex, int pageSize)throws Exception;
    long selectCount(Notice notice)throws Exception;
    int deleteJNotice(Integer id) throws Exception;
    int updateNotice(Notice notice,String usedname)throws Exception;
    int insertNotice(Notice notice) throws Exception;
}
