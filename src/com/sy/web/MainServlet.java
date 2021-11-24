package com.sy.web;

import com.alibaba.fastjson.JSON;
import com.sy.entity.Sign;
import com.sy.service.Impl.SignServiceImpl;
import com.sy.service.SignService;
import com.sy.util.RequestContextUtil;
import com.sy.util.RequestMapping;
import com.sy.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@RequestMapping("/main")
public class MainServlet {
    private SignService service = new SignServiceImpl();
    private HttpServletRequest request = RequestContextUtil.getRequest();
    private HttpServletResponse response = RequestContextUtil.getResponse();

    @RequestMapping("/show.do")
    public void getUser() throws Exception {
        String username = request.getParameter("username");
        Result result = service.findByLogin(username);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    @RequestMapping("/add.do")
    public void addFlag() throws Exception {
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String uid = request.getParameter("uid");
        long l = System.currentTimeMillis();
        Date Date = new Date(l);
        Sign sign = new Sign();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (uid != null) {
            sign.setUid(Integer.parseInt(uid));
        }
        String form = format.format(Date);
        sign.setCreatetime(format.parse(form));
        Result result = service.addSign(sign);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();

    }

    @RequestMapping("/List.do")
    public void getSign() throws Exception {
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String pageNow = request.getParameter("page");
        String pageSize = request.getParameter("limit");
        Result result = service.findSign(start, end, pageNow, pageSize);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
    @RequestMapping("/charts.do")
    public void getChart() throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String start = request.getParameter("start");
        Result result = service.findSign(start);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
}
