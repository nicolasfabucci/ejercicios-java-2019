package com.eiv.server;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eiv.common.QueryHolder;
import com.eiv.common.RowMapper;

public class JdbcTemplate {

    private static final Logger LOG = LogManager.getLogger(JdbcTemplate.class);
    
    private ConnectionProvider connectionProvider;

    public JdbcTemplate() {
    }
    
    public JdbcTemplate(ConnectionProvider connectionProvider) {
        super();
        this.connectionProvider = connectionProvider;
    }
    
    public JdbcTemplate withDataSource(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        return this;
    }
    
    public <T extends Serializable> List<T> query(QueryHolder<T> queryHolder) {
        return query(queryHolder.getSql(), queryHolder.getRowMapper());
    }
    
    public <T extends Serializable> List<T> query(String sql, RowMapper<T> rowMapper) {
        
        if(connectionProvider == null) {
            LOG.error("JdbcTemplate - Exception: Proveedor de conexiones no disponible!");
            throw new IllegalStateException("Proveedor de conexiones no disponible!");
        }
        
        int rowNum = 0;
        ArrayList<T> result = new ArrayList<T>();
        try(Connection conn = connectionProvider.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
                
                while(rs.next()) {
                    T t = rowMapper.mapRow(rs, rowNum);
                    result.add(t);
                }
                
        } catch (SQLException e) {
            LOG.error("JdbcTemplate - Exception: {}", e.getMessage());
        }
        return result;
    }
}
