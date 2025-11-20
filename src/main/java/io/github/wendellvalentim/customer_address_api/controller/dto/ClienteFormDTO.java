package io.github.wendellvalentim.customer_address_api.controller.dto;

import io.github.wendellvalentim.customer_address_api.model.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteFormDTO(UUID id,
                             @NotBlank(message = "Campo obrigatorio!")
                             @Size(max = 100, message = "Limite do campo excedido!")
                             String nome,

                             @NotBlank(message = "O CPF é um campo obrigatório.")
                             @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 dígitos.")
                             String cpf) {

    public Cliente mapearParaCliente () {
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setCpf(this.cpf);
        return cliente;
    }
}
