package com.eiv.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eiv.common.QueryHolder;
import com.eiv.common.SerializationUtils;

public class ClientHandler extends Thread {

    private static final Logger LOG = LogManager.getLogger(ClientHandler.class);
    
    final Socket clientSocket;
    final OutputStream out;
    final InputStream in;
    
    public ClientHandler(Socket clientSocket, OutputStream out, InputStream in) {
        super();
        this.clientSocket = clientSocket;
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        
        try {
            
            LOG.info("Procesando una nueva solicitud ...");
            
            QueryHolder<?> queryHolder = SerializationUtils.deserialize(in);
            List<?> collection = getJdbcTemplate().query(queryHolder);
    
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(collection);
            oos.close();
            
            LOG.info("Solicitud procesada, se envia respuesta ...");

        } catch (IOException e) {
            LOG.error("SERVER - Exception: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(AppServer.CONNECTION_PROVIDER);
    }
}
