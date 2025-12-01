package io.github.wendellvalentim.customer_address_api.controller.dto.endereco;

import java.util.UUID;

public record EnderecoSimplesDTO(UUID id,
                                 String logradouro,
                                 String cep,
                                 String cidade
                                 ) {
}
