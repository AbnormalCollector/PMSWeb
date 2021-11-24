package com.sy.dao.Impl;


import com.sy.entity.Notice;
import com.sy.dao.NoticeDao;
import com.sy.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class NoticeDaoImpl implements NoticeDao {
    @Override
    public List<Notice> selectNotice(Notice notice, int firstIndex, int pageSize) throws Exception {
        List<Notice> notices = null;
        if(notice == null){
            notices = DBUtil.executeQuery(Notice.class, "select * from notice limit ?,?", firstIndex, pageSize);
        }else {
            notices = DBUtil.executeQuery(Notice.class,
                    "SELECT * FROM notice WHERE title LIKE CONCAT('%',?,'%') and content LIKE CONCAT('%',?,'%') limit ?,?",
                    notice.getTitle() == null?"":notice.getTitle(),
                    notice.getContent() == null?"":notice.getContent(),
                    firstIndex,pageSize );
        }
        return notices;
    }

    @Override
    public long selectCount(Notice notice) throws Exception {
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        if(notice == null){
            preparedStatement = connection.prepareStatement("select count(id) from notice");
        }else {
            preparedStatement = connection.prepareStatement("select count(id) from notice where title LIKE CONCAT('%',?,'%') and content LIKE CONCAT('%',?,'%')");
            preparedStatement.setString(1,notice.getTitle() == null?"":notice.getTitle());

            preparedStatement.setString(2,notice.getContent() == null?"":notice.getContent());
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getLong(1);
        }

        return 0;
    }

    @Override
    public int deleteJNotice(Integer id) throws Exception {
        int i = DBUtil.executeUpdate("delete from notice where id = ?", id);
        return i;
    }

    @Override
    public int updateNotice(Notice notice, String usedname) throws Exception {
        int i = DBUtil.executeUpdate("update notice set title=?,content=? where title=?",notice.getTitle(),notice.getContent(),usedname);
        return i;
    }

    @Override
    public int insertNotice(Notice notice) throws Exception {
        int i = DBUtil.executeUpdate("insert into notice(title,content,uid) values(?,?,?)",
                notice.getTitle(), notice.getContent(), notice.getUid());
        return i;
    }
}
