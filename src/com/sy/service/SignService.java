package com.sy.service;

import com.sy.entity.Sign;
import com.sy.util.Result;

public interface SignService {
    Result findByLogin(String username);

    Result addSign(Sign sign);
    Result findSign(String start);
    Result findSign(String start,String end, String pageNow, String pageSize);
}
