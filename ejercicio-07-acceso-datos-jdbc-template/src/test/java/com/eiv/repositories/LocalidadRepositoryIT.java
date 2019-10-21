package com.eiv.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.entities.LocalidadEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LocalidadRepositoryIT.TestCfg.class)
public class LocalidadRepositoryIT {

    @Configuration
    @EnableTransactionManagement
    public static class TestCfg {
        
        @Bean
        public DataSource getDataSource() {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setUrl("jdbc:h2:mem:testdb"
                    + ";INIT=runscript from 'src/test/resources/test-localidades.sql'");
            ds.setUser("sa");
            return ds;
        }
        
        @Bean
        public PlatformTransactionManager transactionManager() {
            return new DataSourceTransactionManager(getDataSource());
        }
        
        @Bean
        public ProvinciaRepository getProvinciaRepository() {
            return new ProvinciaRepository(getDataSource());
        }
    }
    
    @Test
    public void whenLocalidadId1_thenIsPresent() {
        
        LocalidadRepository localidadRepository = new LocalidadRepository(dataSource);
        Optional<LocalidadEntity> optional = localidadRepository.findById(1L);
        
        assertThat(optional).isPresent();
    }

    @Test
    public void whenLocalidadId10_thenIsNotPresent() {
        
        LocalidadRepository localidadRepository = new LocalidadRepository(dataSource);
        Optional<LocalidadEntity> optional = localidadRepository.findById(10L);
        
        assertThat(optional).isNotPresent();
    }

    @Test
    public void givenAllLocalidades_whenFindById_thenFindLocalidad() {
        
        LocalidadRepository localidadRepository = new LocalidadRepository(dataSource);
        List<LocalidadEntity> localidadEntities = localidadRepository.findAll();
        
        assertThat(localidadEntities).hasSize(3);
        
        localidadEntities.forEach(item -> {
            
            Optional<LocalidadEntity> optional = localidadRepository.findById(item.getId());
            
            assertThat(optional)
                    .contains(item);
        });
    }

    @Test
    public void whenFindMaxId_thenMaxId3() {
        
        LocalidadRepository localidadRepository = new LocalidadRepository(dataSource);
        Optional<Long> optional = localidadRepository.maxId();
        
        assertThat(optional).isPresent().contains(3L);
    }

    @Test
    @Transactional
    public void givenNewLocalidad_whenId_thenSave() {
        
        LocalidadEntity entity = LocalidadEntity.Builder.newInstance()
                .setId(10L)
                .setNombre("TEST")
                .setProvinciaId(1L)
                .build();
        
        LocalidadRepository localidadRepository = new LocalidadRepository(dataSource);
        localidadRepository.setProvinciaRepository(provinciaRepository);
        localidadRepository.save(entity);
        
        Optional<LocalidadEntity> optional = localidadRepository.findById(10L);
        assertThat(optional).contains(entity);
    }

    @Test
    @Transactional
    public void givenNewLocalidad_whenNoId_thenSave() {
        
        LocalidadEntity entity = LocalidadEntity.Builder.newInstance()
                .setId(-1L)
                .setNombre("TEST")
                .setProvinciaId(1L)
                .build();
        
        LocalidadRepository localidadRepository = new LocalidadRepository(dataSource);
        localidadRepository.setProvinciaRepository(provinciaRepository);
        localidadRepository.save(entity);
        
        Optional<LocalidadEntity> optional = localidadRepository.findById(entity.getId());
        assertThat(optional).contains(entity);
    }

    @Test
    @Transactional
    public void givenExistLocalidad_thenUpdated() {
        
        final long LOCALIDAD_ID = 1L;
        final String LOCALIDAD_NOMBRE = "TEST";
        
        LocalidadRepository localidadRepository = new LocalidadRepository(dataSource);
        localidadRepository.setProvinciaRepository(provinciaRepository);
        
        Optional<LocalidadEntity> optional = localidadRepository.findById(LOCALIDAD_ID);
        assertThat(optional).isPresent();
        
        LocalidadEntity entity = optional.get();
        
        entity.setNombre(LOCALIDAD_NOMBRE);
        localidadRepository.save(entity);
        
        assertThat(entity.getNombre()).isEqualTo(LOCALIDAD_NOMBRE);
        
        Optional<LocalidadEntity> saved = localidadRepository.findById(LOCALIDAD_ID);
        assertThat(saved).isPresent().contains(entity);
        
        assertThat(saved.get().getNombre()).isEqualTo(LOCALIDAD_NOMBRE);
        
    }
    
    @Autowired DataSource dataSource;
    @Autowired ProvinciaRepository provinciaRepository;
}
