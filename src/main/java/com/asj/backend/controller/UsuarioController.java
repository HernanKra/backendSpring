package com.asj.backend.controller;

import com.asj.backend.dto.UsuarioDTO;
import com.asj.backend.dto.UsuarioLoginDTO;
import com.asj.backend.entity.Pelicula;
import com.asj.backend.entity.Usuario;
import com.asj.backend.exception.NotFoundException;
import com.asj.backend.mapper.UsuarioMapper;
import com.asj.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;
    private Map<String, Object> response = new HashMap<>();

    public UsuarioController(UsuarioService service, UsuarioMapper mapper, UsuarioMapper mapper1) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id){

            Usuario user = service.getUsuario(id);
            if(user == null) {
                response.put("success", Boolean.FALSE);
                response.put("message", String.format("El usuario con id %s, no existe", id));
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            response.put("success", Boolean.TRUE);
            response.put("message", "Usuario encontrado");
            response.put("data", mapper.usuarioEntityToUsuariodto(user));

            return ResponseEntity.status(HttpStatus.FOUND).body(response);

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Usuario usuario) {
            if(service.getUsuarioByUsername(usuario.getUsername()).isPresent()){
                response.put("success", Boolean.FALSE);
                response.put("message", String.format("El username %s no se encuentra disponible", usuario.getUsername()));
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            } else {
            service.createUsuario(usuario);
            response.put("success", Boolean.TRUE);
            response.put("message", "Usuario creado correctamente");
            response.put("data", mapper.usuarioEntityToUsuariodto(usuario));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UsuarioLoginDTO usuario) {
        Usuario user = service.loginUsuario(usuario);
        if(user != null) {
            response.put("success", Boolean.TRUE);
            response.put("message","Credenciales Encontradas");
            response.put("data", mapper.usuarioEntityToUsuariodto(user));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("success", Boolean.FALSE );
            response.put("message", "Credenciales incorrectas");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // MANEJAR PELICULAS DEL USUARIO

    @PostMapping("/wishlist/{id}")
    public ResponseEntity<?> addPeliculasWishlist(@PathVariable Integer id,
                                                  @RequestBody Pelicula pelicula){
        if(service.addPeliculasWishlist(id, pelicula) != null) {
            service.addPeliculasWishlist(id, pelicula);
            response.put("success", Boolean.TRUE);
            response.put("message", "La pelicula se agrego a la WishList");
            response.put("data", pelicula);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else {
            response.put("success", Boolean.FALSE);
            response.put("message", "La pelicula ya se encuentra en la lista");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping("/favorite/{id}")
    public ResponseEntity<?> addPeliculasFavorite(@PathVariable Integer id,
                                                  @RequestBody Pelicula pelicula){
        if(service.addPeliculasFavorite(id, pelicula) != null) {
            service.addPeliculasFavorite(id, pelicula);
            response.put("success", Boolean.TRUE);
            response.put("message", "La pelicula se agrego a la lista de Favoritos");
            response.put("data", pelicula);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else {
            response.put("success", Boolean.FALSE);
            response.put("message", "La pelicula ya se encuentra en la lista");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @DeleteMapping("/wishlist/{idUsuario}/{idPelicula}")
    public ResponseEntity<?> removePeliculasWishlist(@PathVariable Integer idUsuario,
                                                     @PathVariable Integer idPelicula) {
        service.deletePeliculasWishlist(idUsuario, idPelicula);
        response.put("success", Boolean.TRUE);
        response.put("message", "Pelicula eliminada correctamente de la WishList");
        response.put("data", idPelicula);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("/favorite/{idUsuario}/{idPelicula}")
    public ResponseEntity<?> removePeliculasFavorite(@PathVariable Integer idUsuario,
                                                     @PathVariable Integer idPelicula) {
        service.deletePeliculasFavorite(idUsuario, idPelicula);
        service.deletePeliculasWishlist(idUsuario, idPelicula);
        response.put("success", Boolean.TRUE);
        response.put("message", "Pelicula eliminada correctamente de la lista de Favoritos");
        response.put("data", idPelicula);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    // FIN MANEJO DE PELICULAS DEL USUARIO


    // Modificar y Eliminar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id,
                                        @RequestBody UsuarioDTO usuarioDTO) {
        if (service.getUsuario(id) != null) {
            Usuario userUpdate = service.updateUsuario(id, usuarioDTO);
            response.put("success", Boolean.TRUE);
            response.put("message", "Usuario editado Correctamente");
            response.put("data", usuarioDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else {
            response.put("success", Boolean.FALSE);
            response.put("message", "Usuario no encontrado");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        try {
            service.deleteUsuario(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
}
