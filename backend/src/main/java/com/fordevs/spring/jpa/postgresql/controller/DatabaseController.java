package com.fordevs.spring.jpa.postgresql.controller;

import com.fordevs.spring.jpa.postgresql.model.DatabaseCredentials;
import com.fordevs.spring.jpa.postgresql.sevice.DatabaseService;
import com.fordevs.spring.jpa.postgresql.sevice.DatabaseServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class DatabaseController {

    private final DatabaseServiceFactory serviceFactory;

    @Autowired
    public DatabaseController(DatabaseServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }


    @PostMapping("/database/connect")
    public ResponseEntity<String> connectDb(@RequestBody DatabaseCredentials credentials) {
        try {
            DatabaseService service = serviceFactory.getService(credentials);
            DataSource dataSource = service.connect(credentials);
            log.info("DataSource: {}", dataSource);
            return new ResponseEntity<>("Connection successful", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error creating DataSource", e);
            return new ResponseEntity<>("Connection failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
