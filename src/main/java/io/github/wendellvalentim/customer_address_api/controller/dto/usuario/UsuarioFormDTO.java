package io.github.wendellvalentim.customer_address_api.controller.dto.usuario;

import java.util.List;

public record UsuarioFormDTO(String login,
                             String senha,
                             String cpf,
                             String nome,
                             List<String> roles) {
}
