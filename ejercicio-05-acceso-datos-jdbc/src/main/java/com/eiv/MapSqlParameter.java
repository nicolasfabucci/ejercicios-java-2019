package com.eiv;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapSqlParameter implements SqlParameter {

    private Map<Integer, Object> values = new LinkedHashMap<Integer, Object>();
    private Map<Integer, Class<?>> types = new LinkedHashMap<Integer, Class<?>>();

    @Override
    public SqlParameter addValue(int position, Object value, Class<?> type) {
        this.values.put(position, value);
        this.types.put(position, type);
        return this;
    }

    public Map<Integer, Object> getValues() {
        return values;
    }
    
    public Class<?> getType(int position) {
        return types.get(position);
    }
    
    public <T> T getValue(int position, Class<T> clazz) {
        Object raw = values.get(position);
        T t = clazz.cast(raw);
        return t;
    }
}
