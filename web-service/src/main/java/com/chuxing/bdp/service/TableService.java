package com.chuxing.bdp.service;

import com.chuxing.bdp.service.execute.ExecuteRouter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @date 2023/5/17 16:18
 * @author huangchenguang
 * @desc 表服务
 */
@Slf4j
@Service
public class TableService {

    @Resource
    private ExecuteRouter executeRouter;

    /**
     * @date 2023/5/17 16:19
     * @author huangchenguang
     * @desc 查询表列表
     */
    public List<String> searchTables(String tableName) {
        return executeRouter.searchTables(tableName);
    }

}
