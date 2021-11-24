package com.sy.util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName RequestContextFilter
 * @Description 帮助生成请求和响应的工具类和RequestContextUtil搭配
 * @Author Administrator
 * @Date: 2021/11/12 14:08
 * @Version 1.0
 */
@WebFilter("/*")
public class RequestContextFilter implements Filter {
    public static final ThreadLocal<RequestContextUtil> THREAD_LOCAL = new ThreadLocal<>();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        RequestContextUtil requestContextUtil = new RequestContextUtil(request, response);
        THREAD_LOCAL.set(requestContextUtil);
        filterChain.doFilter(servletRequest, servletResponse);

    }
    @Override
    public void destroy() {

    }
}
