package io.github.wendellvalentim.customer_address_api.controller.mappers;

import io.github.wendellvalentim.customer_address_api.controller.dto.cliente.ClienteDetailReponseDTO;
import io.github.wendellvalentim.customer_address_api.controller.dto.cliente.ClienteFormDTO;
import io.github.wendellvalentim.customer_address_api.controller.dto.cliente.ClienteResponseDTO;
import io.github.wendellvalentim.customer_address_api.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface ClienteMapper {

    @Mapping(source = "nome", target = "nome")
    Cliente toEntity (ClienteFormDTO dto);

    ClienteResponseDTO toDTO(Cliente cliente);

    ClienteDetailReponseDTO toDETAILDTO(Cliente cliente);

}
