package com.asj.backend.service.impl;

import com.asj.backend.entity.Pelicula;
import com.asj.backend.repository.PeliculaRepository;
import com.asj.backend.service.PeliculaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    private final PeliculaRepository repository;

    public PeliculaServiceImpl(PeliculaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pelicula getPelicula(Integer id) {
        if(repository.findById(id).isPresent()){
            return repository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public Pelicula createPelicula(Pelicula pelicula) {
        if(repository.findById(pelicula.getIdPelicula()).isEmpty()) {
            repository.save(pelicula);
            return pelicula;
        } else {
           return null;
        }
    }

    @Override
    public void deletePelicula(Integer id) {
        Optional<Pelicula> pelicula = repository.findById(id);
        if(pelicula.isPresent()) {
            repository.delete(pelicula.get());
        }

    }
}
