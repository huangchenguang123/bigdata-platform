package com.chuxing.bdp.config;

import com.chuxing.bdp.model.enums.AppModeEnum;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2023/4/23 17:16
 * @author huangchenguang
 * @desc 应用全局配置，对应application.properties
 */
@Getter
@Configuration
public class AppConfig {

    /**
     * @date 2023/4/23 17:16
     * @desc 应用运行模式
     * @see AppModeEnum
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
     * @desc 独立模式下，本地存储数据的目录
     */
    @Value("${datawarehouse.path:/db}")
    private String datawarehousePath;

    /**
     * @date 2023/4/23 17:28
     * @desc 独立模式下，本地存储数据的地址中的临时目录
     */
    @Value("${tmp.path:/tmp}")
    private String tmpPath;

    /**
     * @date 2023/4/23 17:28
     * @desc 独立模式下，本地存储数据的地址中的数据目录
     */
    @Value("${db.path:/db}")
    private String dbPath;

    /**
     * @date 2023/4/23 17:28
     * @desc 独立模式下，本地存储数据的地址中的数据库名称
     */
    @Value("${db.main.name:bdp.main.db}")
    private String dbMainName;

    /* standalone end*/


}
