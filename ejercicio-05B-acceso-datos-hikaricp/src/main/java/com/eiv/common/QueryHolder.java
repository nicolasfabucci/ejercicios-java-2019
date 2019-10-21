package com.eiv.common;

import java.io.Serializable;
import java.util.function.Supplier;

public class QueryHolder<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -306008450084529087L;
    
    private String sql;
    private RowMapper<T> rowMapper;
    
    public QueryHolder() {
    }

    public QueryHolder(String sql, RowMapper<T> rowMapper) {
        super();
        this.sql = sql;
        this.rowMapper = rowMapper;
    }

    public String getSql() {
        return sql;
    }

    public QueryHolder<T> setSql(String sql) {
        this.sql = sql;
        return this;
    }

    public RowMapper<T> getRowMapper() {
        return rowMapper;
    }

    public QueryHolder<T> setRowMapper(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
        return this;
    }
    
    public QueryHolder<T> setRowMapper(Supplier<RowMapper<T>> rowMapper) {
        this.rowMapper = rowMapper.get();
        return this;
    }

    @Override
    public String toString() {
        return "QueryHolder [sql=" + sql + "]";
    }
    
}
