package com.sy.service;

import com.sy.entity.File;
import com.sy.entity.Job;
import com.sy.util.Result;

public interface FileService {
    Result findFile(File file, String pageNow, String pageSize);

    Result removeFile(String[] array,String[] fileNames);

    Result removeFile(String id,String fileName);

    Result addFile(File file);
}
