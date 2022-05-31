package com.chuxing.bigdataplatform.adhocquery.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @date 2022/5/25 13:53
 * @author huangchenguang
 * @desc starter
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.chuxing.bigdataplatform.adhocquery")
public class AdHocQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdHocQueryApplication.class, args);
    }

}
