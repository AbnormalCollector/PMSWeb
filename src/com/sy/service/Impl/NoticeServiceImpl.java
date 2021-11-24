package com.sy.service.Impl;

import com.sy.dao.Impl.NoticeDaoImpl;
import com.sy.dao.NoticeDao;
import com.sy.entity.Job;
import com.sy.entity.Notice;
import com.sy.service.NoticeService;
import com.sy.util.Constants;
import com.sy.util.DBUtil;
import com.sy.util.Result;

import java.util.List;

public class NoticeServiceImpl implements NoticeService {

    private NoticeDao noticeDao = new NoticeDaoImpl();
    @Override
    public Result findNotice(Notice notice, String pageNow, String pageSize) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int pn = Integer.parseInt(pageNow);
            int ps = Integer.parseInt(pageSize);
            List<Notice> Notices = noticeDao.selectNotice(notice, (pn - 1) * ps, ps);
            long count = noticeDao.selectCount(notice);
            result.setData(Notices);
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
    public Result removeNotice(String[] array) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int sums = 0;
            for(String str : array){
                int i = Integer.parseInt(str);
                int sum = noticeDao.deleteJNotice(i);
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
    public Result removeNotice(String id) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            Integer i = Integer.parseInt(id);
            int sum = noticeDao.deleteJNotice(i);
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
    public Result modifyNotice(Notice notice, String usedname) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int i = noticeDao.updateNotice(notice, usedname);
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
    public Result addNotice(Notice notice) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int i = noticeDao.insertNotice(notice);
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
