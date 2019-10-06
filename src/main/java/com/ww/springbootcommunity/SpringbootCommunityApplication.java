package com.ww.springbootcommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ww.springbootcommunity.mapper")//是dao层接口
public class SpringbootCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCommunityApplication.class, args);
    }

}
