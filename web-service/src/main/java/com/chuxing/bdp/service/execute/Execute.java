package com.chuxing.bdp.service.execute;

import java.util.List;

/**
 * @date 2023/4/27 11:01
 * @author huangchenguang
 * @desc 执行器
 */
public interface Execute {

    /**
     * @date 2023/5/17 11:12
     * @author huangchenguang
     * @desc 查询底层引擎类型
     */
    String getEngineType();

    /**
     * @date 2023/4/27 11:01
     * @author huangchenguang
     * @desc 执行query
     */
    List<List<String>> executeQuery(String sql);

    /**
     * @date 2023/5/6 11:53
     * @author huangchenguang
     * @desc 执行dml
     */
    void executeDml(String sql);

    /**
     * @date 2023/5/17 11:18
     * @author huangchenguang
     * @desc 查询字段
     */
    List<String> getColumns(String table);

    /**
     * @date 2023/5/17 11:18
     * @author huangchenguang
     * @desc 查询表
     */
    List<String> searchTables(String tableName);

}
