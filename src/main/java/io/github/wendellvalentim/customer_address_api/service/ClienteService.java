package io.github.wendellvalentim.customer_address_api.service;

import io.github.wendellvalentim.customer_address_api.exceptions.RecursoNaoEncontradoException;
import io.github.wendellvalentim.customer_address_api.exceptions.ViolacaoIntegridadeException;
import io.github.wendellvalentim.customer_address_api.model.Cliente;
import io.github.wendellvalentim.customer_address_api.model.Endereco;
import io.github.wendellvalentim.customer_address_api.repository.ClienteRepository;
import io.github.wendellvalentim.customer_address_api.repository.EnderecoRepository;
import io.github.wendellvalentim.customer_address_api.validator.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {


    private final ClienteRepository repository;

    private final EnderecoRepository enderecoRepository;

    private final ClienteValidator validator;


    public Cliente salvarCliente (Cliente cliente) {
        validator.validar(cliente);
        return repository.save(cliente);
    }

    public void atualizarCliente (Cliente cliente) {
       if (cliente.getId() == null) {
           throw new IllegalArgumentException("Para atualizar o Cliente precisa estar cadastrado");
       }
        validator.validar(cliente);
        repository.save(cliente);
    }

    public Optional<Cliente> obeterPorId (UUID id) {
        return repository.findById(id);
    }

    public void deletarPorId (UUID id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
        if(possuiEndereco(cliente)) {
            throw new ViolacaoIntegridadeException("Cliente possui endereços vinculados, não é possivel excluir!");
        }
        repository.deleteById(id);
    }

    public void deletarPorCliente (Cliente cliente) {
        repository.delete(cliente);
    }

    public List<Cliente> pesquisar(String nome, String cpf) {


        if (nome != null && cpf != null) {

            return repository.findByCpfAndNome(cpf, nome);
        }


        if (nome != null) {
            return repository.findByNome(nome);
        }


        if (cpf != null) {
            return repository.findByCpf(cpf);
        }


        return repository.findAll();
    }

    public boolean possuiEndereco (Cliente cliente) {
        return enderecoRepository.existsByCliente(cliente);
    }
}
