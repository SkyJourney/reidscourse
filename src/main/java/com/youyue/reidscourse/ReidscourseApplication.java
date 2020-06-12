package com.youyue.reidscourse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.youyue.reidscourse.mapper")
@EnableCaching
public class ReidscourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReidscourseApplication.class, args);
    }

}
