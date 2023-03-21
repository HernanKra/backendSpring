package com.asj.backend.dto;

import com.asj.backend.entity.Pelicula;
import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioDTO {

    private Integer idUser;
    private String username;
    private String nombre;
    private String apellido;
    ArrayList<Pelicula> peliculas_wishlist;
    ArrayList<Pelicula> peliculas_favorite;

}
