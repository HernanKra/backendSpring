package com.asj.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movies")
public class Pelicula {

    @Id
    private Integer idPelicula;
    private String titulo;
    @Column(name = "detalle",columnDefinition="TEXT", unique = true)
    private String detalle;
    private String urlPortada;
    private double valoracion;
}
