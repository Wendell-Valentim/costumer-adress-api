package io.github.wendellvalentim.customer_address_api.controller.dto.cliente;

import io.github.wendellvalentim.customer_address_api.controller.dto.endereco.EnderecoResponseDTO;
import io.github.wendellvalentim.customer_address_api.controller.dto.endereco.EnderecoSimplesDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClienteDetailReponseDTO(UUID id,
                                      String nome,
                                      String cpf,
                                      LocalDate dataNascimento,
                                      // Lista do NOVO DTO de endereços (o relacionamento)
                                      List<EnderecoSimplesDTO> enderecos) {
}
