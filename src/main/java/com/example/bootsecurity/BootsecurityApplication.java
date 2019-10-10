package com.example.bootsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan( "com.example.bootsecurity.mapper")
public class BootsecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootsecurityApplication.class, args);
    }

}
