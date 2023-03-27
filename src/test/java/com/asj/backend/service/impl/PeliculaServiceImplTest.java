package com.asj.backend.service.impl;

import com.asj.backend.datos.DatosDummy;
import com.asj.backend.entity.Pelicula;
import com.asj.backend.repository.PeliculaRepository;
import com.asj.backend.service.PeliculaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class PeliculaServiceImplTest {

    @Mock
    private PeliculaRepository peliculaRepository;
    private PeliculaService peliculaService;


    @BeforeEach
    void setUp() {
        peliculaRepository = mock(PeliculaRepository.class);
        peliculaService = new PeliculaServiceImpl(peliculaRepository);
    }

    @Test
    void getPelicula() {
        Integer idPelicula = 1;

        when(peliculaRepository.findById(idPelicula)).thenReturn(Optional.of(DatosDummy.getPelicula()));
        Pelicula pelicula = peliculaService.getPelicula(idPelicula);

        assertThat(pelicula.getIdPelicula()).isEqualTo(1);
    }

    @Test
    void getPeliculaError() {
        Integer idPelicula = 2;

        when(peliculaRepository.findById(1)).thenReturn(Optional.of(DatosDummy.getPelicula()));

        assertThatThrownBy(() -> peliculaService.getPelicula(idPelicula)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void createPelicula() {
        Pelicula pelicula = DatosDummy.getPelicula();
        peliculaService.createPelicula(pelicula);

        ArgumentCaptor<Pelicula> peliculaArgumentCaptor = ArgumentCaptor.forClass(Pelicula.class);
        verify(peliculaRepository).save(peliculaArgumentCaptor.capture());

        Pelicula peliculaCaptor = peliculaArgumentCaptor.getValue();

        assertThat(peliculaCaptor).isEqualTo(pelicula);

        verify(peliculaRepository).save(any());
    }

    @Test
    void createPeliculaError() {
        Pelicula pelicula = DatosDummy.getPelicula();
        given(peliculaRepository.findById(pelicula.getIdPelicula())).willReturn(Optional.of(pelicula));
        assertThatThrownBy(() -> peliculaService.createPelicula(pelicula)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deletePelicula() {
        when(peliculaRepository.findById(1)).thenReturn(Optional.of(DatosDummy.getPelicula()));
        peliculaService.deletePelicula(1);
        verify(peliculaRepository).findById(1);

    }
}