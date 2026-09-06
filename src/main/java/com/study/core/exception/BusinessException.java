package com.study.core.exception;

/// # 业务异常
///
/// 继承自 `RuntimeException`，表示出现错误时不必立即处理。
///
/// 它区别于必须通过 `try-catch` 处理的编译期异常。
public class BusinessException extends RuntimeException{

    // 错误码
    private Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
