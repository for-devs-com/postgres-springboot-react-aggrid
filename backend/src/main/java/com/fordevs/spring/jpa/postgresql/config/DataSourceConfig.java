package com.fordevs.spring.jpa.postgresql.config;

import com.fordevs.spring.jpa.postgresql.model.DatabaseCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    DatabaseCredentials credentials;
    private static final String MYSQL_DATABASE_TYPE = "jdbc:mysql://";
    private static final String POSTGRES_DATABASE_TYPE = "jdbc:postgresql://";

    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();


        switch (credentials.getDatabaseType()) {
            case MYSQL_DATABASE_TYPE:
                dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
                dataSourceBuilder.url(credentials.getDatabaseType()+ credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabaseName());
                break;
            case POSTGRES_DATABASE_TYPE:
                dataSourceBuilder.driverClassName("org.postgresql.Driver");
                dataSourceBuilder.url(credentials.getDatabaseType()+ credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabaseName());
                break;
            default:
                throw new IllegalArgumentException("Unsupported database datasource type: " + credentials.getDatabaseType());
        }
        dataSourceBuilder.username(credentials.getUsername());
        dataSourceBuilder.password(credentials.getPassword());
        return dataSourceBuilder.build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
