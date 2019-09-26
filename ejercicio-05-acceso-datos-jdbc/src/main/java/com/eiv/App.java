package com.eiv;

import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class App {
    
    private static final String SQL_QUERY_ALT_1 = "select * from personas";
    private static final String SQL_QUERY_ALT_2 = "select * from personas where tdi_cod = ?";
    
    private static final Logger LOG = LogManager.getLogger(App.class);
    
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
        
        ds.setURL("jdbc:sqlserver://sqlserver\\sql2008");
        ds.setUser("sa");
        ds.setPassword("rv760");
        ds.setDatabaseName("DESARROLLO_MUTUAL");
        
        return ds;
    }
}
