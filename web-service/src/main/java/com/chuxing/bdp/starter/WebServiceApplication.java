package com.chuxing.bdp.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @date 2023/4/23 11:37
 * @author huangchenguang
 * @desc 启动类
 */
@SpringBootApplication(scanBasePackages = "com.chuxing")
public class WebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServiceApplication.class, args);
	}

}
