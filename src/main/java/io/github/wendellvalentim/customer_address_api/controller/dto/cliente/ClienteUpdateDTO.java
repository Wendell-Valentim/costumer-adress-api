package io.github.wendellvalentim.customer_address_api.controller.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteUpdateDTO (@NotBlank(message = "Campo obrigatorio!")
                               @Size(max = 100, message = "Limite do campo excedido!")
                               String nome,
                               @NotBlank(message = "O CPF é um campo obrigatório.")
                               @Size(min = 11, max = 14, message = "O CPF deve ter exatamente 11 dígitos.")
                               @CPF
                               String cpf) {

        }

