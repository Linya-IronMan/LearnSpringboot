# Spring Boot Web Controller 加载与请求映射

## 1. `TestController` 为什么会被自动加载

项目的启动类位于 `com.study` 包中：

```java
package com.study;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

`@SpringBootApplication` 是一个组合注解，包含以下核心能力：

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
```

其中，`@ComponentScan` 负责扫描 Spring 组件。未显式指定扫描范围时，它会从启动类所在的包开始，扫描该包及其所有子包。

当前包结构如下：

```text
com.study
├── Application
└── api
    └── TestController
```

`TestController` 位于 `com.study.api`，属于 `com.study` 的子包，并且带有 `@RestController` 注解，因此会被组件扫描发现并注册为 Spring Bean。

完整加载过程为：

```text
Application.main()
    ↓
SpringApplication.run(Application.class, args)
    ↓
@SpringBootApplication 开启组件扫描和自动配置
    ↓
扫描 com.study 及其子包
    ↓
发现带有 @RestController 的 TestController
    ↓
创建 TestController 实例并注册到 Spring 容器
    ↓
Spring MVC 注册控制器中的请求映射
```

如果将 `TestController` 移动到 `com.study` 之外的包，例如 `org.example.api`，默认组件扫描将无法发现它。此时需要调整包结构或显式配置扫描范围。

## 2. `@RestController` 的作用

`@RestController` 表示当前类是一个 REST 接口控制器：

```java
@RestController
public class TestController {
}
```

它相当于同时使用：

```java
@Controller
@ResponseBody
```

主要作用包括：

1. 将类标记为 Spring 组件，使其能够被组件扫描并注册为 Spring Bean。
2. 将方法返回值直接写入 HTTP 响应体，而不是把返回值解释为页面模板名称。

返回字符串时：

```java
public String test() {
  return "hello";
}
```

HTTP 响应体就是：

```text
hello
```

返回 Java 对象时，Spring 通常会使用 Jackson 将对象序列化为 JSON。

## 3. `@RequestMapping("/test")` 的作用

`@RequestMapping` 用于声明请求路径，可以放在类或方法上：

```java
@RequestMapping("/test")
public class TestController {
}
```

放在类上时，它定义该控制器中所有接口的公共路径前缀。这个控制器里的方法路径都会在 `/test` 的基础上继续拼接。

`@RequestMapping` 还可以限制 HTTP 请求方法、请求参数、请求头和媒体类型。对于明确的请求方法，通常优先使用 `@GetMapping`、`@PostMapping` 等更具体的注解。

## 4. `@GetMapping("/hello")` 的作用

`@GetMapping` 用于声明只接受 HTTP `GET` 请求的方法：

```java
@GetMapping("/hello")
public String test() {
  return "hello";
}
```

它是以下写法的简化形式：

```java
@RequestMapping(value = "/hello", method = RequestMethod.GET)
```

当客户端使用 `GET` 请求访问对应路径时，Spring MVC 会调用该方法。若使用 `POST` 等其他请求方法访问，通常会收到 `405 Method Not Allowed` 响应。

## 5. 三个注解组合后的结果

完整控制器如下：

```java
@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping("/hello")
  public String test() {
    return "hello";
  }
}
```

类路径和方法路径会进行拼接：

```text
/test + /hello = /test/hello
```

最终生成的接口为：

```http
GET http://localhost:8080/test/hello
```

响应体为：

```text
hello
```

## 6. 请求处理过程

请求进入应用后的简化流程如下：

```text
GET /test/hello
    ↓
内嵌 Tomcat 接收请求
    ↓
DispatcherServlet 分发请求
    ↓
RequestMappingHandlerMapping 查找匹配方法
    ↓
调用 TestController.test()
    ↓
@RestController 将返回值写入响应体
    ↓
客户端收到 hello
```

## 7. 为什么注解都以 `Mapping` 结尾

这里的 `Mapping` 不是“地图”，而是程序设计中的“映射关系”，表示将某种 HTTP 请求关联到某个 Java 处理方法：

```text
HTTP 请求条件 → Java Controller 方法
```

例如：

```java
@GetMapping("/hello")
public String hello() {
  return "hello";
}
```

它建立的映射关系是：

```text
GET /hello → hello()
```

因此，`GetMapping` 可以理解为“GET 请求的映射规则”。这些注解的核心作用不是发出请求或执行 GET，而是登记“什么请求应该交给哪个 Java 方法处理”。

### 7.1 `RequestMapping` 是通用映射

`@RequestMapping` 可以描述不同类型的 HTTP 请求：

```java
@RequestMapping(path = "/hello", method = RequestMethod.GET)
public String hello() {
  return "hello";
}
```

除了请求路径和 HTTP 方法，它还可以根据以下条件匹配请求：

- 请求参数；
- 请求头；
- 请求体的媒体类型，即 `Content-Type`；
- 客户端希望接收的媒体类型。

所以 `RequestMapping` 表达的是一套通用的“请求到处理方法”的映射规则。

### 7.2 `GetMapping` 是 GET 请求的专用映射

下面两种写法表达相同的核心含义：

```java
@RequestMapping(path = "/hello", method = RequestMethod.GET)
```

```java
@GetMapping("/hello")
```

`@GetMapping` 是基于 `@RequestMapping` 构成的组合注解，它预先将 HTTP 方法限制为 GET，使代码更加简洁，也更容易看出接口用途。

Spring MVC 为常见的 HTTP 方法提供了对应的专用注解：

| 注解 | 映射关系 |
| --- | --- |
| `@GetMapping` | GET 请求 → Java 方法 |
| `@PostMapping` | POST 请求 → Java 方法 |
| `@PutMapping` | PUT 请求 → Java 方法 |
| `@DeleteMapping` | DELETE 请求 → Java 方法 |
| `@PatchMapping` | PATCH 请求 → Java 方法 |
| `@RequestMapping` | 满足指定条件的请求 → Java 类或方法 |

### 7.3 类映射和方法映射会组合

在当前控制器中：

```java
@RequestMapping("/test")
public class TestController {

  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }
}
```

两个注解分别负责不同层级：

```text
类上的 @RequestMapping：公共路径前缀 /test
方法上的 @GetMapping：请求方法 GET，方法路径 /hello
```

Spring 会将它们组合为一条完整映射：

```text
GET + /test + /hello
        ↓
GET /test/hello → TestController.hello()
```

Spring 启动时，会收集这些注解并建立类似下面的请求路由登记表：

```text
GET  /test/hello → TestController.hello()
POST /users      → UserController.create()
GET  /users/{id} → UserController.findById()
```

可以把 `Mapping` 记成一句话：

> 当满足指定条件的 HTTP 请求到来时，将它交给指定的 Java 方法处理。
