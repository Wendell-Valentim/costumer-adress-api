package io.github.wendellvalentim.customer_address_api.repository;

import io.github.wendellvalentim.customer_address_api.model.Cliente;
import io.github.wendellvalentim.customer_address_api.model.Endereco;
import io.github.wendellvalentim.customer_address_api.service.ClienteService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
public class ClienteRepositoryTest {

    @Autowired
    ClienteRepository repository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ClienteService clienteService;




    @Test
    void findClienteByCpf() {
       var cliente = repository.findByCpf("02431559927");
        System.out.println(cliente);
    }


}
