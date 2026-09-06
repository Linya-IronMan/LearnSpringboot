# 添加 Thymeleaf 依赖方案

## 背景

当前项目已经引入 `spring-boot-starter-web`，但尚未引入服务端模板引擎。项目使用 `spring-boot-starter-parent:3.5.16` 统一管理 Spring Boot 相关依赖版本。

## 修改方案

在 `pom.xml` 的 `dependencies` 节点中添加：

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

不单独声明 `version`，继续由 Spring Boot Parent 管理兼容版本。

## 影响范围

- 引入 Thymeleaf 模板引擎及 Spring MVC 集成能力。
- Spring Boot 默认从 `src/main/resources/templates` 查找页面模板。
- 不创建模板页面，不修改 Controller、配置文件或现有插件。

## 验证方式

- 检查 `pom.xml` 的 XML 结构。
- 使用 Maven 解析 `spring-boot-starter-thymeleaf` 依赖。
- 不运行完整项目构建。

