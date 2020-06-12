package com.youyue.reidscourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RedisController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping("/redis")
    public String redis() {
        Map map = (Map)redisTemplate.opsForValue().get("d");
        System.out.println(map);
        return "SUCCESS";
    }

}
