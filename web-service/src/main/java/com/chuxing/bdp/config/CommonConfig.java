package com.chuxing.bdp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @date 2023/5/8 11:17
 * @author huangchenguang
 * @desc 通用配置
 */
@Configuration
public class CommonConfig {

    /**
     * @date 2023/5/8 11:18
     * @author huangchenguang
     * @desc @Value配置
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return placeholderConfigurer;
    }

}
