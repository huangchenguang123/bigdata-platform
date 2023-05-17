package com.chuxing.bdp.service;

import com.chuxing.bdp.config.AppConfig;
import com.chuxing.bdp.model.enums.AppModeEnum;
import com.chuxing.bdp.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;

/**
 * @date 2023/4/23 17:19
 * @author huangchenguang
 * @desc 系统服务
 */
@Service
public class SystemService {

    @Resource
    private AppConfig appConfig;

    /**
     * @date 2023/4/23 17:27
     * @author huangchenguang
     * @desc 应用启动检查
     */
    @PostConstruct
    private void check() {
        String mode = appConfig.getMode();
        if (StringUtils.isBlank(mode)) {
            throw new RuntimeException("应用启动异常，请检查启动配置");
        }
        if (Objects.equals(mode, AppModeEnum.STANDALONE.getMode())) {
            checkStandalone();
        } else {
            throw new RuntimeException("应用启动异常，请检查启动配置");
        }
    }

    /**
     * @date 2023/4/23 17:29
     * @author huangchenguang
     * @desc 独立模式应用启动检查
     */
    private void checkStandalone() {
        if (StringUtils.isBlank(appConfig.getDatawarehousePath())) {
            throw new RuntimeException("应用启动异常，请检查启动配置");
        }
        if (StringUtils.isBlank(appConfig.getTmpPath())) {
            throw new RuntimeException("应用启动异常，请检查启动配置");
        }
        if (StringUtils.isBlank(appConfig.getDbPath())) {
            throw new RuntimeException("应用启动异常，请检查启动配置");
        }
        if (StringUtils.isBlank(appConfig.getDbMainName())) {
            throw new RuntimeException("应用启动异常，请检查启动配置");
        }
        String tmpPath = String.format("%s%s", appConfig.getDatawarehousePath(), appConfig.getTmpPath());
        FileUtils.mkdirs(tmpPath);
        String dbPath = String.format("%s%s", appConfig.getDatawarehousePath(), appConfig.getDbPath());
        FileUtils.mkdirs(dbPath);
    }

    /**
     * @date 2023/4/23 17:20
     * @author huangchenguang
     * @desc 查询系统运行模式
     */
    public String getMode() {
        return appConfig.getMode();
    }

}
