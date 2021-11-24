package com.sy.dao.Impl;

import com.sy.dao.DepartmentDao;
import com.sy.entity.Department;
import com.sy.entity.User;
import com.sy.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao {
    @Override
    public List<Department> selectDepartment(Department department, int firstIndex, int pageSize) throws Exception {
        List<Department> departments = null;
        if(department == null || null == department.getName()){
            departments = DBUtil.executeQuery(Department.class, "select * from department limit ?,?", firstIndex, pageSize);
        }else {
            departments = DBUtil.executeQuery(Department.class,
                    "SELECT * FROM department WHERE name LIKE CONCAT('%',?,'%') limit ?,?",department.getName(),firstIndex,pageSize );
        }
        return departments;
    }

    @Override
    public long selectCount(Department department) throws Exception {
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        if(department == null || null == department.getName()){
            preparedStatement = connection.prepareStatement("select count(id) from department");
        }else {
            preparedStatement = connection.prepareStatement("select count(id) from department where name LIKE CONCAT('%',?,'%')");
            preparedStatement.setString(1,department.getName());
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getLong(1);
        }
        return 0;
    }

    @Override
    public int deleteDepartment(Integer id) throws Exception {
        int i = DBUtil.executeUpdate("delete from department where id = ?", id);
        return i;
    }

    @Override
    public int updateDepartment(Department department, String usedname) throws Exception {
        int i = DBUtil.executeUpdate("update department set name=?,remark=? where name=?",department.getName(),department.getRemark(),usedname);
        return i;
    }

    @Override
    public int insertDepartment(Department department) throws Exception {
        int i = DBUtil.executeUpdate("insert into department(name,remark) values(?,?)",department.getName(),department.getRemark());
        return i;
    }
}
