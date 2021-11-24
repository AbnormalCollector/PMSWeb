package com.sy.service;

import com.sy.entity.Department;
import com.sy.entity.Job;
import com.sy.util.Result;

public interface JobService {

    Result findJob(Job job, String pageNow, String pageSize);

    Result removeJob(String[] array);

    Result removeJob(String id);

    Result modifyJob(Job job, String usedname);

    Result addJob(Job job);
}
