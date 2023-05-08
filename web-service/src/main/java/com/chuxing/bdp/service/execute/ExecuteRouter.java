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
 * @desc execute router
 */
@Service
public class ExecuteRouter implements Execute {

    @Resource
    private AppConfig appConfig;

    @Resource
    private StandaloneExecute standaloneExecute;

    /**
     * @date 2023/4/27 09:53
     * @author huangchenguang
     * @desc execute query
     */
    @Override
    public List<List<String>> executeQuery(String sql) {
        if (Objects.equals(AppModeEnum.STANDALONE.getMode(), appConfig.getMode())) {
            return standaloneExecute.executeQuery(sql);
        }
        throw new RuntimeException("mode is empty, please add property 'mode' in application.properties");
    }

    /**
     * @date 2023/5/6 11:54
     * @author huangchenguang
     * @desc execute dml
     */
    @Override
    public void executeDml(String sql) {
        if (Objects.equals(AppModeEnum.STANDALONE.getMode(), appConfig.getMode())) {
           standaloneExecute.executeDml(sql);
        }
        throw new RuntimeException("mode is empty, please add property 'mode' in application.properties");
    }

}
