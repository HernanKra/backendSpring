package com.asj.backend.service;

import com.asj.backend.dto.UsuarioDTO;
import com.asj.backend.dto.UsuarioLoginDTO;
import com.asj.backend.entity.Pelicula;
import com.asj.backend.entity.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Usuario getUsuario(Integer id);

    Optional<Usuario> getUsuarioByUsername(String username);

    Usuario createUsuario(Usuario user);

    Usuario loginUsuario(UsuarioLoginDTO usuarioLoginDTO);

    Usuario updateUsuario(Integer id, UsuarioDTO userdto);

    void deleteUsuario(Integer id);

    Usuario addPeliculasWishlist(Integer id, Pelicula pelicula);

    Usuario addPeliculasFavorite(Integer id, Pelicula pelicula);

    void deletePeliculasWishlist(Integer idUsuario, Integer idPelicula);

    void deletePeliculasFavorite(Integer idUsuario, Integer idPelicula);

}
