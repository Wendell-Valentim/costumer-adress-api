package io.github.wendellvalentim.customer_address_api.controller.dto.cliente;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteFormDTO(UUID id,
                             @NotBlank(message = "Campo obrigatorio!")
                             @Size(max = 100, message = "Limite do campo excedido!")
                             String nome,

                             @NotNull(message = "A data de nascimento é obrigatória")
                             @Past(message = "A data de nascimento não pode ser futura")
                             @JsonFormat(pattern = "dd/MM/yyyy")
                             LocalDate dataNascimento,

                             @NotBlank(message = "O CPF é um campo obrigatório.")
                             @Size(min = 11, max = 14, message = "O CPF deve ter exatamente 11 dígitos.")
                             @CPF
                             String cpf) {

}
