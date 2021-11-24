package com.sy.web;

import com.alibaba.fastjson.JSON;
import com.sy.entity.Notice;
import com.sy.service.Impl.NoticeServiceImpl;
import com.sy.service.NoticeService;
import com.sy.util.RequestContextUtil;
import com.sy.util.RequestMapping;
import com.sy.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;

@RequestMapping("/notice")
public class NoticeServlet {
    private NoticeService service = new NoticeServiceImpl();
    private HttpServletRequest request = RequestContextUtil.getRequest();
    private HttpServletResponse response = RequestContextUtil.getResponse();


    @RequestMapping("/list.do")
    public void noticeList()throws Exception{
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String pageNow = request.getParameter("page");
        String pageSize = request.getParameter("limit");
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        Result result = service.findNotice(notice, pageNow, pageSize);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }


    @RequestMapping("/removes.do")
    public void removeNotices()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String[] parameter = request.getParameterValues("newsId[]");
        Result result = service.removeNotice(parameter);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }


    @RequestMapping("/remove.do")
    public void removeNotice()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String parameter = request.getParameter("newsId");
        Result result = service.removeNotice(parameter);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }


    @RequestMapping("/modify.do")
    public void modifyNotice()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String usedname = request.getParameter("usedname");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        Result result = service.modifyNotice(notice,usedname);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    @RequestMapping("/add.do")
    public void addNotice()throws Exception{
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String uid = request.getParameter("uid");
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        if(uid != null){
            notice.setUid(Integer.parseInt(uid));
        }
        Result result = service.addNotice(notice);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
}
