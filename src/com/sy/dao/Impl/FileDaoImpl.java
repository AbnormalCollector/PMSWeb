package com.sy.dao.Impl;

import com.sy.dao.FileDao;
import com.sy.entity.File;
import com.sy.entity.Job;
import com.sy.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class FileDaoImpl implements FileDao {
    @Override
    public List<File> selectFile(File file, int firstIndex, int pageSize) throws Exception {
        List<File> files = null;
        if(file == null || null == file.getTitle()){
            files = DBUtil.executeQuery(File.class, "select * from file limit ?,?", firstIndex, pageSize);
        }else {
            files = DBUtil.executeQuery(File.class,
                    "SELECT * FROM file WHERE title LIKE CONCAT('%',?,'%') limit ?,?",file.getTitle(),firstIndex,pageSize );
        }
        return files;
    }

    @Override
    public long selectCount(File file) throws Exception {
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        if(file == null || null == file.getTitle()){
            preparedStatement = connection.prepareStatement("select count(id) from file");
        }else {
            preparedStatement = connection.prepareStatement("select count(id) from file where title LIKE CONCAT('%',?,'%')");
            preparedStatement.setString(1,file.getTitle());
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getLong(1);
        }
        return 0;
    }

    @Override
    public int deleteFile(Integer id) throws Exception {
        int i = DBUtil.executeUpdate("delete from file where id = ?", id);
        return 0;
    }


    @Override
    public int insertFile(File file) throws Exception {
        int i = DBUtil.executeUpdate("insert into file(title,filename,remark,uid) values(?,?,?,?)",file.getTitle(),file.getFileName(),file.getRemark(),file.getUid());
        return i;
    }
}
