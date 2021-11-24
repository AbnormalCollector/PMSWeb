package com.sy.web;

import com.alibaba.fastjson.JSON;
import com.sy.entity.Emp;
import com.sy.entity.Job;
import com.sy.service.EmpService;
import com.sy.service.Impl.EmpServiceImpl;
import com.sy.service.Impl.JobServiceImpl;
import com.sy.service.JobService;
import com.sy.util.RequestContextUtil;
import com.sy.util.RequestMapping;
import com.sy.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@RequestMapping("/emp")
public class EmpServlet {

    private EmpService service = new EmpServiceImpl();
    private HttpServletRequest request = RequestContextUtil.getRequest();
    private HttpServletResponse response = RequestContextUtil.getResponse();
    @RequestMapping("/list.do")
    public void EmpList()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String name = request.getParameter("name");
        String tel = request.getParameter("tel");
        String sex = request.getParameter("sex");
        String job = request.getParameter("job");
        String cardid = request.getParameter("cardid");
        String De = request.getParameter("De");
        String pageNow = request.getParameter("page");
        String pageSize = request.getParameter("limit");
        Emp emp = new Emp();
        emp.setName(name);
        emp.setTel(tel);
        if(sex != null){
            emp.setSex(Integer.parseInt(sex));
        }
        if(job != null){
            emp.setJid(Integer.parseInt(job));
        }
        emp.setCardid(cardid);
        if(De != null){
            emp.setDid(Integer.parseInt(De));
        }
        Result result = service.findEmp(emp, pageNow, pageSize);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    @RequestMapping("/removes.do")
    public void removeEmps()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String[] parameter = request.getParameterValues("newsId[]");
        Result result = service.removeEmp(parameter);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }


    @RequestMapping("/remove.do")
    public void removeEmp()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String parameter = request.getParameter("newsId");
        Result result = service.removeEmp(parameter);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
    @RequestMapping("/modify.do")
    public void modifyEmp()throws Exception{
//         request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String usedname = request.getParameter("usedname");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String tel = request.getParameter("tel");
        String email = request.getParameter("email");
        String jid = request.getParameter("jid");
        String education = request.getParameter("education");
        String cardid = request.getParameter("cardid");
        String did = request.getParameter("did");
        String address = request.getParameter("address");
        String ncreateDate = request.getParameter("createDate");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date createDate = format.parse(ncreateDate);
        Emp emp = new Emp();
        emp.setName(name);
        if(sex != null){
            emp.setSex(Integer.parseInt(sex));
        }
        emp.setTel(tel);
        emp.setEmail(email);
        if(jid != null){
            emp.setJid(Integer.parseInt(jid));
        }
        emp.setEducation(education);
        emp.setCardid(cardid);
        if(did != null){
            emp.setDid(Integer.parseInt(did));
        }
        emp.setAddress(address);
        emp.setCreateDate(createDate);
        Result result = service.modifyEmp(emp,usedname);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
    @RequestMapping("/add.do")
    public void addEmp()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String name = request.getParameter("name");
        String cardid = request.getParameter("cardid");
        String sex = request.getParameter("sex");
        String job = request.getParameter("job");
        String education = request.getParameter("education");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        String party = request.getParameter("party");
        String address = request.getParameter("address");
        String nbirthday = request.getParameter("birthday");
        String department = request.getParameter("department");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = format.parse(nbirthday);
        String remark = request.getParameter("remark");
        Emp emp = new Emp();
        emp.setName(name);
        emp.setCardid(cardid);
        if(sex != null){
            emp.setSex(Integer.parseInt(sex));
        }
        if (job != null){
            emp.setJid(Integer.parseInt(job));
        }
        emp.setEducation(education);
        emp.setEmail(email);
        emp.setTel(tel);
        emp.setParty(party);
        emp.setAddress(address);
        if(department != null){
            emp.setDid(Integer.parseInt(department));
        }
        emp.setBirthday(birthday);
        emp.setRemark(remark);
        Result result = service.addEmp(emp);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
}
