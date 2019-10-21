package com.eiv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class App {
    
    private static final String SQL_QUERY_ALT_1 = "select * from personas";
    private static final String SQL_QUERY_ALT_2 = "select * from personas where tdi_cod = ?";
    
    private static final Logger LOG = LogManager.getLogger(App.class);
    private static final Properties PROPS;
    
    static {
        
        String propsPath = System.getProperty("propsFile");
        InputStream in = null;
        
        if (propsPath == null) {
            in = App.class.getClassLoader().getResourceAsStream("application.properties");
        } else {
            LOG.info("System property: {}", propsPath);
            File f = new File(propsPath);
            if (f.exists()) {
                try {
                    in = new FileInputStream(f);
                } catch (FileNotFoundException e) {
                    LOG.error(e.getMessage());
                }
            }
        }
        
        PROPS = new Properties();
        try {
            PROPS.load(in);
        } catch (IOException e) {
            LOG.error("Archivo de propiedades: {}", e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        App app = new App();
        app.run2();
    }
    
    public void run1() {
        
        DataSource dataSource = getDataSource();
            
        List<PersonaEntity> personaEntities = new JdbcTemplate(dataSource)
                .query(SQL_QUERY_ALT_1, (rs, row) -> {
                   
                    int tipoDocumentoId = rs.getInt("tdi_cod");
                    long numeroDocumento = rs.getLong("nro_doc");
                    String nombres = rs.getString("nom_ape");
                    
                    PersonaEntity personaEntity = new PersonaEntity(
                            tipoDocumentoId, numeroDocumento, nombres);
                    
                    return personaEntity;
                });
        
        personaEntities.forEach(personaEntity -> {
            LOG.info("Persona: {}", personaEntity);
        });
    }

    public void run2() {
        
        DataSource dataSource = getDataSource();
        
        SqlParameter sqlParameter = new MapSqlParameter();
        sqlParameter.addValue(1, 10, Integer.class);
            
        List<PersonaEntity> personaEntities = new JdbcTemplate(dataSource)
                .query(SQL_QUERY_ALT_2, sqlParameter, (rs, row) -> {
                   
                    int tipoDocumentoId = rs.getInt("tdi_cod");
                    long numeroDocumento = rs.getLong("nro_doc");
                    String nombres = rs.getString("nom_ape");
                    
                    PersonaEntity personaEntity = new PersonaEntity(
                            tipoDocumentoId, numeroDocumento, nombres);
                    
                    return personaEntity;
                });
        
        personaEntities.forEach(personaEntity -> {
            LOG.info("Persona: {}", personaEntity);
        });
    }
    
    private DataSource getDataSource() {
        
        SQLServerDataSource ds = new SQLServerDataSource();
        
        ds.setURL(PROPS.getProperty("datasource.url"));
        ds.setUser(PROPS.getProperty("datasource.user"));
        ds.setPassword(PROPS.getProperty("datasource.password"));
        ds.setDatabaseName(PROPS.getProperty("datasource.database"));
        
        return ds;
    }
}
