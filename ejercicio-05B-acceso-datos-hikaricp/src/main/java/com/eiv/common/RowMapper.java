package com.eiv.common;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> extends Serializable {

    public T mapRow(ResultSet resultSet, int rowNum) throws SQLException;
}
