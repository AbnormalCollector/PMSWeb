package com.sy.service.Impl;

import com.sy.dao.Impl.UserDaoImpl;
import com.sy.dao.UserDao;
import com.sy.entity.User;
import com.sy.service.UserService;
import com.sy.util.Constants;
import com.sy.util.DBUtil;
import com.sy.util.RequestContextUtil;
import com.sy.util.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public Result findByLogin(String username, String password) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            User user = userDao.selectByLogin(username, password);
            if(user==null){
                result.setCode(Constants.CODE_ERROR);
                result.setMsg("用户名或密码错误！");
            }else {
                HttpSession session = RequestContextUtil.getRequest().getSession();
                session.setAttribute(Constants.USER_SESSION_ID,user);
                result.setCode(Constants.CODE_SUCCESS);
                result.setMsg("登录成功！");
                result.setData(user);
            }
            DBUtil.commitTx();
        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollbackTx();
        }
        return result;
    }



    @Override
    public Result findUser(User user, String pageNow, String pageSize) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int pn = Integer.parseInt(pageNow);
            int ps = Integer.parseInt(pageSize);
            List<User> users = userDao.selectUser(user, (pn - 1) * ps, ps);
            long count = userDao.selectCount(user);
            result.setData(users);
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
    public Result removeUser(String[] array) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int sums = 0;
            for(String str : array){
                int i = Integer.parseInt(str);
                int sum = userDao.deleteUser(i);
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
    public Result removeUser(String id) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            Integer i = Integer.parseInt(id);
            int sum = userDao.deleteUser(i);
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
    public Result modifyUser(User user,String usedname) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int i = userDao.updateUser(user, usedname);
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
    public Result modifyUser(User user) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            Integer status = user.getStatus();
            if(status == 0){
                status = 1;
                user.setStatus(status);
            }else {
                status = 0;
                user.setStatus(status);
            }
            int i = userDao.updateUser(user);
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
    public Result addUser(User user) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int i = userDao.insertUser(user);
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
