package com.youyue.reidscourse.controller;

import com.youyue.reidscourse.mapper.UserLoginMapper;
import com.youyue.reidscourse.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CacheConfig(cacheNames = "users",cacheManager = "cacheManager")
@RestController
public class UserController {

    @Autowired
    private UserLoginMapper userLoginMapper;

    @GetMapping("/users")
    @PostMapping("/pusers")
    @Cacheable(keyGenerator = "myKeyGenerator")
    public List<UserLogin> users(Integer id) {
        System.out.println("打印代表查询了数据库");
        return userLoginMapper.selectAll();
    }
}
