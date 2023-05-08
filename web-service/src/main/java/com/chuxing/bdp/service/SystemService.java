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
 * @desc system service
 */
@Service
public class SystemService {

    @Resource
    private AppConfig appConfig;

    /**
     * @date 2023/4/23 17:27
     * @author huangchenguang
     * @desc check
     */
    @PostConstruct
    private void check() {
        String mode = appConfig.getMode();
        if (StringUtils.isBlank(mode)) {
            throw new RuntimeException("mode is empty, please add property 'mode' in application.properties");
        }
        if (Objects.equals(mode, AppModeEnum.STANDALONE.getMode())) {
            checkStandalone();
        } else {
            throw new RuntimeException("unknown mode, please modify property 'mode' in application.properties");
        }
    }

    /**
     * @date 2023/4/23 17:29
     * @author huangchenguang
     * @desc check standalone
     */
    private void checkStandalone() {
        if (StringUtils.isBlank(appConfig.getDatawarehousePath())) {
            throw new RuntimeException("datawarehouse.path is empty, please add property 'datawarehouse.path' in application.properties");
        }
        if (StringUtils.isBlank(appConfig.getTmpPath())) {
            throw new RuntimeException("tmp.path is empty, please add property 'tmp.path' in application.properties");
        }
        if (StringUtils.isBlank(appConfig.getDbPath())) {
            throw new RuntimeException("db.path is empty, please add property 'db.path' in application.properties");
        }
        if (StringUtils.isBlank(appConfig.getDbMainName())) {
            throw new RuntimeException("db.main.name is empty, please add property 'db.main.name' in application.properties");
        }
        String tmpPath = String.format("%s%s", appConfig.getDatawarehousePath(), appConfig.getTmpPath());
        FileUtils.mkdirs(tmpPath);
        String dbPath = String.format("%s%s", appConfig.getDatawarehousePath(), appConfig.getDbPath());
        FileUtils.mkdirs(dbPath);
    }

    /**
     * @date 2023/4/23 17:20
     * @author huangchenguang
     * @desc get mode
     */
    public String getMode() {
        return appConfig.getMode();
    }

}
