package com.asj.backend.repository;


import com.asj.backend.dto.UsuarioLoginDTO;
import com.asj.backend.entity.Pelicula;
import com.asj.backend.entity.Usuario;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {


        Optional<Usuario> findById(Integer id);

        Optional<Usuario> findUsuarioByUsername(String username);

        Optional<Usuario> findUsuarioByUsernameAndPassword(String username, String password);

        @Query("SELECT COUNT(p) > 0 FROM Usuario u JOIN u.peliculas_wishlist p WHERE u.idUser = :idUser AND p.idPelicula = :idPelicula")
        boolean existPeliculaInUsuarioWishlist(@Param("idUser")Integer idUser, @Param("idPelicula") Integer idPelicula);

        @Query("SELECT COUNT(p) > 0 FROM Usuario u JOIN u.peliculas_favoritas p WHERE u.idUser = :idUser AND p.idPelicula = :idPelicula")
        boolean existPeliculaInUsuarioFavorite(@Param("idUser")Integer idUser, @Param("idPelicula") Integer idPelicula);
}
