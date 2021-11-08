package com.gk.zuul.Filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author PreFilter
 * @version 1.0
 * @date 2021/4/6 0:10
 */
@Component
@Slf4j
public class PreFilter extends ZuulFilter {
/*
“pre” 预过滤器 - 在路由分发一个请求之前调用。
“post” 后过滤器 - 在路由分发一个请求后调用。
“route” 路由过滤器 - 用于路由请求分发。
“error” 错误过滤器 - 在处理请求时发生错误时调用 */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.out.println("Pre Filter: Request Method : " + request.getMethod() + " " + "Request URL : " + request.getRequestURL().toString());
        return false;
    }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        System.out.println("Pre Filter: Request Method : " + request.getMethod() + " " + "Request URL : " + request.getRequestURL().toString());

        return null;
    }
}