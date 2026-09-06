package com.study.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect // 标记为切面类。准确来说，标记为该类为一个切面配置类，因为这个类可以配置多个切点，产生多个切面
@Order(2) // 标记切面排序
@Component // 标记该类实例加入到Spring上下文中（IoC容器中）
public class LogAspect {

    // 前置处理通知
    @Before("execution(public * com.study.service.*.*(..))")
    public void before (JoinPoint point) {
        // 目标实例
        Object target = point.getTarget();
        // 获取方法参数
        Object[] args = point.getArgs();
        System.out.println("service执行前");
    }

    // 后置处理通知
    @After("execution(public * com.study.service.*.*(..))")
    public void after (JoinPoint point) {
        Object target = point.getTarget();
        Object[] args = point.getArgs();
        System.out.println("service执行后");
    }

    // 环绕通知，可以在方法执行前后搞点儿事儿，只要你愿意，你可以篡改参数和返回值
    @Around("execution(public * com.study.service.*.*(..))")
    public Object around (ProceedingJoinPoint point) {
        try {
            Object target = point.getTarget();
            Object[] args = point.getArgs();
            System.out.println("around before");
            Object result = point.proceed();
            System.out.println("around after");
            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    // 异常通知
    @AfterThrowing(
            value = "execution(public * com.study.service.*.*(..))",
            throwing = "ex"
    )
    public void afterThrowing(JoinPoint point, Exception ex) {
        Object[] args = point.getArgs();
        System.out.println("service出现异常：" + ex.getMessage());
    }
}
