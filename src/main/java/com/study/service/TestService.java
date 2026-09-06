package com.study.service;

import com.study.core.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class TestService {

//    @Resource
//    private TestMapper testMapper;

//    public void create(CreateTestDTO dto) {
public void create() {
        if (true) {
            throw new BusinessException(501, "用户名已存在");
        }
    }
}
