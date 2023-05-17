package com.chuxing.bdp.client;

import com.chuxing.bdp.config.AppConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

/**
 * @date 2023/4/26 10:29
 * @author huangchenguang
 * @desc duckdb工厂类，用于duckdb建立连接，关闭连接等操作
 */
@Service
public class DuckDbFactory {

    @Resource
    private AppConfig appConfig;

    // 初始化jdbc驱动
    static {
        try {
            Class.forName("org.duckdb.DuckDBDriver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @date 2023/4/26 10:40
     * @author huangchenguang
     * @desc 获取duckdb连接
     */
    public Connection getConnection() {
        try {
            String dbPath = String.format("%s%s/%s", appConfig.getDatawarehousePath(), appConfig.getDbPath(), appConfig.getDbMainName());
            return DriverManager.getConnection(String.format("%s%s", "jdbc:duckdb:", dbPath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @date 2023/4/26 10:40
     * @author huangchenguang
     * @desc 关闭jdbc连接
     */
    public void close(Connection connection) {
        try {
            if (Objects.nonNull(connection)) {
                connection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
