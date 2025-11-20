package io.github.wendellvalentim.customer_address_api.controller.dto;

import io.github.wendellvalentim.customer_address_api.model.Cliente;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteResponseDTO(UUID id, String nome, String cpf) {

    public static ClienteResponseDTO fromEntity(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf()
        );
    }
}
