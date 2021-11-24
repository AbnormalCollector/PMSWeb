package com.sy.service;

import com.sy.entity.Emp;
import com.sy.entity.Job;
import com.sy.util.Result;

public interface EmpService {
    Result findEmp(Emp emp, String pageNow, String pageSize);

    Result removeEmp(String[] array);

    Result removeEmp(String id);

    Result modifyEmp(Emp emp, String usedname);

    Result addEmp(Emp emp);
}
