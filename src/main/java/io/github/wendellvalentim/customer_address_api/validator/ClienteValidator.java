package io.github.wendellvalentim.customer_address_api.validator;

import io.github.wendellvalentim.customer_address_api.exceptions.RegistroDuplicadoException;
import io.github.wendellvalentim.customer_address_api.exceptions.ValidacaoIdadeException;
import io.github.wendellvalentim.customer_address_api.model.Cliente;
import io.github.wendellvalentim.customer_address_api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteValidator {

    private final ClienteRepository repository;

    public void validarDuplicidade (Cliente cliente) {
        if(existeCpfDuplicado(cliente)) {
            throw  new RegistroDuplicadoException("Cpf ja esta Cadastrado!");
        }
        if(existeNomeDuplicado(cliente)){
            throw new RegistroDuplicadoException("Nome ja esta Cadastrado!");
        }
    }

    public void validarDataNascimento (LocalDate cliente) {
        if(validarDataNasc(cliente)) {
            throw new ValidacaoIdadeException("O cliente deve ter no mínimo 18 anos para o cadastro.");
        }
    }

    public boolean existeCpfDuplicado (Cliente cliente) {


        Optional<Cliente> clienteEncontrado = repository.findByCpf(cliente.getCpf());

        if (cliente.getId() == null) {
            return clienteEncontrado.isPresent();
        }

        return clienteEncontrado
                .map(Cliente::getId)
                .stream()
                .anyMatch(id -> !id.equals(cliente.getId()));
    }

    public boolean existeNomeDuplicado (Cliente cliente) {


        Optional<Cliente> clienteEncontrado = repository.findByNome(cliente.getNome());

        if (cliente.getId() == null) {
            return clienteEncontrado.isPresent();
        }

        return clienteEncontrado
                .map(Cliente::getId)
                .stream()
                .anyMatch(id -> !id.equals(cliente.getId()));
    }

    public String formatarCpf(String cpfFormatado) {
        if (cpfFormatado == null) {
            return null;
        }
        return cpfFormatado.replaceAll("[^0-9]", "");
    }

    public boolean validarDataNasc (LocalDate dataNascimento) {

        LocalDate dataAtual = LocalDate.now();

        Period periodo = Period.between(dataNascimento, dataAtual);

        return periodo.getYears() < 18;

    }


}
