package com.study.annotation;

import com.study.api.TestController;
import com.study.core.annotation.ApiMethod;

import java.lang.reflect.Method;

public class ApiMethodTest {

    public static void main(String[] args) {
        // 通过反射，获取到TestController类中所有的方法
        Method[] methods = TestController.class.getDeclaredMethods();

        // 遍历方法列表
        for (Method method : methods) {
            // 获取方法上的@ApiMethod注解
            ApiMethod apiMethod = method.getAnnotation(ApiMethod.class);

            // 获取注解信息
            System.out.println(apiMethod.name());
            System.out.println(apiMethod.description());
        }
    }
}
