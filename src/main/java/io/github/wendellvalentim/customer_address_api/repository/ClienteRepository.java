package io.github.wendellvalentim.customer_address_api.repository;

import io.github.wendellvalentim.customer_address_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID>  {

    List<Cliente> findByNome(String nome);

    List<Cliente> findByCpf(String cpf);

    List<Cliente> findByCpfAndNome(String cpf, String nome);

    Optional<Cliente> findByNomeAndCpf (String nome, String cpf);

}
