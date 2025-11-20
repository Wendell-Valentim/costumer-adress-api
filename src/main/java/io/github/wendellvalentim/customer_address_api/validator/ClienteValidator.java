package io.github.wendellvalentim.customer_address_api.validator;

import io.github.wendellvalentim.customer_address_api.exceptions.RegistroDuplicadoException;
import io.github.wendellvalentim.customer_address_api.model.Cliente;
import io.github.wendellvalentim.customer_address_api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteValidator {

    private final ClienteRepository repository;

    public void validar (Cliente cliente) {
        if(existeClienteCadastrado(cliente)) {
            throw  new RegistroDuplicadoException("Autor ja cadastrado!");
        }
    }

    private boolean existeClienteCadastrado(Cliente autor) {
        Optional<Cliente> autorEncontrado = repository.findByNomeAndCpf(
                autor.getNome(), autor.getCpf()
        );

        // 1. Se a busca por duplicado retornou vazio, não há duplicidade, então retorne false.
        if (autorEncontrado.isEmpty()) {
            return false;
        }

        // 2. SE CHEGOU AQUI, O AUTOR FOI ENCONTRADO, AGORA É SEGURO CHAMAR .GET()
        Cliente duplicado = autorEncontrado.get();

        // 3. Verifica se o encontrado é o próprio registro
        if (autor.getId() != null && autor.getId().equals(duplicado.getId())) {
            return false; // É ele mesmo, OK.
        }

        // 4. Se não for ele mesmo, é um DUPLICADO.
        return true;
    }

}
