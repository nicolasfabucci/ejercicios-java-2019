package com.eiv.repositories;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.eiv.entities.LocalidadEntity;
import com.eiv.entities.ProvinciaEntity;

@Repository
public class LocalidadRepository implements CrudRepository<LocalidadEntity, Long> {

    private static final String SQL_FIND_BY_ID = 
            "SELECT l.id, l.nombre, p.id as p_id, p.nombre as p_nombre FROM localidades l "
            + "INNER JOIN provincias p ON l.provincia_id = p.id WHERE l.id = :id";
    
    private static final String SQL_FIND_ALL = 
            "SELECT l.id, l.nombre, p.id as p_id, p.nombre as p_nombre FROM localidades l "
            + "INNER JOIN provincias p ON l.provincia_id = p.id";
    
    private static final String SQL_MAX_ID = "SELECT MAX(id) FROM localidades";
    
    private static final String SQL_INSERT = "INSERT INTO localidades (id, nombre, provincia_id) "
            + "VALUES (:id, :nombre, :provincia_id)";
    
    private static final String SQL_UPDATE = "UPDATE localidades "
            + "SET nombre = :nombre, provincia_id = :provincia_id "
            + "WHERE id = :id";
    
    private static final String SQL_DELETE = "DELETE FROM localidades WHERE id = :id";
    
    private final RowMapper<LocalidadEntity> rowMapper = (rs, row) -> {
        
        long id = rs.getLong("id");
        String nombre = rs.getString("nombre");
        long provinciaId = rs.getLong("p_id");
        String provinciaNombre = rs.getString("p_nombre");
        
        return LocalidadEntity.Builder.newInstance()
                .setId(id)
                .setNombre(nombre)
                .setProvinciaId(provinciaId)
                .setProvinciaNombre(provinciaNombre)
                .build();
    };

    private DataSource dataSource;
    private ProvinciaRepository provinciaRepository;
    
    @Autowired
    public LocalidadRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Autowired
    public void setProvinciaRepository(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    @Override
    public Optional<LocalidadEntity> findById(Long id) {

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        
        try {
            LocalidadEntity localidadEntity = jdbcTemplate.queryForObject(
                    SQL_FIND_BY_ID, params, rowMapper);
            
            return Optional.of(localidadEntity);
            
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> maxId() {
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Long maxId = jdbcTemplate.queryForObject(SQL_MAX_ID, Long.class);
        
        if (maxId == null) {
            return Optional.empty();
        } else {
            return Optional.of(maxId);
        }
    }

    @Override
    public List<LocalidadEntity> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public void save(LocalidadEntity localidadEntity) {

        Optional<LocalidadEntity> optional = findById(localidadEntity.getId());

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
                
        if (optional.isPresent()) {
            
            LocalidadEntity entity = optional.get();
            
            entity.setNombre(localidadEntity.getNombre());
            
            if (localidadEntity.getProvincia().getId() != entity.getProvincia().getId()) {
                provinciaRepository
                        .findById(localidadEntity.getProvincia().getId())
                        .ifPresent(p -> {
                            entity.setProvincia(p);
                        });
            }
            
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", entity.getId())
                    .addValue("nombre", entity.getNombre())
                    .addValue("provincia_id", entity.getProvincia().getId());
            
            jdbcTemplate.update(SQL_UPDATE, params);
            
        } else {
            
            long id = localidadEntity.getId() == -1 
                    ? maxId().orElse(0L) + 1L : localidadEntity.getId();
            
            String nombre = localidadEntity.getNombre();
            ProvinciaEntity provinciaEntity = provinciaRepository
                    .findById(localidadEntity.getProvincia().getId())
                    .get();
            
            localidadEntity.setId(id);
            localidadEntity.setNombre(nombre);
            localidadEntity.setProvincia(provinciaEntity);
            
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", localidadEntity.getId())
                    .addValue("nombre", localidadEntity.getNombre())
                    .addValue("provincia_id", localidadEntity.getProvincia().getId());
            
            jdbcTemplate.update(SQL_INSERT, params);
        }
    }

    @Override
    public void delete(LocalidadEntity localidadEntity) {

        Optional<LocalidadEntity> optional = findById(localidadEntity.getId());
        
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        
        if (optional.isPresent()) {
            
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", localidadEntity.getId());
                    
            jdbcTemplate.update(SQL_DELETE, params);
        }
    }
    
}
