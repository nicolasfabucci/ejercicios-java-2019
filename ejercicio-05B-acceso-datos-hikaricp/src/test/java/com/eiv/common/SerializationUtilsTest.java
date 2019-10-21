package com.eiv.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SerializationUtilsTest {

    @Test
    public void test() {
        
        QueryHolder<ProvinciaEntity> direct = getProvinciaQueryHolder();
        byte[] data = SerializationUtils.serialize(direct);
        
        Assertions.assertThat(data).isNotEmpty();
        
        InputStream in = new ByteArrayInputStream(data);
        QueryHolder<ProvinciaEntity> revert = SerializationUtils.deserialize(in);
        Assertions.assertThat(revert).isNotNull();
        
        System.out.println(revert);
    }

    private QueryHolder<ProvinciaEntity> getProvinciaQueryHolder() {
        
        QueryHolder<ProvinciaEntity> queryHolder = new QueryHolder<ProvinciaEntity>()
                .setSql("SELECT * FROM provincias")
                .setRowMapper((rs, row) -> {
                  
                    long id = rs.getLong("id");
                    String nombre = rs.getString("nombre");
                    
                    ProvinciaEntity provinciaEntity = new ProvinciaEntity();
                    
                    provinciaEntity.setId(id);
                    provinciaEntity.setNombre(nombre);
                    
                    return provinciaEntity;
                });
      
        return queryHolder;
    }
}
