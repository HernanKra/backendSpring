package com.asj.backend.repository;


import com.asj.backend.dto.UsuarioLoginDTO;
import com.asj.backend.entity.Pelicula;
import com.asj.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {


        Optional<Usuario> findById(Integer id);

        Optional<Usuario> findUsuarioByUsername(String username);

        Optional<Usuario> findUsuarioByUsernameAndPassword(String username, String password);

}
