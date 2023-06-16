package com.fordevs.spring.jpa.postgresql.sevice;

import com.fordevs.spring.jpa.postgresql.model.DatabaseCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceFactory {
    private final MysqlDatabaseService mysqlDatabaseService;
    private final PostgresDatabaseService postgresDatabaseService;

    private static final String MYSQL_DATABASE_TYPE = "jdbc:mysql://";
    private static final String POSTGRES_DATABASE_TYPE = "jdbc:postgresql://";

    @Autowired
    public DatabaseServiceFactory(MysqlDatabaseService mysqlDatabaseService,
                                  PostgresDatabaseService postgresDatabaseService) {
        this.mysqlDatabaseService = mysqlDatabaseService;
        this.postgresDatabaseService = postgresDatabaseService;
    }
    public DatabaseService getService(DatabaseCredentials databaseCredentials) {

        String databaseType = databaseCredentials.getDatabaseType();

        if (databaseType.equals(MYSQL_DATABASE_TYPE)) {
            return mysqlDatabaseService;
        } else if (databaseType.equals(POSTGRES_DATABASE_TYPE)) {
            return postgresDatabaseService;
        } else {
            throw new IllegalArgumentException("Unsupported database factory type: " + databaseType);
        }
    }
}
