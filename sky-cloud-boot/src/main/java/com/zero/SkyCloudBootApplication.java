package com.zero;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.zero.repository.**"})
@SpringBootApplication
public class SkyCloudBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyCloudBootApplication.class, args);
    }

}
