package com.study.core.filter;

import com.study.core.model.ApiResponse;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/// # AuthFilter 注解说明
///
/// 该过滤器通过以下注解完成注册和执行顺序配置：
///
/// - `@Order(1)`：设置过滤器的执行顺序。数值越小，优先级越高；`1` 表示该过滤器会较早执行。
/// - `@Component`：将过滤器注册为 Spring Bean，使其可以交由 Spring 容器创建和管理。
/// - `@WebFilter(urlPatterns = "/*")`：将过滤器注册到 Servlet 容器，并匹配所有请求路径。
///
/// 在 Spring Boot 中使用 `@WebFilter` 时，通常还需要启用 `@ServletComponentScan`。
@Order(1)
@Component
@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 从请求头中获取token（或者从cookie里获取token）
        String token = request.getHeader("x-user-token");
//        if (token == null) {
//            HttpServletResponse response = (HttpServletResponse) servletResponse;
//            ApiResponse.response(response, ApiResponse.failed(401, "未登录"));
//            return;
//        }
        // 根据token查询用户信息，查不到，一样直接返回未登录，否则就放行
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
