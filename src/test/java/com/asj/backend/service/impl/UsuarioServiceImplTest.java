package com.asj.backend.service.impl;

import com.asj.backend.datos.DatosDummy;
import com.asj.backend.dto.UsuarioDTO;
import com.asj.backend.entity.Usuario;
import com.asj.backend.repository.PeliculaRepository;
import com.asj.backend.repository.UsuarioRepository;
import com.asj.backend.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UsuarioServiceImplTest {

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private PeliculaRepository peliculaRepository;

    @Autowired
    private UsuarioService usuarioService;


    @Test
    void getUsuario() {
        Integer idUsuario = 1;

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(DatosDummy.getUsuario()));
        Usuario usuario = usuarioService.getUsuario(idUsuario);

        assertThat(usuario.getIdUser()).isEqualTo(1);

    }

    @Test
    void createUsuario() {
        Usuario usuario = DatosDummy.getUsuario();
        usuarioService.createUsuario(usuario);

        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(usuarioArgumentCaptor.capture());

        Usuario userCaptor = usuarioArgumentCaptor.getValue();

        assertThat(userCaptor).isEqualTo(usuario);

        verify(usuarioRepository).save(any());
    }

    @Test
    void loginUsuario() {
    }

    @Test
    void updateUsuario() {


    }

    @Test
    void deleteUsuario() {
    }

    @Test
    void addPeliculasWishlist() {
    }

    @Test
    void addPeliculasFavorite() {
    }

    @Test
    void deletePeliculasWishlist() {
    }

    @Test
    void deletePeliculasFavorite() {
    }
}