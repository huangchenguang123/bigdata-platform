package com.chuxing.bdp.service.execute;

import com.chuxing.bdp.client.DuckDbFactory;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date 2023/4/27 10:40
 * @author huangchenguang
 * @desc 独立模式执行器
 */
@Service
public class StandaloneExecute implements Execute {

    @Resource
    private DuckDbFactory duckDbFactory;

    @Override
    public String getEngineType() {
        return "sqlite";
    }

    /**
     * @date 2023/4/27 10:40
     * @author huangchenguang
     * @desc 执行query
     */
    @Override
    public List<List<String>> executeQuery(String sql) {
        List<List<String>> result = Lists.newArrayList();
        Connection connection = null;
        try {
            connection = duckDbFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            List<String> mate = Lists.newArrayList();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                mate.add(resultSetMetaData.getColumnName(i));
            }
            result.add(mate);

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
     * @desc 执行dml
     */
    @Override
    public void executeDml(String sql) {
        Connection connection = null;
        try {
            connection = duckDbFactory.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            duckDbFactory.close(connection);
        }
    }

    /**
     * @date 2023/5/17 16:14
     * @author huangchenguang
     * @desc 查询字段
     */
    @Override
    public List<String> getColumns(String table) {
        String showTableSql = String.format("pragma table_info(%s);", table);
        List<List<String>> result = executeQuery(showTableSql);
        return result.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * @date 2023/5/17 11:18
     * @author huangchenguang
     * @desc 查询表
     */
    @Override
    public List<String> searchTables(String tableName) {
        String sql = "SELECT name FROM sqlite_master WHERE type='table';";
        List<List<String>> result = executeQuery(sql);
        return result.stream()
                .flatMap(Collection::stream)
                .filter(table -> {
                    if (StringUtils.isNotBlank(tableName)) {
                        return table.contains(tableName);
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

}
