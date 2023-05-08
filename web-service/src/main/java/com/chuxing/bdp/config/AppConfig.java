package com.chuxing.bdp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2023/4/23 17:16
 * @author huangchenguang
 * @desc app config
 */
@Getter
@Configuration
public class AppConfig {

    /**
     * @date 2023/4/23 17:16
     * @desc app mode
     */
    @Value("${mode:}")
    private String mode;

    /**
     * @date 2023/5/5 17:05
     * @desc open api key
     */
    @Value("${api.key:}")
    private String apiKey;

    /* standalone start*/

    /**
     * @date 2023/4/23 17:28
     * @desc datawarehouse path
     */
    @Value("${datawarehouse.path:}")
    private String datawarehousePath;

    /**
     * @date 2023/4/23 17:28
     * @desc tmp path
     */
    @Value("${tmp.path:}")
    private String tmpPath;

    /**
     * @date 2023/4/23 17:28
     * @desc db path
     */
    @Value("${db.path:}")
    private String dbPath;

    /**
     * @date 2023/4/23 17:28
     * @desc db name
     */
    @Value("${db.main.name:}")
    private String dbMainName;

    /* standalone end*/


}
