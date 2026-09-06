package com.study.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/// # Knife4j / OpenAPI 文档配置
///
/// 为 Springdoc OpenAPI 和 Knife4j 注册全局的 OpenAPI 元数据。
///
/// 该配置会被 Spring 自动发现，`customOpenAPI` 返回的 `OpenAPI` Bean
/// 会用于生成接口文档的标题、版本和描述信息。
///
/// Spring 处理 `@Configuration` 的大致流程如下：
///
/// 1. 启动类上的 `@SpringBootApplication` 触发组件扫描，扫描到当前配置类；
/// 2. Spring 将 `Knife4jConfig` 注册为配置类的 Bean 定义；
/// 3. `ConfigurationClassPostProcessor` 解析类上的 `@Bean` 方法；
/// 4. Spring 为 `customOpenAPI()` 注册一个 `OpenAPI` Bean 定义；
/// 5. 应用上下文刷新时调用该方法，创建并管理一个单例 `OpenAPI` 实例；
/// 6. Springdoc OpenAPI 和 Knife4j 从容器中获取这个实例，并使用其中的文档元数据生成接口文档。
///
/// `@Configuration` 本身是一个特殊的组件注解，因此不需要手动实例化该类。
/// 默认情况下，Spring 还会增强配置类，以保证配置类内部通过方法调用获取的 `@Bean`
/// 仍然遵循容器的单例管理规则。
@Configuration
public class Knife4jConfig {

    /// 创建并注册 OpenAPI 文档对象。
    ///
    /// 文档页面会读取该对象中的基础信息：
    ///
    /// - `title`：接口文档标题；
    /// - `version`：当前接口版本；
    /// - `description`：接口文档说明。
    ///
    /// @return 配置完成的 OpenAPI 文档对象
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Study项目接口文档")
                        .version("1.0.0")
                        .description("提供...系统接口"));
    }
}
