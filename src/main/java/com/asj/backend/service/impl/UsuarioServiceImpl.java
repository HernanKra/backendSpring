package com.asj.backend.service.impl;

import com.asj.backend.dto.UsuarioDTO;
import com.asj.backend.dto.UsuarioLoginDTO;
import com.asj.backend.entity.Pelicula;
import com.asj.backend.entity.Usuario;
import com.asj.backend.repository.PeliculaRepository;
import com.asj.backend.repository.UsuarioRepository;
import com.asj.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final PeliculaRepository repositoryPelicula;

    public UsuarioServiceImpl(UsuarioRepository repository, PeliculaRepository repositoryPelicula) {
        this.repository = repository;
        this.repositoryPelicula = repositoryPelicula;
    }

    @Override
    public Usuario getUsuario(Integer id) {
        Optional<Usuario> user = repository.findById(id);
        if(user.isPresent()) {
            return user.get();
        } else {
          throw new RuntimeException(String.format("Usuario con id %s no encontrado", id));
        }

    }

    @Override
    public Usuario createUsuario(Usuario user) {

        Optional<Usuario> tmp = repository.findUsuarioByUsername(user.getUsername());
        if(tmp.isEmpty()) {
            repository.save(user);
        } else {
            throw new RuntimeException(String.format("El username: %s no se encuentra disponible", user.getUsername()));
        }
        return user;
    }

    @Override
    public Usuario loginUsuario(UsuarioLoginDTO usuarioLoginDTO) throws HttpClientErrorException {

        if(repository.findUsuarioByUsernameAndPassword(usuarioLoginDTO.getUsername(), usuarioLoginDTO.getPassword()).isPresent()) {
            return repository.findUsuarioByUsername(usuarioLoginDTO.getUsername()).get();
        } else {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Credenciales incorrectas");
        }
    }

    @Override
    public Usuario updateUsuario(Integer id,
                                 UsuarioDTO userDTO) {

        Usuario userUpdate;
        Optional<Usuario> usuarioOptional = repository.findById(id);

        if (repository.findUsuarioByUsername(userDTO.getUsername()).isPresent() && !(usuarioOptional.get().getUsername().equals(userDTO.getUsername()))) {
            throw new RuntimeException(String.format("El username: %s no se encuentra disponible", userDTO.getUsername()));
        } else {
            userUpdate = usuarioOptional.get();
            userUpdate.setUsername(userDTO.getUsername());
            userUpdate.setNombre(userDTO.getNombre());
            userUpdate.setApellido(userDTO.getApellido());
        }
        return repository.save(userUpdate);
    }


    @Override
    public void deleteUsuario(Integer id) {

        Optional<Usuario> tmp = repository.findById(id);

        if(tmp.isPresent()) {
            Usuario userDelete = repository.getReferenceById(id);
            repository.delete(userDelete);
        } else {
            throw new RuntimeException(String.format("Usuario con id %s no encontrado", id));
        }

    }

    @Override
    public Usuario addPeliculasWishlist(Integer id,
                                        Pelicula pelicula) {

        Optional<Usuario> user = repository.findById(id);
        Optional<Pelicula> peliculaOptional = repositoryPelicula.findById(pelicula.getIdPelicula());
        boolean existPeliculaInUsuario = repository.existPeliculaInUsuarioWishlist(id , pelicula.getIdPelicula());

        if(peliculaOptional.isEmpty()) {
            repositoryPelicula.save(pelicula);
        }
        if(existPeliculaInUsuario) {
            throw new RuntimeException(String.format("%s ya se encuentra en la lista", pelicula.getTitulo()));
        } else {
            Usuario usuario = user.get();
            usuario.getPeliculas_wishlist().add(pelicula);
            repository.save(usuario);
            return usuario;
        }
       }


    @Override
    public Usuario addPeliculasFavorite(Integer id,
                                        Pelicula pelicula) {

        Optional<Usuario> user = repository.findById(id);
        Optional<Pelicula> peliculaOptional = repositoryPelicula.findById(pelicula.getIdPelicula());
        boolean existPeliculaInUsuario = repository.existPeliculaInUsuarioFavorite(id , pelicula.getIdPelicula());

        if(peliculaOptional.isEmpty()) {
            repositoryPelicula.save(pelicula);
        }
        if(existPeliculaInUsuario) {
            throw new RuntimeException(String.format("%s ya se encuentra en la lista", pelicula.getTitulo()));
        } else {
            Usuario usuario = user.get();
            usuario.getPeliculas_favoritas().add(pelicula);
            repository.save(usuario);
            return usuario;
        }
    }

    @Override
    public void deletePeliculasWishlist(Integer idUsuario,
                                        Integer idPelicula) {
        Optional<Usuario> usuarioOptional = repository.findById(idUsuario);
        Optional<Pelicula> peliculaOptional = repositoryPelicula.findById(idPelicula);

        if(peliculaOptional.isPresent() && usuarioOptional.isPresent()) {
           boolean contienePelicula = repository.existPeliculaInUsuarioWishlist(idUsuario, idPelicula);
           if(contienePelicula) {
               Usuario user = usuarioOptional.get();
               user.getPeliculas_wishlist().remove(peliculaOptional.get());
               repository.save(user);
           }
        }
    }

    @Override
    public void deletePeliculasFavorite(Integer idUsuario,
                                        Integer idPelicula) {
        Optional<Usuario> usuarioOptional = repository.findById(idUsuario);
        Optional<Pelicula> peliculaOptional = repositoryPelicula.findById(idPelicula);

        if(usuarioOptional.isPresent() && peliculaOptional.isPresent()) {
            boolean contienePelicula = repository.existPeliculaInUsuarioFavorite(idUsuario, idPelicula);
            if(contienePelicula) {
                Usuario user = usuarioOptional.get();
                user.getPeliculas_favoritas().remove(peliculaOptional.get());
                repository.save(user);
            }
        }
    }



}
