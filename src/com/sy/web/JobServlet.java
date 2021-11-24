package com.sy.web;

import com.alibaba.fastjson.JSON;
import com.sy.entity.Department;
import com.sy.entity.Job;
import com.sy.service.DepartmentService;
import com.sy.service.Impl.DepartmentServiceImpl;
import com.sy.service.Impl.JobServiceImpl;
import com.sy.service.JobService;
import com.sy.util.RequestContextUtil;
import com.sy.util.RequestMapping;
import com.sy.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RequestMapping("/job")
public class JobServlet {
    private JobService service = new JobServiceImpl();
    private HttpServletRequest request = RequestContextUtil.getRequest();
    private HttpServletResponse response = RequestContextUtil.getResponse();
    @RequestMapping("/list.do")
    public void jobList()throws Exception{
        String name = request.getParameter("name");
        String pageNow = request.getParameter("page");
        String pageSize = request.getParameter("limit");
        Job job = new Job();
        job.setName(name);
        Result result = service.findJob(job, pageNow, pageSize);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    @RequestMapping("/removes.do")
    public void removeJobs()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String[] parameter = request.getParameterValues("newsId[]");
        Result result = service.removeJob(parameter);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }


    @RequestMapping("/remove.do")
    public void removeJob()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String parameter = request.getParameter("newsId");
        Result result = service.removeJob(parameter);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
    @RequestMapping("/modify.do")
    public void modifyJob()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String usedname = request.getParameter("usedname");
        String remark = request.getParameter("remark");
        String name = request.getParameter("name");
        Job job = new Job();
        job.setName(name);
        job.setRemark(remark);
        Result result = service.modifyJob(job,usedname);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
    @RequestMapping("/add.do")
    public void addJob()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        Job job = new Job();
        job.setName(name);
        job.setRemark(remark);
        Result result = service.addJob(job);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
}
