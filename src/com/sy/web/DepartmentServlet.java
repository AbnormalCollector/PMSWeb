package com.sy.web;

import com.alibaba.fastjson.JSON;
import com.sy.entity.Department;
import com.sy.entity.User;
import com.sy.service.DepartmentService;
import com.sy.service.Impl.DepartmentServiceImpl;
import com.sy.util.RequestContextUtil;
import com.sy.util.RequestMapping;
import com.sy.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;

@RequestMapping("/department")
public class DepartmentServlet {
    private DepartmentService service = new DepartmentServiceImpl();
    private HttpServletRequest request = RequestContextUtil.getRequest();
    private HttpServletResponse response = RequestContextUtil.getResponse();
    @RequestMapping("/list.do")
    public void departmentList()throws Exception{
        String name = request.getParameter("name");
        String pageNow = request.getParameter("page");
        String pageSize = request.getParameter("limit");
        Department department = new Department();
        department.setName(name);
        Result result = service.findDepartment(department, pageNow, pageSize);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    @RequestMapping("/removes.do")
    public void removeDepartments()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String[] parameter = request.getParameterValues("newsId[]");
        Result result = service.removeDepartment(parameter);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }


    @RequestMapping("/remove.do")
    public void removeDepartment()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String parameter = request.getParameter("newsId");
        Result result = service.removeDepartment(parameter);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
    @RequestMapping("/modify.do")
    public void modifyDepartment()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String usedname = request.getParameter("usedname");
        String remark = request.getParameter("remark");
        String name = request.getParameter("name");
        Department department = new Department();
        department.setName(name);
        department.setRemark(remark);
        Result result = service.modifyDepartment(department,usedname);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
    @RequestMapping("/add.do")
    public void addDepartment()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        Department department = new Department();
        department.setName(name);
        department.setRemark(remark);
        Result result = service.addDepartment(department);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

}
