package com.fordevs.spring.jpa.postgresql.sevice;

import com.fordevs.spring.jpa.postgresql.model.DatabaseCredentials;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DatabaseService {
    DataSource connect(DatabaseCredentials databaseCredentials) throws SQLException;

    List<Map<String, Object>> executeQuery(String query) throws SQLException;

    List<String> getSchema(DatabaseCredentials credentials) throws SQLException;

    HikariConfig getConfig();

    void disconnect();
}
