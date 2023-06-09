package com.asj.backend.controller;

import com.asj.backend.entity.Pelicula;
import com.asj.backend.entity.Usuario;
import com.asj.backend.service.PeliculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/movies")
public class PeliculaController {

    private final PeliculaService service;
    private Map<String, Object> response = new HashMap<>();

    public PeliculaController(PeliculaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPelicula(@PathVariable Integer id) {



        try {
            Pelicula pelicula = service.getPelicula(id);
            response.put("success", Boolean.TRUE);
            response.put("message", "Pelicula encontrada");
            response.put("data", pelicula);
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } catch (RuntimeException ex){
            response.put("success", Boolean.FALSE);
            response.put("message", ex.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }


    }

    @PostMapping
    public ResponseEntity<?> createPelicula(@RequestBody Pelicula pelicula) {

        try {
            service.createPelicula(pelicula);
            response.put("success", Boolean.TRUE);
            response.put("message", "Pelicula Creada");
            response.put("data", pelicula);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException ex){
            response.put("success", Boolean.FALSE);
            response.put("message", ex.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePelicula(@PathVariable Integer id) {
        service.deletePelicula(id);
        return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente");
    }


}
