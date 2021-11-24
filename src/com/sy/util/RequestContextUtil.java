package com.sy.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName RequestContextUtil
 * @Description 生成请求和相应的工具类
 * @Author Administrator
 * @Date: 2021/11/12 14:05
 * @Version 1.0
 */
public class RequestContextUtil {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public RequestContextUtil(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response =response;
    }

    private static RequestContextUtil getContext(){
        RequestContextUtil ctx = RequestContextFilter.THREAD_LOCAL.get();
        return ctx;
    }

    public static HttpServletRequest getRequest(){
        RequestContextUtil ctx = getContext();
        return ctx.request;
    }

    public static HttpServletResponse getResponse(){
        RequestContextUtil ctx = getContext();
        return ctx.response;
    }
}
