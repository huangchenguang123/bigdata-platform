package com.chuxing.bdp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @date 2023/5/9 17:30
 * @author huangchenguang
 * @desc mvc配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * @date 2023/5/9 17:31
     * @author huangchenguang
     * @desc cors配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
