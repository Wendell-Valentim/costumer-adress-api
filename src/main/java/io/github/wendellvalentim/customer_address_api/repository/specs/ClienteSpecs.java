package io.github.wendellvalentim.customer_address_api.repository.specs;

import io.github.wendellvalentim.customer_address_api.model.Cliente;
import org.springframework.data.jpa.domain.Specification;

public class ClienteSpecs {

    public static Specification<Cliente> nomeLike (String nome) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Cliente> cpfEqual (String cpf) {
        return (root, query, cb) -> cb.equal(root.get("cpf"), cpf);
    }

    public static Specification<Cliente> nascEqual(Integer anoNascimento) {
        return (root, query, cb) -> cb.equal(cb.function("to_char", String.class, root.get("dataNascimento"), cb.literal("yyyy")), anoNascimento.toString());
    }
}
