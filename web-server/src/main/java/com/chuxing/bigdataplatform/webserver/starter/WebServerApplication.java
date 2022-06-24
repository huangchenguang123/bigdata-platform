package com.chuxing.bigdataplatform.webserver.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @date 2022/5/25 13:53
 * @author huangchenguang
 * @desc starter
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.chuxing.bigdataplatform.webserver")
public class WebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class, args);
    }

}
