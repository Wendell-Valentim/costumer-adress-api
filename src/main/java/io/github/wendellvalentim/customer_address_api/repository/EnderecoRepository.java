package io.github.wendellvalentim.customer_address_api.repository;

import io.github.wendellvalentim.customer_address_api.model.Cliente;
import io.github.wendellvalentim.customer_address_api.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {

    boolean existsByCliente(Cliente cliente);
}
