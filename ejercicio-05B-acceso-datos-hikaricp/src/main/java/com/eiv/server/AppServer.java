package com.eiv.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppServer {

    public static final ConnectionProvider CONNECTION_PROVIDER;
    private static final Logger LOG = LogManager.getLogger(AppServer.class);
    
    static {
        CONNECTION_PROVIDER = new ConnectionProviderImpl();
    }
    
    public static void main(String[] args) {
        AppServer app = new AppServer();
        
        try {
            app.run();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public void run() throws IOException {
        
        try(ServerSocket serverSocket = new ServerSocket(9000)) {
            
            LOG.info("Iniciando servidor ...");
            
            while (true) {
                
                Socket clientSocket = null;
                
                try {
                    
                    clientSocket = serverSocket.accept();
                    LOG.info("Cliente conectado desde {}", clientSocket.getInetAddress());
                    
                    InputStream in = clientSocket.getInputStream();
                    OutputStream out = clientSocket.getOutputStream();
                    
                    Thread t = new ClientHandler(clientSocket, out, in); 
                    t.start(); 
                    
                    
                } catch (IOException e) {
                    LOG.error("SERVER - Exception: {}", e.getMessage());
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }
}
