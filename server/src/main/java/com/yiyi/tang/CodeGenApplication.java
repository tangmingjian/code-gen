package com.yiyi.tang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author tangmingjian 2018-12-22 下午12:54
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.yiyi.tang"})
@MapperScan(basePackages = "com.yiyi.tang.mappers")
public class CodeGenApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeGenApplication.class, args);
    }
}
