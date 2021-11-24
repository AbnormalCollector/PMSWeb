package com.sy.dao.Impl;

import com.sy.dao.EmpDao;
import com.sy.entity.Emp;
import com.sy.entity.Job;
import com.sy.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class EmpDaoImpl implements EmpDao {
    @Override
    public List<Emp> selectEmp(Emp emp, int firstIndex, int pageSize) throws Exception {
        List<Emp> emps = null;
        if(emp == null){
            emps = DBUtil.executeQuery(Emp.class, "select * from emp limit ?,?", firstIndex, pageSize);
        }else {
            emps = DBUtil.executeQuery(Emp.class,
                    "SELECT * FROM emp WHERE name LIKE CONCAT('%',?,'%') and sex LIKE CONCAT('%',?,'%') and tel LIKE CONCAT('%',?,'%')" +
                            " and jid LIKE CONCAT('%',?,'%') and cardid LIKE CONCAT('%',?,'%') and did LIKE CONCAT('%',?,'%') limit ?,?",
                    emp.getName()==null?"":emp.getName(),
                    emp.getSex() == null?"":emp.getSex(),
                    emp.getTel() == null?"":emp.getTel(),
                    emp.getJid() == null?"":emp.getJid(),
                    emp.getCardid() == null?"":emp.getCardid(),
                    emp.getDid() == null?"":emp.getDid(),
                    firstIndex,pageSize);
        }
        return emps;
    }

    @Override
    public long selectCount(Emp emp) throws Exception {
        Connection connection = DBUtil.getConnection();
        PreparedStatement preparedStatement = null;
        if(emp == null || null == emp.getName()){
            preparedStatement = connection.prepareStatement("select count(id) from emp");
        }else {
            preparedStatement = connection.prepareStatement("select count(id) from emp where name LIKE CONCAT('%',?,'%') and sex LIKE CONCAT('%',?,'%') and tel LIKE CONCAT('%',?,'%')" +
                    " and jid LIKE CONCAT('%',?,'%') and cardid LIKE CONCAT('%',?,'%') and did LIKE CONCAT('%',?,'%')");
            preparedStatement.setString(1,emp.getName() == null?"":emp.getName());
            preparedStatement.setString(2,emp.getSex() == null?"":emp.getSex().toString());
            preparedStatement.setString(3,emp.getTel() == null?"":emp.getTel());
            preparedStatement.setString(4,emp.getJid() == null?"":emp.getJid().toString());
            preparedStatement.setString(5,emp.getCardid() == null?"":emp.getCardid());
            preparedStatement.setString(6,emp.getDid() == null?"":emp.getDid().toString());
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getLong(1);
        }
        return 0;
    }

    @Override
    public int deleteEmp(Integer id) throws Exception {
        int i = DBUtil.executeUpdate("delete from emp where id = ?", id);
        return i;
    }

    @Override
    public int updateEmp(Emp emp, String usedname) throws Exception {
        int i = DBUtil.executeUpdate("update emp set name=?,cardid=?,address=?,postCose=?,tel=?,qqNum=?,email=?,sex=?," +
                "party=?,birthday=?,rece=?,education=?,speciality=?,hobby=?,remark=? where name=?",
                emp.getName(),emp.getCardid(),emp.getAddress(),emp.getPostCose(),emp.getTel(),emp.getQqNum(),emp.getEmail(),emp.getSex(),
                emp.getParty(),emp.getBirthday(),emp.getRece(),emp.getEducation(),emp.getSpeciality(),emp.getHobby(),emp.getRemark(),usedname);
        return i;
    }

    @Override
    public int insertEmp(Emp emp) throws Exception {
        int i = DBUtil.executeUpdate("insert into emp(name,cardid,sex,jid,education,email," +
                "tel,party,address,birthday,did,remark) values(?,?,?,?,?,?,?,?,?,?,?,?)",
                emp.getName(),emp.getCardid(),emp.getSex(),emp.getJid(),emp.getEducation(),emp.getEmail() == null?"":emp.getEmail(),
                emp.getTel(),emp.getParty(),emp.getAddress(),emp.getBirthday(),emp.getDid(),emp.getRemark() == null?"":emp.getRemark());
        return i;
    }
}
