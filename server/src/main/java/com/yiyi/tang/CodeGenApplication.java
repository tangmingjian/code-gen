package com.yiyi.tang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tangmingjian 2018-12-22 下午12:54
 **/
@SpringBootApplication
//@ComponentScan(basePackages = {"com.yiyi.tang"})
@MapperScan(basePackages = "com.yiyi.tang.mapper")
public class CodeGenApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeGenApplication.class, args);
    }
}
