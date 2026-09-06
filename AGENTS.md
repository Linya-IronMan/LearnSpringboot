# Java 文档注释规范

## Markdown 文档注释

本项目使用 JDK 23 及以上版本时，Java 类型和公开 API 的说明优先采用 `///` 形式的 Markdown 文档注释。

### 编写规则

- 每一行文档内容都以 `///` 开头。
- 空行也保留 `///`，用于分隔段落。
- 可以使用 Markdown 标题、列表、编号列表、行内代码和代码块。
- 类、接口、注解、公开方法以及需要对外说明的字段，都应在对应代码前添加文档注释。
- 自定义注解的文档应说明用途、适用范围、保留策略、属性含义和使用示例。
- 同一类上的多个注解说明必须合并为一个连续的 `///` 文档块，并放在第一个注解之前；不要在注解之间拆分文档块，否则 IDE 悬停文档可能只显示第一段。
- 不要将面向开发者的 API 说明写成普通 `//` 注释。

### 示例

```java
/// # Spring Boot 应用启动类
///
/// `Application` 是 Spring Boot 应用的入口类。
/// `@SpringBootApplication` 是以下注解的组合：
///
/// - `@SpringBootConfiguration`：声明应用配置类；
/// - `@EnableAutoConfiguration`：根据项目依赖启用自动配置；
/// - `@ComponentScan`：扫描并注册 Spring 组件。
///
/// ## 启动方式
///
/// `SpringApplication.run(Application.class)` 会创建并启动 Spring 应用上下文。
@SpringBootApplication
public class Application {
}
```

### 兼容性

- `///` Markdown 文档注释需要 JDK 23+ 及支持该语法的 IDE 或 Javadoc 工具。
- 如果项目必须兼容 JDK 22 或更低版本，改用传统 `/** ... */` JavaDoc，并使用 HTML 标签（如 `<p>`、`<ul>`、`{@code ...}`）。
- 如果 IntelliJ IDEA 悬停文档仍显示 Markdown 原文，先确认项目 SDK、语言级别和 IDE 版本；不要因此把文档改成普通注释。
