package com.sy.dao;

import com.sy.entity.Department;
import com.sy.entity.User;

import java.util.List;

public interface DepartmentDao {
    List<Department> selectDepartment(Department department, int firstIndex, int pageSize)throws Exception;
    long selectCount(Department department)throws Exception;
    int deleteDepartment(Integer id) throws Exception;
    int updateDepartment(Department department,String usedname)throws Exception;
    int insertDepartment(Department department) throws Exception;
}
