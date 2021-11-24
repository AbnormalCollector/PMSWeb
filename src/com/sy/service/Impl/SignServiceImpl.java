package com.sy.service.Impl;

import com.sy.dao.Impl.SignDaoImpl;
import com.sy.dao.SignDao;
import com.sy.entity.Sign;
import com.sy.entity.User;
import com.sy.service.SignService;
import com.sy.util.Constants;
import com.sy.util.DBUtil;
import com.sy.util.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SignServiceImpl implements SignService {
    private SignDao signDao = new SignDaoImpl();
    @Override
    public Result findByLogin(String username) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            User user = signDao.selectByLogin(username);
            result.setCode(Constants.CODE_SUCCESS);
            result.setMsg("登录成功！");
            result.setData(user);
            DBUtil.commitTx();
        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollbackTx();
        }
        return result;
    }

    @Override
    public Result addSign(Sign sign) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            long l = System.currentTimeMillis();
            Date date = new Date(l);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String parse = format.format(date);
            Sign selectSign = signDao.selectSign(sign);
            int i;
            if(selectSign==null) {
                sign.setStart(format.parse(parse));
                sign.setEnd(format.parse("00:00:00"));
                i = signDao.insertSign(sign);
            }else {
                selectSign.setEnd(format.parse(parse));
                i = signDao.updateSign(selectSign);
            }
            result.setData(i);
            result.setCode(Constants.CODE_SUCCESS);
            result.setMsg("打卡成功!");
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
    public Result findSign(String start) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            List<Sign> signs = signDao.selectSign(start);
            result.setData(signs);
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
    public Result findSign(String start, String end, String pageNow, String pageSize) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int pn = Integer.parseInt(pageNow);
            int ps = Integer.parseInt(pageSize);
            List<Sign> signs = signDao.selectSign(start, end, (pn - 1) * ps, ps);
            long count = signDao.selectCount(start, end);
            result.setData(signs);
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


}
