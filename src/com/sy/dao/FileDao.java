package com.sy.dao;

import com.sy.entity.File;
import com.sy.entity.Job;

import java.util.List;

public interface FileDao {

    List<File> selectFile(File file, int firstIndex, int pageSize)throws Exception;
    long selectCount(File file)throws Exception;
    int deleteFile(Integer id) throws Exception;
    int insertFile(File file) throws Exception;
}
