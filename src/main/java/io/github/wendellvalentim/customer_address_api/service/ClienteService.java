package io.github.wendellvalentim.customer_address_api.service;

import io.github.wendellvalentim.customer_address_api.exceptions.RecursoNaoEncontradoException;
import io.github.wendellvalentim.customer_address_api.exceptions.ViolacaoIntegridadeException;
import io.github.wendellvalentim.customer_address_api.model.Cliente;
import io.github.wendellvalentim.customer_address_api.model.Usuario;
import io.github.wendellvalentim.customer_address_api.repository.ClienteRepository;
import io.github.wendellvalentim.customer_address_api.repository.EnderecoRepository;
import io.github.wendellvalentim.customer_address_api.security.SecurityService;
import io.github.wendellvalentim.customer_address_api.validator.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.wendellvalentim.customer_address_api.repository.specs.ClienteSpecs.*;

@Service
@RequiredArgsConstructor
public class ClienteService {


    private final ClienteRepository repository;

    private final EnderecoRepository enderecoRepository;

    private final ClienteValidator validator;

    private final SecurityService securityService;

    @Transactional
    public Cliente salvarCliente (Cliente cliente) {
        cliente.setCpf(validator.formatarCpf(cliente.getCpf()));

        validator.validarDataNascimento(cliente.getDataNascimento());

        validator.validarDuplicidade(cliente);

        return repository.save(cliente);
    }


    public void atualizarCliente (Cliente cliente) {

        if (cliente.getId() == null) {
           throw new IllegalArgumentException("Para atualizar o Cliente precisa estar cadastrado");
       }

        cliente.setCpf(validator.formatarCpf(cliente.getCpf()));

        validator.validarDuplicidade(cliente);

        Optional<Usuario> usuario= securityService.obterUsuarioLogado();

        cliente.setIdUsuario(usuario.get().getId());

        repository.save(cliente);
    }

    public Optional<Cliente> obeterPorId (UUID id) {
        return repository.findById(id);
    }

    public Cliente obeterClienteEListaPorId (UUID id) {
        Optional<Cliente> clienteEncontrado = repository.findById(id);
        Cliente cliente = clienteEncontrado.get();
        cliente.getEnderecos().size();

        return cliente;
    }



    public void deletarPorId (UUID id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
        if(possuiEndereco(cliente)) {
            throw new ViolacaoIntegridadeException("Cliente possui endereços vinculados, não é possivel excluir!");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void deletarPorCliente (Cliente cliente) {
        repository.delete(cliente);
    }

    public Page<Cliente> pesquisa(String nome, String cpf, Integer anoNascimento, Integer pagina, Integer tamanhoPagina) {

        Specification<Cliente> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (nome != null) {
            specs = specs.and(nomeLike(nome));
        }

        if(cpf != null) {
            specs = specs.and(cpfEqual(cpf));
        }

        if(anoNascimento != null) {
            specs = specs.and(nascEqual(anoNascimento));
        }
        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageRequest);
    }

    public boolean possuiEndereco (Cliente cliente) {
        return enderecoRepository.existsByCliente(cliente);
    }
}
