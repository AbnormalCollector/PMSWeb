package com.sy.service.Impl;

import com.sy.dao.Impl.JobDaoImpl;
import com.sy.dao.JobDao;
import com.sy.entity.Department;
import com.sy.entity.Job;
import com.sy.service.JobService;
import com.sy.util.Constants;
import com.sy.util.DBUtil;
import com.sy.util.Result;

import java.util.List;

public class JobServiceImpl implements JobService {
    private JobDao jobDao = new JobDaoImpl();
    @Override
    public Result findJob(Job job, String pageNow, String pageSize) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int pn = Integer.parseInt(pageNow);
            int ps = Integer.parseInt(pageSize);
            List<Job> jobs = jobDao.selectJob(job, (pn - 1) * ps, ps);
            long count = jobDao.selectCount(job);
            result.setData(jobs);
            result.setCount(count);
            result.setCode(Constants.CODE_SUCCESS);
            result.setMsg(Constants.MSG_SUCCESS);
            DBUtil.commitTx();
        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollbackTx();
            result.setCode(Constants.CODE_ERROR);
            result.setMsg("参数传递失败");
        }
        return result;
    }

    @Override
    public Result removeJob(String[] array) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int sums = 0;
            for(String str : array){
                int i = Integer.parseInt(str);
                int sum = jobDao.deleteJob(i);
                if(sum == 1){
                    sums = sum;
                }
            }
            result.setData(sums);
            result.setCode(Constants.CODE_SUCCESS);
            result.setMsg(Constants.MSG_SUCCESS);
            DBUtil.commitTx();
        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollbackTx();
            result.setCode(Constants.CODE_ERROR);
            result.setMsg(Constants.MSG_ERROR);
        }
        return result;
    }

    @Override
    public Result removeJob(String id) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            Integer i = Integer.parseInt(id);
            int sum = jobDao.deleteJob(i);
            result.setCode(Constants.CODE_SUCCESS);
            result.setMsg(Constants.MSG_SUCCESS);
            result.setData(sum);
            DBUtil.commitTx();
        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollbackTx();
            result.setCode(Constants.CODE_ERROR);
            result.setMsg(Constants.MSG_ERROR);
        }
        return result;
    }

    @Override
    public Result modifyJob(Job job, String usedname) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int i = jobDao.updateJob(job, usedname);
            result.setMsg(Constants.MSG_SUCCESS);
            result.setCode(Constants.CODE_SUCCESS);
            result.setData(i);
            DBUtil.commitTx();
        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollbackTx();
            result.setMsg(Constants.MSG_ERROR);
            result.setCode(Constants.CODE_ERROR);
        }
        return result;
    }

    @Override
    public Result addJob(Job job) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int i = jobDao.insertJob(job);
            result.setMsg(Constants.MSG_SUCCESS);
            result.setCode(Constants.CODE_SUCCESS);
            result.setData(i);
            DBUtil.commitTx();
        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollbackTx();
            result.setMsg(Constants.MSG_ERROR);
            result.setCode(Constants.CODE_ERROR);
        }
        return result;
    }
}
