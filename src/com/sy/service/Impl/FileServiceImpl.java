package com.sy.service.Impl;

import com.sy.dao.FileDao;
import com.sy.dao.Impl.FileDaoImpl;
import com.sy.entity.File;
import com.sy.entity.Job;
import com.sy.service.FileService;
import com.sy.util.Constants;
import com.sy.util.DBUtil;
import com.sy.util.Result;

import java.util.List;

public class FileServiceImpl implements FileService {
    private FileDao fileDao = new FileDaoImpl();
    @Override
    public Result findFile(File file, String pageNow, String pageSize) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int pn = Integer.parseInt(pageNow);
            int ps = Integer.parseInt(pageSize);
            List<File> files = fileDao.selectFile(file, (pn - 1) * ps, ps);
            long count = fileDao.selectCount(file);
            result.setData(files);
            result.setCount(count);
            result.setCode(Constants.CODE_SUCCESS);
            result.setMsg(Constants.MSG_SUCCESS);
            DBUtil.commitTx();
        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollbackTx();
            result.setCode(Constants.CODE_ERROR);
            result.setMsg("传输失败!");
        }
        return result;
    }

    @Override
    public Result removeFile(String[] array,String[] fileNames) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int sums = 0;
            for(String str : array){
                int i = Integer.parseInt(str);
                int sum = fileDao.deleteFile(i);
                if(sum == 1){
                    sums = sum;
                }
            }

            for (String str : fileNames){
                java.io.File file = new java.io.File("C:\\Users\\Administrator\\IdeaProjects\\PMSWeb\\web\\upload\\" + str);
                boolean delete = file.delete();
                if (delete){
                    System.out.println("文件集删除成功！");
                }else {
                    System.out.println("文件删除失败！");
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
    public Result removeFile(String id,String fileName) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            Integer i = Integer.parseInt(id);
            int sum = fileDao.deleteFile(i);
            java.io.File file = new java.io.File("C:\\Users\\Administrator\\IdeaProjects\\PMSWeb\\web\\upload\\" + fileName);
            boolean delete = file.delete();
            if (delete){
                System.out.println("文件删除成功！");
            }else {
                System.out.println("文件删除失败！");
            }
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
    public Result addFile(File file) {
        Result result = new Result();
        try {
            DBUtil.beginTx();
            int i = fileDao.insertFile(file);
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
