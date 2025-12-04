package io.github.wendellvalentim.customer_address_api.controller.mappers;

import io.github.wendellvalentim.customer_address_api.controller.dto.usuario.UsuarioFormDTO;
import io.github.wendellvalentim.customer_address_api.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

     Usuario toEntity(UsuarioFormDTO dto);
}
