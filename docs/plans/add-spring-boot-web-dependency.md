# 添加 Spring Boot Web 依赖方案

## 背景

当前项目使用 `spring-boot-starter-parent:3.5.16` 管理 Spring Boot 依赖版本，`pom.xml` 的 `dependencies` 节点为空。

## 修改方案

在 `pom.xml` 的 `dependencies` 节点中添加：

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

依赖版本继续由 Spring Boot Parent 统一管理，不单独声明 `version`。

## 影响范围

- 引入 Spring MVC、内嵌 Tomcat、Jackson 及基础 Web 开发能力。
- 不修改现有 Java 源码、插件配置或其他依赖。

## 验证方式

修改后仅检查 `pom.xml` 的 XML 结构和 Maven 依赖解析；不运行完整项目构建。

