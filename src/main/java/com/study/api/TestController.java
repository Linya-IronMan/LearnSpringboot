package com.study.api;

import com.study.core.annotation.ApiMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @ApiMethod(name = "测试接口 ", description = "仅仅是一个测试 ")
    @GetMapping("/hello")
    public String test() {
        return "hello";
    }
}
