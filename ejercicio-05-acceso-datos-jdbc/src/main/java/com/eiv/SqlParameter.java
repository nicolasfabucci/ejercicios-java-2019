package com.eiv;

public interface SqlParameter {

    public SqlParameter addValue(int position, Object value, Class<?> type);
}
