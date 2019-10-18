package com.eiv.services;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.dtos.ProvinciaFrmDto;
import com.eiv.entities.ProvinciaEntity;
import com.eiv.repositories.ProvinciaRepository;

@Service
public class ProvinciaService {

    private ProvinciaRepository provinciaRepository;
    
    public ProvinciaService(DataSource dataSource) {
        this.provinciaRepository = new ProvinciaRepository(dataSource);
    }
    
    @Transactional(readOnly = true)
    public Optional<ProvinciaEntity> findById(long id) {
        return provinciaRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<ProvinciaEntity> findAll() {
        return provinciaRepository.findAll();
    }
    
    @Transactional
    public ProvinciaEntity create(ProvinciaFrmDto provinciaFrmDto) {
        
        long id = provinciaRepository.maxId().orElse(0L) + 1;
        String nombre = provinciaFrmDto.getNombre();
        
        ProvinciaEntity provinciaEntity = new ProvinciaEntity(id, nombre);
        
        provinciaRepository.save(provinciaEntity);
        
        return provinciaEntity;
    }
    
    @Transactional
    public ProvinciaEntity update(Long id, ProvinciaFrmDto provinciaFrmDto) {
        
        ProvinciaEntity provinciaEntity = provinciaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("NO EXISTE UNA PROVINCIA CON ID %s", id)));
        
        provinciaEntity.setNombre(provinciaFrmDto.getNombre());
        
        provinciaRepository.save(provinciaEntity);
        
        return provinciaEntity;
    }
    
    @Transactional
    public void delete(Long id) {
        
        ProvinciaEntity provinciaEntity = provinciaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("NO EXISTE UNA PROVINCIA CON ID %s", id)));
        
        provinciaRepository.delete(provinciaEntity);
    }
}
