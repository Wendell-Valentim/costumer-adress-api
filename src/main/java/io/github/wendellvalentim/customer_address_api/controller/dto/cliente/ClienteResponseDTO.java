package io.github.wendellvalentim.customer_address_api.controller.dto.cliente;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteResponseDTO(UUID id, String nome,LocalDate dataNascimento, String cpf) {

}
