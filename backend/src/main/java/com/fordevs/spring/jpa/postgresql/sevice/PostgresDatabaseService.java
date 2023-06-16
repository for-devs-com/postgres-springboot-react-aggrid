package com.fordevs.spring.jpa.postgresql.sevice;

import com.fordevs.spring.jpa.postgresql.model.DatabaseCredentials;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PostgresDatabaseService implements DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
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

        this.ds = new HikariDataSource(config);
        return ds;
    }

    @Override
    public List<Map<String, Object>> executeQuery(String query) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate.queryForList(query);
    }


    @Override
    public void disconnect() {
        try {
            if (ds != null) {
                ds.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public List<String> getSchema(DatabaseCredentials credentials) throws SQLException {
        String sql = "SELECT schema_name FROM information_schema.schemata";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public HikariConfig getConfig() {
        return ds;
    }
}
