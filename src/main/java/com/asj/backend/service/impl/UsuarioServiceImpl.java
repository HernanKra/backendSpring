package com.asj.backend.service.impl;

import com.asj.backend.dto.UsuarioDTO;
import com.asj.backend.dto.UsuarioLoginDTO;
import com.asj.backend.entity.Pelicula;
import com.asj.backend.entity.Usuario;
import com.asj.backend.repository.PeliculaRepository;
import com.asj.backend.repository.UsuarioRepository;
import com.asj.backend.service.UsuarioService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
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
          return null;
        }

    }

    @Override
    public Optional<Usuario> getUsuarioByUsername(String username) {
        return repository.findUsuarioByUsername(username);
    }

    @Override
    public Usuario createUsuario(Usuario user) {

        if(repository.findUsuarioByUsername(user.getUsername()).isEmpty()) {
            repository.save(user);
        }
        return user;

    }

    @Override
    public Usuario loginUsuario(UsuarioLoginDTO usuarioLoginDTO) {

            if(repository.findUsuarioByUsername(usuarioLoginDTO.getUsername()).isPresent()) {
                Usuario user = repository.findUsuarioByUsername(usuarioLoginDTO.getUsername()).get();
                return user;
            } else {
                return null;
            }

    }

    @Override
    public Usuario updateUsuario(Integer id,
                                 UsuarioDTO userDTO) {
        Usuario userUpdate;

        Optional<Usuario> usuarioOptional = repository.findById(id);

        if(usuarioOptional.isPresent()) {
            userUpdate = usuarioOptional.get();
            userUpdate.setUsername(userDTO.getUsername());
            userUpdate.setNombre(userDTO.getNombre());
            userUpdate.setApellido(userDTO.getApellido());
            return repository.save(userUpdate);
        } else {
            return null;
        }


    }

    @Override
    public void deleteUsuario(Integer id) {
        Usuario userDelete = repository.getReferenceById(id);
        repository.delete(userDelete);
    }

    @Override
    public Usuario addPeliculasWishlist(Integer id,
                                        Pelicula pelicula) {

        Optional<Usuario> user = repository.findById(id);
        Optional<Pelicula> peliculaOptional = repositoryPelicula.findById(pelicula.getIdPelicula());

        if(user.get().getPeliculas_wishlist().contains(peliculaOptional.get())) {
            return null;
        }

        if(user.isPresent() && peliculaOptional.isPresent()) {
            user.get().getPeliculas_wishlist().add(peliculaOptional.get());
            repository.save(user.get());
            return user.get();
        } else if (user.isPresent() && peliculaOptional.isEmpty()) {
            repositoryPelicula.save(pelicula);
            user.get().getPeliculas_wishlist().add(pelicula);
            repository.save(user.get());
            return user.get();
        } else {
            return null;
        }

    }


    @Override
    public Usuario addPeliculasFavorite(Integer id,
                                        Pelicula pelicula) {

        Optional<Usuario> user = repository.findById(id);
        Optional<Pelicula> peliculaOptional = repositoryPelicula.findById(pelicula.getIdPelicula());

        if(user.get().getPeliculas_favoritas().contains(peliculaOptional.get())) {
            return null;
        }

        if(user.isPresent() && peliculaOptional.isPresent()) {
            user.get().getPeliculas_favoritas().add(peliculaOptional.get());
            repository.save(user.get());
            return user.get();
        } else if (user.isPresent() && peliculaOptional.isEmpty()) {
            repositoryPelicula.save(pelicula);
            user.get().getPeliculas_favoritas().add(pelicula);
            repository.save(user.get());
            return user.get();
        } else {
            return null;
        }
    }

    @Override
    public void deletePeliculasWishlist(Integer idUsuario,
                                        Integer idPelicula) {
        Optional<Usuario> usuarioOptional = repository.findById(idUsuario);
        Optional<Pelicula> peliculaOptional = repositoryPelicula.findById(idPelicula);

        if(peliculaOptional.isPresent() && usuarioOptional.isPresent()) {
           boolean contienePelicula = usuarioOptional.get().getPeliculas_wishlist().contains(peliculaOptional.get());
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
            boolean contienePelicula = usuarioOptional.get().getPeliculas_favoritas().contains(peliculaOptional.get());
            if(contienePelicula) {
                Usuario user = usuarioOptional.get();
                user.getPeliculas_favoritas().remove(peliculaOptional.get());
                repository.save(user);
            }
        }
    }


}
