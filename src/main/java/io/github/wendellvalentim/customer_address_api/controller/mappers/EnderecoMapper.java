package io.github.wendellvalentim.customer_address_api.controller.mappers;

import io.github.wendellvalentim.customer_address_api.controller.dto.endereco.EnderecoFormDTO;
import io.github.wendellvalentim.customer_address_api.controller.dto.endereco.EnderecoResponseDTO;
import io.github.wendellvalentim.customer_address_api.controller.dto.endereco.EnderecoSimplesDTO;
import io.github.wendellvalentim.customer_address_api.model.Endereco;
import io.github.wendellvalentim.customer_address_api.repository.ClienteRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EnderecoMapper {

    @Autowired
    ClienteRepository clienteRepository;

    @Mapping(target = "cliente", expression = "java(clienteRepository.findById(dto.idCliente()).orElse(null))")
    public abstract Endereco toEntity (EnderecoFormDTO dto);

    @Mapping(source = "cliente.id", target = "clienteId")
    public abstract EnderecoResponseDTO toDTO (Endereco endereco);


    public abstract List<EnderecoSimplesDTO> toDTOList(List<Endereco> enderecos);

}
