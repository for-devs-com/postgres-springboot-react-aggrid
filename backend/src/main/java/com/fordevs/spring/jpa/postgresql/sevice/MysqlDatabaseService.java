package com.fordevs.spring.jpa.postgresql.sevice;

import com.fordevs.spring.jpa.postgresql.model.DatabaseCredentials;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MysqlDatabaseService implements DatabaseService {

    private HikariDataSource ds;

    @Override
    public HikariDataSource connect(DatabaseCredentials credentials) throws SQLException {
        String url = credentials.getDatabaseType() + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabaseName();
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        ds = new HikariDataSource(config);
        return ds;
    }

    @Override
    public List<Map<String, Object>> executeQuery(String query) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate.queryForList(query);
    }

    @Override
    public void disconnect() {
        if (ds != null) {
            ds.close();
        }
    }

    @Override
    public List<String> getSchema(DatabaseCredentials credentials) throws SQLException {
        connect(credentials);
        List<Map<String, Object>> schemas = executeQuery("SELECT schema_name FROM information_schema.schemata");
        List<String> tables = new ArrayList<>();
        while (schemas.iterator().hasNext()) {
                tables.add(schemas.iterator().next().get("schema_name").toString());
            }
        disconnect();
        return tables;
    }

    @Override
    public HikariConfig getConfig() {
        return ds;
    }
}

