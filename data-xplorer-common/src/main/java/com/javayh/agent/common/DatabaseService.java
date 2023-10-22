package com.javayh.agent.common;

import com.javayh.agent.common.constant.DatabaseType;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author haiji
 */
@Lazy
@Service
public class DatabaseService {

    private final DataSource dataSource;

    public DatabaseService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getDatabaseInfo() {
        try {
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            // 根据数据库类型修改SQL查询语句
            if (DatabaseType.MySQL.name().equalsIgnoreCase(databaseProductName)) {
                return DatabaseType.MySQL.name();
            } else if (DatabaseType.Oracle.name().equalsIgnoreCase(databaseProductName)) {
                return DatabaseType.Oracle.name();
            } else if (DatabaseType.PostgreSQL.name().equalsIgnoreCase(databaseProductName)) {
                return DatabaseType.PostgreSQL.name();
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}