package com.chuxing.bdp.service.execute;

import com.chuxing.bdp.config.AppConfig;
import com.chuxing.bdp.model.enums.AppModeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @date 2023/4/27 09:52
 * @author huangchenguang
 * @desc 执行器路由
 */
@Service
public class ExecuteRouter implements Execute {

    @Resource
    private AppConfig appConfig;

    @Resource
    private StandaloneExecute standaloneExecute;

    /**
     * @date 2023/5/17 16:10
     * @author huangchenguang
     * @desc 查询底层引擎类型
     */
    @Override
    public String getEngineType() {
        if (Objects.equals(AppModeEnum.STANDALONE.getMode(), appConfig.getMode())) {
            return standaloneExecute.getEngineType();
        }
        throw new RuntimeException("应用初始化异常");
    }

    /**
     * @date 2023/4/27 09:53
     * @author huangchenguang
     * @desc 执行query
     */
    @Override
    public List<List<String>> executeQuery(String sql) {
        if (Objects.equals(AppModeEnum.STANDALONE.getMode(), appConfig.getMode())) {
            return standaloneExecute.executeQuery(sql);
        }
        throw new RuntimeException("应用初始化异常");
    }

    /**
     * @date 2023/5/6 11:54
     * @author huangchenguang
     * @desc 执行dml
     */
    @Override
    public void executeDml(String sql) {
        if (Objects.equals(AppModeEnum.STANDALONE.getMode(), appConfig.getMode())) {
           standaloneExecute.executeDml(sql);
        }
        throw new RuntimeException("应用初始化异常");
    }

    /**
     * @date 2023/5/17 16:11
     * @author huangchenguang
     * @desc 查询字段
     */
    @Override
    public List<String> getColumns(String table) {
        if (Objects.equals(AppModeEnum.STANDALONE.getMode(), appConfig.getMode())) {
            standaloneExecute.getColumns(table);
        }
        throw new RuntimeException("应用初始化异常");
    }

    /**
     * @date 2023/5/17 16:21
     * @author huangchenguang
     * @desc 查询表
     */
    @Override
    public List<String> searchTables(String tableName) {
        if (Objects.equals(AppModeEnum.STANDALONE.getMode(), appConfig.getMode())) {
            standaloneExecute.searchTables(tableName);
        }
        throw new RuntimeException("应用初始化异常");
    }

}
