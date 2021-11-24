package com.sy.service;

import com.sy.entity.Job;
import com.sy.entity.Notice;
import com.sy.util.Result;

public interface NoticeService {

    Result findNotice(Notice notice, String pageNow, String pageSize);

    Result removeNotice(String[] array);

    Result removeNotice(String id);

    Result modifyNotice(Notice notice, String usedname);

    Result addNotice(Notice notice);

}
