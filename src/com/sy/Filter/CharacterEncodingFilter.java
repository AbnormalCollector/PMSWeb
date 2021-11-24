package com.sy.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @ClassName CharacterEncodingFilter
 * @Description 过滤编码集使用加(.do),表示只过滤Servlet而不过滤html,注:过滤html是指前端页面写在服务器里，没有前后端分离，分离类可以不用写
 * @Author Administrator
 * @Date: 2021/11/15 10:56
 * @Version 1.0
 */
@WebFilter("*.do")
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("text/html;charset=utf-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
