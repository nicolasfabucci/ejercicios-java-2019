package com.eiv.repositories;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;

import com.eiv.entities.ProvinciaEntity;

@Repository
public class ProvinciaRepository implements CrudRepository<ProvinciaEntity, Long> {

    private static final String SQL_FIND_BY_ID = "SELECT * FROM provincias WHERE id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM provincias";
    private static final String SQL_MAX_ID = "SELECT MAX(id) FROM provincias";
    private static final String SQL_INSERT = "INSERT INTO provincias (id, nombre) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE provincias SET nombre = ? WHERE id = ?";
    
    private final RowMapper<ProvinciaEntity> rowMapper = (rs, row) -> {
        long id = rs.getLong("id");
        String nombre = rs.getString("nombre");
        return new ProvinciaEntity(id, nombre);
    };
    
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public ProvinciaRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Long> maxId() {
        
        Long maxId = jdbcTemplate.queryForObject(SQL_MAX_ID, Long.class);
        
        if(maxId == null) {
            return Optional.empty();
        } else {
            return Optional.of(maxId);
        }
    }
    
    @Override
    public Optional<ProvinciaEntity> findById(Long id) {
        
        SqlParameterValue paramId = new SqlParameterValue(Types.INTEGER, id);
        
        try {
            ProvinciaEntity provinciaEntity = jdbcTemplate.queryForObject(
                    SQL_FIND_BY_ID, rowMapper, paramId);
            
            return Optional.of(provinciaEntity);
            
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ProvinciaEntity> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public void save(ProvinciaEntity provinciaEntity) {
        
        Optional<ProvinciaEntity> optional = findById(provinciaEntity.getId());
        
        if (optional.isPresent()) {
            
            ProvinciaEntity entity = optional.get();
            entity.setNombre(provinciaEntity.getNombre());
            jdbcTemplate.update(SQL_UPDATE, entity.getNombre(), entity.getId());
            
        } else {
            
            long id = provinciaEntity.getId() == -1 
                    ? maxId().orElse(0L) + 1L : provinciaEntity.getId();
            String nombre = provinciaEntity.getNombre();
            
            provinciaEntity.setId(id);
            provinciaEntity.setNombre(nombre);
            
            jdbcTemplate.update(SQL_INSERT, provinciaEntity.getId(), provinciaEntity.getNombre());
        }
    }
}
