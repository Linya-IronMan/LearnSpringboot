# SpringBoot 学习仓库

## Thymeleaf 页面渲染

本项目使用 Thymeleaf 将服务端数据渲染为 HTML 页面，相关依赖、控制器和模板如下：

```text
pom.xml                                      # spring-boot-starter-thymeleaf
src/main/java/com/study/api/PageController.java
src/main/resources/templates/index.html     # Thymeleaf 模板
src/main/resources/static/index.html        # 静态 HTML，不经过 Thymeleaf
```

### 1. 引入依赖

`pom.xml` 中引入 Spring Boot 官方 starter，版本由 Spring Boot 的 parent 统一管理：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

引入后，Spring Boot 会自动配置 Thymeleaf 的视图解析器。项目没有额外覆盖配置，默认模板目录为 `classpath:/templates/`，默认后缀为 `.html`。

### 2. 控制器传递数据

页面控制器需要使用 `@Controller`，并返回视图名称，而不是 `@RestController`：

```java
@Controller
public class PageController {
    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("message", "dynamic message");
        return "index";
    }
}
```

处理 `GET /` 时，`Model` 中的 `message` 会被传递给模板。`return "index"` 表示查找：

```text
classpath:/templates/index.html
```

视图名称通常不写目录前缀和 `.html` 后缀。

### 3. 模板读取数据

`src/main/resources/templates/index.html` 使用 `th:text` 将模型数据写入元素文本：

```html
<!doctype html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org">
<body>
<h1>Thymeleaf</h1>
<p th:text="${message}">默认消息</p>
</body>
</html>
```

`${message}` 是 Thymeleaf 表达式。页面渲染后，`<p>` 的内容会变为 `dynamic message`；标签体中的“默认消息”可以作为未经过模板处理时的后备内容。

### 4. 常用模板语法

```html
<!-- 输出文本，并自动进行 HTML 转义 -->
<span th:text="${message}"></span>

<!-- 条件显示 -->
<div th:if="${user != null}" th:text="${user.name}"></div>

<!-- 遍历集合 -->
<ul>
    <li th:each="item : ${items}" th:text="${item}"></li>
</ul>

<!-- 拼接链接参数 -->
<a th:href="@{/users/{id}(id=${user.id})}">用户详情</a>
```

模板属性以 `th:` 开头。表达式放在 `${...}` 中，链接建议使用 `@{...}`，这样可以正确处理应用上下文路径。

### 5. 启动和访问

项目端口配置为 `8080`：

```bash
mvn spring-boot:run
```

启动后访问 [http://localhost:8080/](http://localhost:8080/)，即可看到由 Thymeleaf 动态渲染的 `dynamic message`。

### 6. `templates` 与 `static` 的区别

| 目录 | 处理方式 | 适用内容 | 本项目示例 |
| --- | --- | --- | --- |
| `src/main/resources/templates/` | 由控制器返回视图后交给 Thymeleaf 处理 | 动态页面、服务端变量、条件和循环 | `templates/index.html` |
| `src/main/resources/static/` | Spring Boot 直接作为静态资源返回 | CSS、JavaScript、图片和不需要服务端渲染的 HTML | `static/index.html` |

放在 `static/` 中的 HTML 不会解析 `th:text` 等 Thymeleaf 属性；需要使用模型数据的页面应放在 `templates/` 中，并通过 `@Controller` 返回视图。

### 7. 自定义配置

如果需要修改模板前缀、后缀或缓存策略，可以在 `src/main/resources/application.yml` 中配置：

```yaml
spring:
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html
    cache: false # 开发阶段关闭缓存，修改模板后刷新即可生效
```

本项目当前只配置了服务端口，Thymeleaf 使用 Spring Boot 默认配置。

更多学习资料：

- [语雀参考笔记](https://goldpankit.yuque.com/org-wiki-goldpankit-opypff/full-stack/oeelx7lizuk7hhbv)
