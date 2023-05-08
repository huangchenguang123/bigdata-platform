package com.chuxing.bdp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @date 2023/5/8 11:17
 * @author huangchenguang
 * @desc common config
 */
@Configuration
public class CommonConfig {

    /**
     * @date 2023/5/8 11:18
     * @author huangchenguang
     * @desc placeholderConfigurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return placeholderConfigurer;
    }

}
