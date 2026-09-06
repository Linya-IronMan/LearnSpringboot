# Maven 与 Spring Boot 配置文件

## 1. `application.yml` 属于谁

`src/main/resources/application.yml` 是 Spring Boot 约定的应用配置文件，不是 Maven 配置文件。

Maven 和 Spring Boot 使用不同的配置文件，并承担不同职责：

| 文件 | 所属体系 | 主要作用 |
| --- | --- | --- |
| `pom.xml` | Maven | 管理项目依赖、插件、编译、测试和打包过程 |
| `application.yml` | Spring Boot | 配置端口、数据库、日志以及其他应用运行参数 |

可以简单记为：

> `pom.xml` 决定项目使用什么以及如何构建，`application.yml` 决定 Spring Boot 应用运行时如何工作。

## 2. 当前端口配置的含义

项目的 `application.yml` 包含以下内容：

```yaml
server:
  port: 8080
```

它表示让 Spring Boot 使用的内嵌 Web 服务器监听 `8080` 端口。在当前 Web 项目中，默认的内嵌服务器是 Tomcat。

应用启动后，可以通过以下地址访问服务：

```text
http://localhost:8080
```

## 3. Maven 在其中承担什么职责

`src/main/resources` 是 Maven 项目的约定资源目录。Maven 在处理资源或打包项目时，会将其中的文件复制到项目的类路径中：

```text
src/main/resources/application.yml
                ↓ Maven 处理资源或打包
target/classes/application.yml
                ↓
JAR 内部类路径根目录的 application.yml
```

Maven只负责处理和复制资源文件，不会理解或应用下面的配置含义：

```yaml
server:
  port: 8080
```

真正读取、解析并应用这些配置的是 Spring Boot。

## 4. Spring Boot 为什么能自动找到它

Spring Boot 默认识别以下应用配置文件名：

```text
application.properties
application.yaml
application.yml
```

因此，将 `application.yml` 放在 `src/main/resources` 中之后，通常不需要再编写代码告诉 Spring Boot 文件位置。

启动过程可以简化为：

```text
SpringApplication.run(...)
    ↓
Spring Boot 创建并准备应用环境
    ↓
从类路径等默认位置查找 application.yml
    ↓
解析 YAML 配置
    ↓
读取 server.port
    ↓
配置内嵌 Tomcat 监听 8080 端口
```

## 5. YAML 和 Properties 写法对照

Spring Boot 同时支持 YAML 和 Properties 格式。下面两种写法表达相同含义。

YAML 写法：

```yaml
server:
  port: 8080
```

Properties 写法：

```properties
server.port=8080
```

一个项目通常选择其中一种格式作为主要配置文件，以保持配置结构和维护方式一致。

## 6. 核心区别

完整关系可以概括为：

```text
pom.xml
  ↓ Maven 读取
决定依赖和构建方式

application.yml
  ↓ Spring Boot 读取
决定应用运行参数

src/main/resources
  ↓ Maven 按约定处理
将 application.yml 放入运行时类路径
```

因此，`src/main/resources` 是 Maven 约定的资源目录，而其中的 `application.yml` 是 Spring Boot 约定并解析的配置文件。

