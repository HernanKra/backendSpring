package com.asj.backend.service;

import com.asj.backend.entity.Pelicula;


public interface PeliculaService {

    Pelicula getPelicula(Integer id);

    Pelicula createPelicula(Pelicula pelicula);

    void deletePelicula(Integer id);

}
