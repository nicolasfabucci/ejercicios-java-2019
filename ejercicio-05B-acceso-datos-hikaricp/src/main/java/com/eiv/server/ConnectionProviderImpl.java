package com.eiv.server;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionProviderImpl implements ConnectionProvider {

    private static final Logger LOG = LogManager.getLogger(ConnectionProviderImpl.class);
    
    private DataSource datasource;
    
    private void init() {
        
        if (datasource != null) {
            LOG.info("ConnectionProvider - DataSource ya inicializado, se sale !!");
            return;
        }
        
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.h2.Driver");
        config.setUsername("sa");
        config.setPassword("");
        config.setJdbcUrl("jdbc:h2:mem:testdb;"
                + "INIT=runscript from 'src/main/resources/import.sql'");
        
        HikariDataSource datasource = new HikariDataSource(config);
        
        LOG.info("ConnectionProvider - Nuevo DataSource cargado - {}", datasource.getJdbcUrl());
        this.datasource = datasource;
    }
    
    @Override
    public Connection getConnection() {
        
        init();
        
        try {
            return datasource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
