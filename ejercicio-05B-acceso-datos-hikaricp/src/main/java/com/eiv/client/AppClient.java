package com.eiv.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eiv.common.ProvinciaEntity;
import com.eiv.common.QueryHolder;
import com.eiv.common.SerializationUtils;

public class AppClient {

    private static final Logger LOG = LogManager.getLogger(AppClient.class);
    
    public static void main(String[] args) {
        AppClient app = new AppClient();
        app.run();
    }

    private void run() {
        
        QueryHolder<ProvinciaEntity> queryHolder = getProvinciaQueryHolder();
        byte[] data = SerializationUtils.serialize(queryHolder);
        
        LOG.info("Conectando con servidor ...");
        
        try (Socket socket = new Socket("localhost", 9000);
                InputStream in = new ByteArrayInputStream(data);
                OutputStream socketOutputStream = socket.getOutputStream();
                InputStream socketInputStream = socket.getInputStream()) {
            
            LOG.info("Conectado! Enviando datos ...");
            socketOutputStream.write(data);
            
            LOG.info("Recibiendo respuesta ...");
            List<?> collection = SerializationUtils.deserialize(socketInputStream);
            
            collection.forEach(e -> {
                LOG.info("Provincia: {}", e);
            });

            
        } catch (IOException e) {
            LOG.error("CLIENT - Exception: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
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
