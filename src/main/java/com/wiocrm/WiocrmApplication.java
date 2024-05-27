package com.wiocrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wiocrm.mapper")
public class WiocrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(WiocrmApplication.class, args);
    }
}
