package io.github.wendellvalentim.customer_address_api.validator;

import io.github.wendellvalentim.customer_address_api.exceptions.RegistroDuplicadoException;
import io.github.wendellvalentim.customer_address_api.model.Endereco;
import io.github.wendellvalentim.customer_address_api.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EnderecoValidator {
    private final EnderecoRepository repository;
    public void validar(Endereco endereco) {
        if(existirEndereco(endereco)) {
            throw new RegistroDuplicadoException("Endereco ja cadastrado!");
        }
    }

    public boolean existirEndereco (Endereco endereco) {
        Optional<Endereco> existeEndereco = repository.findByLogradouroAndCep(endereco.getLogradouro(), endereco.getCep());

        if (endereco.getId() == null) {
            return existeEndereco.isPresent();
        }


        return existeEndereco
                .map(Endereco::getId)
                .stream()
                .anyMatch(id -> !id.equals(endereco.getId()));
    }
}
