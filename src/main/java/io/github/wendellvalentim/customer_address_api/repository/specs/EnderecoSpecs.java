package io.github.wendellvalentim.customer_address_api.repository.specs;

import io.github.wendellvalentim.customer_address_api.model.Endereco;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class EnderecoSpecs {

    public static Specification<Endereco> cepEqual (String cep) {
        return (root, query, cb) ->
                cb.equal(root.get("cep"), cep);
    }

    public static Specification<Endereco> logradouroLike(String logradouro) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("logradouro")), "%" + logradouro.toUpperCase() + "%");
    }

    public static Specification<Endereco> cpfEqual(String cpf) {
        return (root, query, cb) ->  {
            Join<Object, Object> clienteJoin = root.join("cliente", JoinType.INNER);
            return cb.equal(cb.upper(clienteJoin.get("cpf")), cpf.toUpperCase());
        };
    }
}
