package io.github.wendellvalentim.customer_address_api.controller.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record EnderecoFormDTO(
                              @NotBlank(message = "campo obrigatorio!")
                              String logradouro,

                              @NotBlank(message = "Campo obrigatorio!")
                              @Size(min = 8, max = 8)
                              String cep,

                              @NotBlank(message = "campo obrigatorio!")
                              String cidade,

                              @NotNull(message = "campo obrigatorio!")
                              UUID idCliente) {
}
