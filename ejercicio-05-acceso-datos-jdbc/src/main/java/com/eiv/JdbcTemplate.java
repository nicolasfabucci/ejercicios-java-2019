package com.eiv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class JdbcTemplate {

    private DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }
    
    public JdbcTemplate withDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }
    
    public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
        ArrayList<T> result = new ArrayList<T>();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
                
            while (rs.next()) {
                T t = rowMapper.mapRow(rs, rs.getRow());
                result.add(t);
            }
                
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        return result;
    }
    
    public <T> List<T> query(String sql, SqlParameter mapSqlParameter, RowMapper<T> rowMapper) {
        ArrayList<T> result = new ArrayList<T>();
        MapSqlParameter sqlParameter = (MapSqlParameter) mapSqlParameter;
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            sqlParameter.getValues().keySet().forEach(pos -> {
                
                Class<?> clazz = sqlParameter.getType(pos);
                
                try {
                    if (String.class == clazz) {
                        String value = sqlParameter.getValue(pos, String.class);   
                        stmt.setString(pos, value);
                    } else if (Integer.class == clazz || int.class == clazz) {
                        Integer value = sqlParameter.getValue(pos, Integer.class);   
                        stmt.setInt(pos, value);
                    } else if (Long.class == clazz || long.class == clazz) {
                        Long value = sqlParameter.getValue(pos, Long.class);   
                        stmt.setLong(pos, value);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            
            ResultSet rs = stmt.executeQuery();
                    
            while (rs.next()) {
                T t = rowMapper.mapRow(rs, rs.getRow());
                result.add(t);
            }
            
            rs.close();
                
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        return result;
    }
}