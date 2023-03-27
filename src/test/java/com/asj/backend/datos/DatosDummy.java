package com.asj.backend.datos;

import com.asj.backend.entity.*;

import java.util.ArrayList;

public class DatosDummy {

    public static Pelicula getPelicula() {
        return new Pelicula(1, "Avatar", "esto es avatar", "images/jpg", 7);
    }

    public static Usuario getUsuario() {
        return new Usuario(1, "nombreusuario", "admin123", "admin", "nistrador", new ArrayList<Pelicula>(), new ArrayList<Pelicula>());
    }

    public static Pelicula getPeliculaNoArgs() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Avatar");
        pelicula.setDetalle("esto es avatar");
        pelicula.setUrlPortada("img/asdas");
        pelicula.setValoracion(7);
        return pelicula;
    }

}
