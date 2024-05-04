package com.qg.filter;

import com.alibaba.fastjson.JSONObject;
import com.qg.po.Result;
import com.qg.util.JwtUtils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LoginCheckFilter.class);

    public LoginCheckFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        // 获取请求头中的Authorization字段

        //设置响应头
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "*");
        ((HttpServletResponse) response).setHeader("Access-Control-Max-Age", "3600");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "*");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");

        // 3. jwt令牌
        String authHeader = ((HttpServletRequest) request).getHeader("Authorization");
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 提取JWT Token，去除Bearer前缀
            jwt = authHeader.substring(7);
        }
        System.out.println(jwt);




        //1.获取请求url。
        String url = req.getRequestURL().toString();
        System.out.println(url);
        log.info("请求的url: {}",url);

        //2.判断请求url中是否包含login，如果包含，说明是登录操作，放行。
        if(url.contains("login") || url.contains("Login") || url.contains("register") || url.contains("retrieve") || url.contains("blank")){
            log.info("登录操作, 放行...");
            chain.doFilter(request,response);
            return;
        }
        if(!url.contains("Servlet")){
            log.info("登录操作, 放行...");
            chain.doFilter(request,response);
            return;
        }

        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）。
        if(jwt == null || jwt.trim().isEmpty()){
            log.info("请求头token为空,返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            ((HttpServletResponse) response).sendRedirect("blank.html");

            return;
        }

        //5.解析token，如果解析失败，返回错误结果（未登录）。
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {//jwt解析失败
            e.printStackTrace();
            log.info("解析令牌失败, 返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            ((HttpServletResponse) response).sendRedirect("blank.html");
            return;
        }



        //6.放行。
        log.info("令牌合法, 放行");
        chain.doFilter(request, response);

    }
}
