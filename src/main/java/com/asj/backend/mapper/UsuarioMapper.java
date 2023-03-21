package com.asj.backend.mapper;

import com.asj.backend.dto.UsuarioDTO;
import com.asj.backend.dto.UsuarioLoginDTO;
import com.asj.backend.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "peliculas_wishlist", source = "peliculas_wishlist")
    @Mapping(target = "peliculas_favorite", source = "peliculas_favoritas")
    UsuarioDTO usuarioEntityToUsuariodto(Usuario user);

    UsuarioDTO usuarioLoginDTOToUsuarioDTO(UsuarioLoginDTO usuarioLoginDTO);

    Usuario usuarioDTOToUsuarioEntity(UsuarioDTO usuarioDTO);


}
