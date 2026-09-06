package com.study.core.exception;

import com.study.core.model.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/// # 全局异常处理器
///
/// 统一处理各个控制器抛出的异常，并将错误信息写入响应体。
@RestControllerAdvice
public class GlobalExceptionAdvice {

    // 指定由当前方法处理 BusinessException 及其子类异常。
    @ExceptionHandler(BusinessException.class)
    public void handleBusinessException (BusinessException e, HttpServletResponse response) {
        // 打印日志完整堆栈信息
        e.printStackTrace();
        // 通过response对象做响应
        ApiResponse.response(response, ApiResponse.failed(e.getCode(), e.getMessage()));
    }

    // 捕获未被其他处理器匹配的异常，作为全局异常处理的兜底方法。
    @ExceptionHandler(Throwable.class)
    public void handleThrowable (Throwable e, HttpServletResponse response) {
        // 打印日志完整堆栈信息
        e.printStackTrace();
        // 通过response对象做响应
        ApiResponse.response(response, ApiResponse.failed("未知错误"));
    }
}
