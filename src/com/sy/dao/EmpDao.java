package com.sy.dao;

import com.sy.entity.Emp;
import com.sy.entity.Job;

import java.util.List;

public interface EmpDao {
    List<Emp> selectEmp(Emp emp, int firstIndex, int pageSize)throws Exception;
    long selectCount(Emp emp)throws Exception;
    int deleteEmp(Integer id) throws Exception;
    int updateEmp(Emp emp,String usedname)throws Exception;
    int insertEmp(Emp emp) throws Exception;
}
