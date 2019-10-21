package com.eiv.services;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.dtos.LocalidadFrmDto;
import com.eiv.entities.LocalidadEntity;
import com.eiv.entities.ProvinciaEntity;
import com.eiv.repositories.LocalidadRepository;
import com.eiv.repositories.ProvinciaRepository;

@Service
public class LocalidadService {

    private ProvinciaRepository provinciaRepository;
    private LocalidadRepository localidadRepository;
    
    public LocalidadService(DataSource dataSource) {
        this.provinciaRepository = new ProvinciaRepository(dataSource);
        this.localidadRepository = new LocalidadRepository(dataSource);
    }
    
    @Transactional(readOnly = true)
    public Optional<LocalidadEntity> findById(long id) {
        return localidadRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<LocalidadEntity> findAll() {
        return localidadRepository.findAll();
    }
    
    @Transactional
    public LocalidadEntity create(LocalidadFrmDto localidadFrmDto) {
        
        long id = localidadRepository.maxId().orElse(0L) + 1;
        String nombre = localidadFrmDto.getNombre();
        ProvinciaEntity provinciaEntity = provinciaRepository
                .findById(localidadFrmDto.getProvinciaId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("NO EXISTE UNA PROVINCIA CON ID %s", 
                                localidadFrmDto.getProvinciaId())));
        
        LocalidadEntity localidadEntity = new LocalidadEntity(id, nombre, provinciaEntity);
        
        localidadRepository.save(localidadEntity);
        
        return localidadEntity;
    }

    @Transactional
    public LocalidadEntity update(Long id, LocalidadFrmDto localidadFrmDto) {
        
        final LocalidadEntity localidadEntity = localidadRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("NO EXISTE UNA LOCALIDAD CON ID %s", id)));

        localidadEntity.setNombre(localidadFrmDto.getNombre());
        
        if (!localidadFrmDto.getProvinciaId().equals(localidadEntity.getProvincia().getId())) {
            
            ProvinciaEntity provinciaEntity = provinciaRepository
                    .findById(localidadFrmDto.getProvinciaId())
                    .orElseThrow(() -> new RuntimeException(
                            String.format("NO EXISTE UNA PROVINCIA CON ID %s", 
                                    localidadFrmDto.getProvinciaId())));
    
            localidadEntity.setProvincia(provinciaEntity);
        }
        
        localidadRepository.save(localidadEntity);
        
        return localidadEntity;
    }

    @Transactional
    public void delete(Long id) {
        
        LocalidadEntity localidadEntity = localidadRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("NO EXISTE UNA LOCALIDAD CON ID %s", id)));
        
        localidadRepository.delete(localidadEntity);
    }
}
