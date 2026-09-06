package com.study.api;

import com.study.core.model.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/// 使用 Swagger/OpenAPI 对接口进行分组，并设置接口分组名称。
///
/// 这是一个 REST 控制器，统一使用 `/test6` 作为请求路径前缀。
///
/// `findAll` 方法通过 GET 请求查询全部数据，并在 OpenAPI 文档中显示“查询所有”摘要。
@Tag(name = "测试接口 6")
@RestController
@RequestMapping("/test6")
public class TestController6 {

    @GetMapping("/all")
    @Operation(summary = "查询所有")
    public ApiResponse<List<?>> findAll() {
        return ApiResponse.success(200, "成功 ", new ArrayList<>());
    }
}
