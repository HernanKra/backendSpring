package com.asj.backend.entity;
import lombok.*;
import org.mapstruct.Mapping;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private String nombre;
    private String apellido;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_movie_wishlist",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    List<Pelicula> peliculas_wishlist;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_movie_favorite",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    List<Pelicula> peliculas_favoritas;
    }
