package com.sy.service;

import com.sy.entity.Department;
import com.sy.util.Result;

public interface DepartmentService {

    Result findDepartment(Department department, String pageNow, String pageSize);

    Result removeDepartment(String[] array);

    Result removeDepartment(String id);

    Result modifyDepartment(Department department, String usedname);

    Result addDepartment(Department department);
}
