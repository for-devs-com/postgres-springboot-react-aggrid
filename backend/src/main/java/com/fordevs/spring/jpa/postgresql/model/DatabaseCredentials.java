package com.fordevs.spring.jpa.postgresql.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DatabaseCredentials {
    private String host;
    private int port;
    private String username;
    private String password;
    private String databaseName;
    private String databaseType;

    public DatabaseCredentials() {

    }
}
