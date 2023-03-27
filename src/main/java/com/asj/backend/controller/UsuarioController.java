package com.asj.backend.controller;

import com.asj.backend.dto.UsuarioDTO;
import com.asj.backend.dto.UsuarioLoginDTO;
import com.asj.backend.entity.Pelicula;
import com.asj.backend.entity.Usuario;
import com.asj.backend.mapper.UsuarioMapper;
import com.asj.backend.service.UsuarioService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
@Api(value = "Allowed actios for the User Entity", tags = "User Controller")
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

        Map<String, Object> response = new HashMap<>();

           try {
                Usuario user = service.getUsuario(id);
                response.put("success", Boolean.TRUE);
                response.put("message", "Usuario encontrado");
                response.put("data", mapper.usuarioEntityToUsuariodto(user));
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } catch (RuntimeException ex) {
               response.put("success", Boolean.FALSE);
               response.put("message", ex.getMessage());
               response.put("data", null);
               return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
           }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Usuario usuario) {

        Map<String, Object> response = new HashMap<>();

        try {
            service.createUsuario(usuario);
            response.put("success", Boolean.TRUE);
            response.put("message", "Usuario creado correctamente");
            response.put("data", mapper.usuarioEntityToUsuariodto(usuario));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException ex) {
            response.put("success", Boolean.FALSE);
            response.put("message", ex.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UsuarioLoginDTO usuario) {

        Map<String, Object> response = new HashMap<>();

        try {
            Usuario user = service.loginUsuario(usuario);
            response.put("success", Boolean.TRUE);
            response.put("message","Credenciales Encontradas");
            response.put("data", mapper.usuarioEntityToUsuariodto(user));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (HttpClientErrorException ex) {
            response.put("success", Boolean.FALSE );
            response.put("message", ex.getMessage());
            return ResponseEntity.status((ex.getStatusCode())).body(response);
        }
    }

    // MANEJAR PELICULAS DEL USUARIO
    @PostMapping("/wishlist/{id}")
    public ResponseEntity<?> addPeliculasWishlist(@PathVariable Integer id,
                                                  @RequestBody Pelicula pelicula){

        Map<String, Object> response = new HashMap<>();

        try {
            service.addPeliculasWishlist(id, pelicula);
            response.put("success", Boolean.TRUE);
            response.put("message", "La pelicula se agrego a la WishList");
            response.put("data", pelicula);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (RuntimeException ex){
            response.put("success", Boolean.FALSE);
            response.put("message", ex.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping("/favorite/{id}")
    public ResponseEntity<?> addPeliculasFavorite(@PathVariable Integer id,
                                                  @RequestBody Pelicula pelicula){

        Map<String, Object> response = new HashMap<>();

       try {
            service.addPeliculasFavorite(id, pelicula);
            response.put("success", Boolean.TRUE);
            response.put("message", "La pelicula se agrego a la lista de Favoritos");
            response.put("data", pelicula);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (RuntimeException ex){
            response.put("success", Boolean.FALSE);
            response.put("message", ex.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @DeleteMapping("/wishlist/{idUsuario}/{idPelicula}")
    public ResponseEntity<?> removePeliculasWishlist(@PathVariable Integer idUsuario,
                                                     @PathVariable Integer idPelicula) {

        Map<String, Object> response = new HashMap<>();

        service.deletePeliculasWishlist(idUsuario, idPelicula);
        response.put("success", Boolean.TRUE);
        response.put("message", "Pelicula eliminada correctamente de la WishList");
        response.put("data", idPelicula);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("/favorite/{idUsuario}/{idPelicula}")
    public ResponseEntity<?> removePeliculasFavorite(@PathVariable Integer idUsuario,
                                                     @PathVariable Integer idPelicula) {

        Map<String, Object> response = new HashMap<>();

        service.deletePeliculasFavorite(idUsuario, idPelicula);
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

        Map<String, Object> response = new HashMap<>();

        try {
            service.updateUsuario(id, usuarioDTO);
            response.put("success", Boolean.TRUE);
            response.put("message", "Usuario editado Correctamente");
            response.put("data", usuarioDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (RuntimeException ex) {
            response.put("success", Boolean.FALSE);
            response.put("message", ex.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {

        Map<String, Object> response = new HashMap<>();

        try {
            service.deleteUsuario(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
