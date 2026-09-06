package com.study.core.interceptor;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TraceInterceptorConfig implements WebMvcConfigurer {

    @Resource
    private TraceInterceptor traceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceInterceptor)
                // 配置拦截器的拦截路径
                .addPathPatterns("/**")
                // 配置拦截器的排除路径（也就是这里配置的路径不会被拦截）
                .excludePathPatterns("/abcd")
                // 配置拦截器的顺序
                .order(1);
    }
}
