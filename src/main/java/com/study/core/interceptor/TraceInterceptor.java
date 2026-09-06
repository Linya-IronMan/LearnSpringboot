package com.study.core.interceptor;

import com.study.core.annotation.ApiMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TraceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 在controller执行之前做点儿什么
        System.out.println("controller即将执行");

        // Knife4j、Swagger UI 等静态资源请求使用 ResourceHttpRequestHandler，
        // 不是 HandlerMethod，不能直接强制类型转换。
        // NOTE: `handler instanceof HandlerMethod method` 是 instanceof 模式匹配语法：
        // 先判断类型，判断成功后自动完成类型转换并绑定到 method，等价于：
        //
        // if (handler instanceof HandlerMethod) {
        //     HandlerMethod method = (HandlerMethod) handler;
        // }
        if (handler instanceof HandlerMethod method) {
            // 获取 controller 方法上的自定义注解
            ApiMethod apiMethod = method.getMethodAnnotation(ApiMethod.class);
        }
        // 返回true，代表着可以继续执行下一个拦截器和controller方法
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 在controller方法执行完成后，做点儿什么（如果存在异常不会执行）
        System.out.println("controller成功执行（有异常不执行）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        // 在controller方法执行完成后，做点儿什么（有无异常都会执行）
        System.out.println("controller执行完毕");
    }
}
