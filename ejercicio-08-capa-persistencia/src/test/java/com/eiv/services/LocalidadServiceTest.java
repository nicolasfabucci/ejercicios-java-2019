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

import com.eiv.dtos.LocalidadFrmDto;
import com.eiv.entities.LocalidadEntity;
import com.eiv.entities.ProvinciaEntity;
import com.eiv.repositories.LocalidadRepository;
import com.eiv.repositories.ProvinciaRepository;

@RunWith(MockitoJUnitRunner.class)
public class LocalidadServiceTest {

    @InjectMocks private LocalidadService localidadService;

    @Mock private DataSource dataSource;
    
    @Mock private ProvinciaRepository provinciaRepository;
    @Mock private LocalidadRepository localidadRepository;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenLocalidadFrmDto_whenCreate_thenLocalidadEntityCreated() {

        Mockito.when(localidadRepository.maxId())
                .thenReturn(Optional.of(0L));

        Mockito.when(provinciaRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new ProvinciaEntity(0L, "PROVINCIA-TEST")));

        
        LocalidadEntity localidadEntity = localidadService.create(new LocalidadFrmDto() {
            @Override
            public Long getProvinciaId() {
                return 0L;
            }
            
            @Override
            public String getNombre() {
                return "LOCALIDAD-TEST";
            }
        });
        
        assertThat(localidadEntity.getId()).isEqualTo(1L);
        assertThat(localidadEntity.getNombre()).isEqualTo("LOCALIDAD-TEST");
        assertThat(localidadEntity.getProvincia()).isNotNull();
        assertThat(localidadEntity.getProvincia().getId()).isEqualTo(0L);
        assertThat(localidadEntity.getProvincia().getNombre()).isEqualTo("PROVINCIA-TEST");
        
        Mockito.verify(provinciaRepository).findById(Mockito.eq(0L));
        Mockito.verify(localidadRepository).save(Mockito.eq(localidadEntity));
    }

    @Test
    public void givenLocalidadFrmDto_whenCreateAndProvinciaDoesNotExist_thenThrowException() {

        Mockito.when(localidadRepository.maxId())
                .thenReturn(Optional.of(0L));

        Mockito.when(provinciaRepository.findById(Mockito.eq(0L)))
                .thenReturn(Optional.empty());

        
        Throwable throwable = catchThrowable(() -> localidadService.create(new LocalidadFrmDto() {
            @Override
            public Long getProvinciaId() {
                return 0L;
            }
            
            @Override
            public String getNombre() {
                return "LOCALIDAD-TEST";
            }
        }));
        
        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("NO EXISTE UNA PROVINCIA CON ID 0");
        
        Mockito.verify(provinciaRepository).findById(Mockito.eq(0L));
        Mockito.verify(localidadRepository, never()).save(Mockito.any(LocalidadEntity.class));
    }

    @Test
    public void givenLocalidadFrmDto_whenUpdateNombre_thenLocalidadEntityUpdated() {
        
        final ProvinciaEntity provinciaEntity = new ProvinciaEntity(0L, "PROVINCIA-TEST");
        final LocalidadEntity localidadEntity = 
                new LocalidadEntity(0L, "LOCALIDAD-TEST", provinciaEntity);
        
        Mockito.when(localidadRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(localidadEntity));
        
        LocalidadEntity updated = localidadService.update(0L, new LocalidadFrmDto() {
            @Override
            public String getNombre() {
                return "UPDATE-TEST";
            }

            @Override
            public Long getProvinciaId() {
                return 0L;
            }
        });
        
        assertThat(updated.getId()).isEqualTo(0L);
        assertThat(updated.getNombre()).isEqualTo("UPDATE-TEST");
        assertThat(updated.getProvincia()).isEqualTo(provinciaEntity);
        
        Mockito.verify(localidadRepository).save(Mockito.eq(localidadEntity));
        Mockito.verify(provinciaRepository, never()).findById(Mockito.any(Long.class));
    }

    @Test
    public void givenLocalidadFrmDto_whenUpdateProvincia_thenLocalidadEntityUpdated() {
        
        final ProvinciaEntity provinciaOldEntity = new ProvinciaEntity(0L, "OLD-TEST");
        final ProvinciaEntity provinciaNewEntity = new ProvinciaEntity(1L, "NEW-TEST");
        final LocalidadEntity localidadEntity = 
                new LocalidadEntity(0L, "LOCALIDAD-TEST", provinciaOldEntity);
        
        Mockito.when(provinciaRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(provinciaOldEntity));

        Mockito.when(provinciaRepository
                .findById(Mockito.eq(1L)))
                .thenReturn(Optional.of(provinciaNewEntity));

        Mockito.when(localidadRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(localidadEntity));
        
        LocalidadEntity updated = localidadService.update(0L, new LocalidadFrmDto() {
            @Override
            public String getNombre() {
                return "LOCALIDAD-TEST";
            }

            @Override
            public Long getProvinciaId() {
                return 1L;
            }
        });
        
        assertThat(updated.getId()).isEqualTo(0L);
        assertThat(updated.getNombre()).isEqualTo("LOCALIDAD-TEST");
        assertThat(updated.getProvincia()).isEqualTo(provinciaNewEntity);
        
        Mockito.verify(localidadRepository).save(Mockito.eq(localidadEntity));
        Mockito.verify(provinciaRepository).findById(Mockito.eq(1L));
    }

    @Test
    public void givenLocalidad_thenDelete() {

        final ProvinciaEntity provinciaEntity = new ProvinciaEntity(0L, "PROVINCIA-TEST");
        final LocalidadEntity localidadEntity = 
                new LocalidadEntity(0L, "LOCALIDAD-TEST", provinciaEntity);

        Mockito.when(localidadRepository
                .findById(Mockito.anyLong()))
                .thenReturn(Optional.of(localidadEntity));
        
        localidadService.delete(0L);
        
        Mockito.verify(localidadRepository).findById(Mockito.eq(0L));
        Mockito.verify(localidadRepository).delete(Mockito.eq(localidadEntity));
    }

    @Test
    public void givenLocalidad_whenDeleteNonExist_thenThrowException() {

        Throwable throwable = catchThrowable(() -> localidadService.delete(0L));
        
        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("NO EXISTE UNA LOCALIDAD CON ID 0");

        Mockito.verify(localidadRepository).findById(Mockito.eq(0L));
        Mockito.verify(localidadRepository, never()).delete(Mockito.any(LocalidadEntity.class));
    }
}
