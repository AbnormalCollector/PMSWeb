package com.sy.dao.Impl;

import com.sy.dao.JobDao;
import com.sy.entity.Department;
import com.sy.entity.Job;
import com.sy.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class JobDaoImpl implements JobDao {
    @Override
    public List<Job> selectJob(Job job, int firstIndex, int pageSize) throws Exception {
        List<Job> Jobs = null;
        if(job == null || null == job.getName()){
            Jobs = DBUtil.executeQuery(Job.class, "select * from job limit ?,?", firstIndex, pageSize);
        }else {
            Jobs = DBUtil.executeQuery(Job.class,
                    "SELECT * FROM job WHERE name LIKE CONCAT('%',?,'%') limit ?,?",job.getName(),firstIndex,pageSize );
        }
        return Jobs;
    }

    @Override
    public long selectCount(Job job) throws Exception {
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        if(job == null || null == job.getName()){
            preparedStatement = connection.prepareStatement("select count(id) from job");
        }else {
            preparedStatement = connection.prepareStatement("select count(id) from job where name LIKE CONCAT('%',?,'%')");
            preparedStatement.setString(1,job.getName());
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getLong(1);
        }
        return 0;
    }

    @Override
    public int deleteJob(Integer id) throws Exception {
        int i = DBUtil.executeUpdate("delete from job where id = ?", id);
        return i;
    }

    @Override
    public int updateJob(Job job, String usedname) throws Exception {
        int i = DBUtil.executeUpdate("update job set name=?,remark=? where name=?",job.getName(),job.getRemark(),usedname);
        return i;
    }

    @Override
    public int insertJob(Job job) throws Exception {
        int i = DBUtil.executeUpdate("insert into job(name,remark) values(?,?)",job.getName(),job.getRemark());
        return i;
    }
}
