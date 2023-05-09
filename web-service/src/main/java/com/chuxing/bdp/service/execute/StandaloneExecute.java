package com.chuxing.bdp.service.execute;


import com.chuxing.bdp.client.DuckDbFactory;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;
import java.util.List;

/**
 * @date 2023/4/27 10:40
 * @author huangchenguang
 * @desc standalone execute
 */
@Service
public class StandaloneExecute implements Execute {

    @Resource
    private DuckDbFactory duckDbFactory;

    /**
     * @date 2023/4/27 10:40
     * @author huangchenguang
     * @desc execute
     */
    @Override
    public List<List<String>> executeQuery(String sql) {
        List<List<String>> result = Lists.newArrayList();
        Connection connection = null;
        try {
            // get connection
            connection = duckDbFactory.getConnection();
            // get statement
            Statement statement = connection.createStatement();
            // get rs
            ResultSet resultSet = statement.executeQuery(sql);
            // get mate
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            List<String> mate = Lists.newArrayList();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                mate.add(resultSetMetaData.getColumnName(i));
            }
            result.add(mate);
            // get result
            while (resultSet.next()) {
                List<String> row = Lists.newArrayList();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    row.add(resultSet.getString(i));
                }
                result.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            duckDbFactory.close(connection);
        }
        return result;
    }

    /**
     * @date 2023/5/6 11:53
     * @author huangchenguang
     * @desc execute dml
     */
    @Override
    public void executeDml(String sql) {
        Connection connection = null;
        try {
            // get connection
            connection = duckDbFactory.getConnection();
            // get statement
            Statement statement = connection.createStatement();
            // get rs
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            duckDbFactory.close(connection);
        }
    }

}
