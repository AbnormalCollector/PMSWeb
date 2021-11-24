package com.sy.service.Impl;

import com.sy.dao.DepartmentDao;
import com.sy.dao.Impl.DepartmentDaoImpl;
import com.sy.entity.Department;
import com.sy.entity.User;
import com.sy.service.DepartmentService;
import com.sy.util.Constants;
import com.sy.util.DBUtil;
import com.sy.util.Result;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentDao departmentDao = new DepartmentDaoImpl();
    //部门分页
    @Override
    public Result findDepartment(Department department, String pageNow, String pageSize) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int pn = Integer.parseInt(pageNow);
            int ps = Integer.parseInt(pageSize);
            List<Department> departments = departmentDao.selectDepartment(department, (pn - 1) * ps, ps);
            long count = departmentDao.selectCount(department);
            result.setData(departments);
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
    public Result removeDepartment(String[] array) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int sums = 0;
            for(String str : array){
                int i = Integer.parseInt(str);
                int sum = departmentDao.deleteDepartment(i);
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
    public Result removeDepartment(String id) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            Integer i = Integer.parseInt(id);
            int sum = departmentDao.deleteDepartment(i);
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
    public Result modifyDepartment(Department department, String usedname) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int i = departmentDao.updateDepartment(department, usedname);
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
    public Result addDepartment(Department department) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int i = departmentDao.insertDepartment(department);
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
