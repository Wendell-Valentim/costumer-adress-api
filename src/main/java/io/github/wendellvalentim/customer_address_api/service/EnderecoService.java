package io.github.wendellvalentim.customer_address_api.service;

import io.github.wendellvalentim.customer_address_api.model.Endereco;
import io.github.wendellvalentim.customer_address_api.repository.EnderecoRepository;
import io.github.wendellvalentim.customer_address_api.repository.specs.EnderecoSpecs;
import io.github.wendellvalentim.customer_address_api.validator.EnderecoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.wendellvalentim.customer_address_api.repository.specs.EnderecoSpecs.*;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository repository;
    private final EnderecoValidator validator;

    public Endereco salvar(Endereco endereco) {
        validator.validar(endereco);
        return repository.save(endereco);
    }

    public void atualizar (Endereco endereco) {
        validator.validar(endereco);
        repository.save(endereco);
    }

    public Optional<Endereco> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Endereco endereco) {
        repository.delete(endereco);
    }

    public List<Endereco> pesquisar (String logradouro, String cep, String cpfCliente) {

        Specification<Endereco> specs = Specification.where((root, query, cb) -> cb.conjunction() );

        if (cep != null) {
            // Usa a Specification correta
            specs = specs.and(EnderecoSpecs.cepEqual(cep));
        }

        if (logradouro != null) {
            // Usa a Specification correta
            specs = specs.and(EnderecoSpecs.logradouroLike(logradouro));
        }

        if(cpfCliente != null) {
            specs = specs.and(cpfEqual(cpfCliente));
        }


        return repository.findAll(specs);
    }
}
