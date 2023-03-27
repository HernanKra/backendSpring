package com.asj.backend.repository;


import com.asj.backend.datos.DatosDummy;
import com.asj.backend.entity.Pelicula;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PeliculaRepositoryTest {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @BeforeEach
    void setUp() {
        peliculaRepository.save(DatosDummy.getPelicula());
    }

    @AfterEach
    void tearDown() {
        peliculaRepository.deleteAll();
    }

    @Test
    void findById() {
        Optional<Pelicula> optionalPelicula = this.peliculaRepository.findById(1);

        assertThat(optionalPelicula.isPresent()).isTrue();
        assertThat(optionalPelicula.get().getTitulo()).isEqualTo("Avatar");
        assertThat(optionalPelicula.get().getValoracion()).isEqualTo(7);
        assertThat(optionalPelicula.get().getDetalle()).isEqualTo("esto es avatar");
    }
}
