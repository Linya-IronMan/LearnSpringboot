package com.study.core.model;

import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/// # 统一接口响应对象
///
/// 使用泛型 `T` 表示接口返回的数据类型，并通过 `code`、`message` 和 `data`
/// 统一描述接口响应结构。
///
/// 类上的 `@Schema` 用于设置 OpenAPI 文档中模型的名称和说明；字段上的
/// `@Schema` 用于设置模型属性的说明。它们只影响 Swagger/Knife4j 文档展示，
/// 不负责运行时的数据校验或 JSON 序列化逻辑。
@Schema(name = "ApiResponse", description = "接口统一响应对象")
public class ApiResponse<T> {

    /// 响应状态码，例如 `200` 表示成功，`500` 表示服务端错误。
    @Schema(description = "响应码")
    private Integer code;

    /// 返回给调用方的提示信息或错误信息。
    @Schema(description = "消息")
    private String message;

    /// 接口返回的业务数据，具体类型由泛型 `T` 决定。
    @Schema(description = "数据")
    private T data;

    public static <T> ApiResponse<T> success(Integer code, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.success(200, "成功", data);
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.success(null);
    }

    public static <T> ApiResponse<T> failed(Integer code, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    /// 直接通过 `HttpServletResponse` 写出响应结果。
    ///
    /// @param response 响应对象
    /// @param apiResponse 统一响应对象
    public static void response (HttpServletResponse response, ApiResponse<?> apiResponse) {
        try {
            response.setHeader("content-type", "application/json;charset=UTF-8");
            response.getOutputStream().write(JSON.toJSONString(apiResponse).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> ApiResponse<T> failed(String message) {
        return ApiResponse.failed(500, message);
    }

    public static <T> ApiResponse<T> failed() {
        return ApiResponse.failed(500, "系统错误");
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
