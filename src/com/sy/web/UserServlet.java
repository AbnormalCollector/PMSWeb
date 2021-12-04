package com.sy.web;

import com.alibaba.fastjson.JSON;
import com.sy.entity.User;
import com.sy.service.Impl.UserServiceImpl;
import com.sy.service.UserService;
import com.sy.util.ImgCodeUtil;
import com.sy.util.RequestContextUtil;
import com.sy.util.RequestMapping;
import com.sy.util.Result;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

@RequestMapping("/user")
public class UserServlet {
    private UserService service = new UserServiceImpl();
    private HttpServletRequest request = RequestContextUtil.getRequest();
    private HttpServletResponse response = RequestContextUtil.getResponse();

    @RequestMapping("/login.do")
    public void loginUser() throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");
        Cookie[] cookies = request.getCookies();
        String code = "";
        String value = "";
        if (cookies!=null) {
            Iterator<Cookie> iterator = Arrays.stream(cookies).iterator();
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = iterator.next();
                if (cookie.getName().equals("UUID")) {
                    value = cookie.getValue();
                    code = (String) request.getServletContext().getAttribute(value);
                    break;
                }
            }
        }
        if (!"".equals(code) && captcha.equalsIgnoreCase(code)) {
            Result byLogin = service.findByLogin(username, password);
            response.getWriter().write(JSON.toJSONString(byLogin));
        } else {
            Result result = new Result();
            result.setMsg("验证码错误！");
            response.getWriter().write(JSON.toJSONString(result));
        }
        request.getServletContext().removeAttribute(value);
    }

    @RequestMapping("/code.do")
    public void codeUser() throws Exception {
        ImgCodeUtil imgCodeUtil = new ImgCodeUtil();
        BufferedImage image = imgCodeUtil.getImage();
        String text = imgCodeUtil.getText();
        String uuid = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("UUID", uuid);
        cookie.setPath("/");
        response.addCookie(cookie);
        imgCodeUtil.output(image, response.getOutputStream());
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute(uuid, text);
    }

    @RequestMapping("/list.do")
    public void UserList() throws Exception {
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String username = request.getParameter("username");
        String status = request.getParameter("status");
        String pageNow = request.getParameter("page");
        String pageSize = request.getParameter("limit");
        User user = new User();
        user.setUsername(username);
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName());
        if (status != null && !"".equals(status)) {
            user.setStatus( Integer.parseInt(status));
        }
        Result result = service.findUser(user, pageNow, pageSize);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();

    }

    @RequestMapping("/removes.do")
    public void removeUsers() throws Exception {
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String[] parameter = request.getParameterValues("newsId[]");
        Result result = service.removeUser(parameter);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    @RequestMapping("/remove.do")
    public void removeUser() throws Exception {
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String parameter = request.getParameter("newsId");
        Result result = service.removeUser(parameter);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    @RequestMapping("/modify.do")
    public void modifyUser() throws Exception {
        //request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String usedname = request.getParameter("usedname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String loginname = request.getParameter("loginname");
        String status = request.getParameter("status");
        User user = new User();
        user.setUsername(username);
        user.setLoginname(loginname);
        user.setPassword(password);
        user.setStatus(Integer.parseInt(status));
        Result result = service.modifyUser(user, usedname);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    @RequestMapping("/status.do")
    public void statusUser() throws Exception {
        String username = request.getParameter("username");
        String status = request.getParameter("status");
        User user = new User();
        user.setUsername(username);
        user.setStatus(Integer.parseInt(status));
        Result result = service.modifyUser(user);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    @RequestMapping("/add.do")
    public void addUser() throws Exception {
//        request.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String loginname = request.getParameter("loginname");
        String status = request.getParameter("status");
        User user = new User();
        user.setUsername(username);
        user.setLoginname(loginname);
        user.setPassword(password);
        user.setStatus(Integer.parseInt(status));
        Result result = service.addUser(user);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }
}
