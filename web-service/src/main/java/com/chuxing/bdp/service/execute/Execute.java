package com.chuxing.bdp.service.execute;

import java.util.List;

/**
 * @date 2023/4/27 11:01
 * @author huangchenguang
 * @desc execute
 */
public interface Execute {

    /**
     * @date 2023/4/27 11:01
     * @author huangchenguang
     * @desc execute query
     */
    List<List<String>> executeQuery(String sql);

    /**
     * @date 2023/5/6 11:53
     * @author huangchenguang
     * @desc execute dml
     */
    void executeDml(String sql);

}
