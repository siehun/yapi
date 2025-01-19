package com.yue.yapi;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class YapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YapiApplication.class, args);
    }

}
