package com.sy.dao;

import com.sy.entity.Department;
import com.sy.entity.Job;

import java.util.List;

public interface JobDao {
    List<Job> selectJob(Job job, int firstIndex, int pageSize)throws Exception;
    long selectCount(Job job)throws Exception;
    int deleteJob(Integer id) throws Exception;
    int updateJob(Job job,String usedname)throws Exception;
    int insertJob(Job job) throws Exception;
}
