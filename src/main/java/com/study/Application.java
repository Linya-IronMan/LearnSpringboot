package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/// # Spring Boot 应用启动类
///
/// `Application` 是 Spring Boot 应用的入口类。类上的
/// `@SpringBootApplication` 是一个组合注解，通常等价于同时使用以下三个注解：
///
/// - `@SpringBootConfiguration`：标记当前类是 Spring Boot 的配置类，
///   本质上是对 `@Configuration` 的专用声明；
/// - `@EnableAutoConfiguration`：开启自动配置，Spring Boot 会根据项目依赖、
///   配置文件和当前环境自动创建并配置相关组件；
/// - `@ComponentScan`：扫描当前包及其子包中的组件，例如 `@Controller`、
///   `@Service`、`@Repository` 和 `@Component`，并将它们注册到 Spring 容器中。
///
/// 因此，`@SpringBootApplication` 可以帮助应用完成：
///
/// 1. 声明应用配置类；
/// 2. 启用 Spring Boot 自动配置；
/// 3. 扫描并注册 Spring 组件。
///
/// ## 启动方式
///
/// `main` 方法调用 `SpringApplication.run(...)`：
///
/// - 创建并启动 Spring 应用上下文；
/// - 加载配置并执行自动配置；
/// - 启动内嵌 Web 服务器（Web 项目中）；
/// - 注册并初始化扫描到的 Spring Bean。
@SpringBootApplication
public class Application
{
    public static void main( String[] args ) {
        SpringApplication.run(Application.class);
    }
}
