package com.study.core.annotation;

import java.lang.annotation.*;

/// ## 接口方法说明注解
///
/// 用于为接口方法补充名称和描述信息，便于：
///
/// - 生成接口文档；
/// - 记录接口日志；
/// - 通过反射读取接口元数据。
///
/// 该注解只能作用于方法，并且会保留到运行时：
///
/// - `@Target(ElementType.METHOD)`：限制使用位置为方法；
/// - `@Retention(RetentionPolicy.RUNTIME)`：运行时可以通过反射读取；
/// - `@Documented`：使用该注解的内容会出现在 Javadoc 中。
///
/// 示例：
///
/// ```java
/// @ApiMethod(
///     name = "查询用户",
///     description = "根据用户 ID 查询用户信息"
/// )
/// public User getUser(Long id) {
///     // ...
/// }
/// ```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiMethod {
    /// 接口名称。
    ///
    /// 例如：`查询用户`。
    ///
    /// @return 接口名称；未指定时返回空字符串
    String name() default "";

    /// 接口描述。
    ///
    /// 例如：`根据用户 ID 查询用户信息`。
    ///
    /// @return 接口描述；未指定时返回空字符串
    String description() default "";
}
