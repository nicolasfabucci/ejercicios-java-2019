package com.eiv.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.never;

import java.util.Optional;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.eiv.dtos.ProvinciaFrmDto;
import com.eiv.entities.ProvinciaEntity;
import com.eiv.repositories.ProvinciaRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProvinciaServiceTest {

    @InjectMocks private ProvinciaService provinciaService;
    
    @Mock private DataSource dataSource;
    @Mock private ProvinciaRepository provinciaRepository;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void givenProvinciaFrmDto_whenCreate_thenProvinciaEntityCreated() {
        
        Mockito.when(provinciaRepository.maxId()).thenReturn(Optional.of(0L));
        
        ProvinciaEntity provinciaEntity = provinciaService.create(new ProvinciaFrmDto() {    
            @Override
            public String getNombre() {
                return "TEST";
            }
        });
        
        assertThat(provinciaEntity.getId()).isEqualTo(1L);
        assertThat(provinciaEntity.getNombre()).isEqualTo("TEST");
    }

    @Test
    public void givenProvinciaFrmDto_whenUpdate_thenProvinciaEntityUpdated() {
        
        Mockito.when(provinciaRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new ProvinciaEntity(0L, "ORIGINAL")));
        
        ProvinciaEntity provinciaEntity = provinciaService.update(0L, new ProvinciaFrmDto() {
            @Override
            public String getNombre() {
                return "TEST";
            }
        });
        
        assertThat(provinciaEntity.getId()).isEqualTo(0L);
        assertThat(provinciaEntity.getNombre()).isEqualTo("TEST");
    }

    @Test
    public void givenProvinciaFrmDto_whenUpdateNonExist_thenThrowException() {
        
        Throwable throwable = catchThrowable(() -> provinciaService.update(
                0L, new ProvinciaFrmDto() {
                    @Override
                    public String getNombre() {
                        return "TEST";
                    }
                }));
        
        
        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("NO EXISTE UNA PROVINCIA CON ID 0");
        
    }
    
    @Test
    public void givenProvincia_thenDelete() {

        ProvinciaEntity provinciaEntity = new ProvinciaEntity(0L, "TEST");
        
        Mockito.when(provinciaRepository
                .findById(Mockito.eq(0L)))
                .thenReturn(Optional.of(provinciaEntity));
        
        provinciaService.delete(0L);
        
        Mockito.verify(provinciaRepository).findById(Mockito.eq(0L));
        Mockito.verify(provinciaRepository).delete(Mockito.eq(provinciaEntity));
    }

    @Test
    public void givenProvincia_whenDeleteNonExist_thenThrowException() {

        Throwable throwable = catchThrowable(() -> provinciaService.delete(0L));
        
        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("NO EXISTE UNA PROVINCIA CON ID 0");

        Mockito.verify(provinciaRepository).findById(Mockito.eq(0L));
        Mockito.verify(provinciaRepository, never()).delete(Mockito.any(ProvinciaEntity.class));
    }
}
