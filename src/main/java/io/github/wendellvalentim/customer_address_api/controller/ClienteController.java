package io.github.wendellvalentim.customer_address_api.controller;

import io.github.wendellvalentim.customer_address_api.controller.dto.cliente.ClienteDetailReponseDTO;
import io.github.wendellvalentim.customer_address_api.controller.dto.cliente.ClienteFormDTO;
import io.github.wendellvalentim.customer_address_api.controller.dto.cliente.ClienteResponseDTO;
import io.github.wendellvalentim.customer_address_api.controller.dto.cliente.ClienteUpdateDTO;
import io.github.wendellvalentim.customer_address_api.controller.mappers.ClienteMapper;
import io.github.wendellvalentim.customer_address_api.model.Cliente;
import io.github.wendellvalentim.customer_address_api.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController implements GenericController{

    private final ClienteService service;
    private final ClienteMapper mapper;


    @PostMapping
    public ResponseEntity<Object> salvar (@RequestBody @Valid ClienteFormDTO clienteDTO) {

        Cliente clienteEntity = mapper.toEntity(clienteDTO);
        service.salvarCliente(clienteEntity);
        URI location = gerarHeaderLocation(clienteEntity.getId());
        return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<ClienteResponseDTO> obterDetalhes (@PathVariable("id") String id) {
        var idCliente = UUID.fromString(id);
        return service.obeterPorId(idCliente)
                .map(cliente -> {
                    ClienteResponseDTO dto = mapper.toDTO(cliente);
                    return  ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/enderecos/{id}")
    public ResponseEntity<ClienteDetailReponseDTO> obterClienteEEnderecoDetalhes (@PathVariable("id") String id) {
        var idCliente = UUID.fromString(id);
        return service.obeterPorId(idCliente)
                .map(cliente -> {
                    ClienteDetailReponseDTO dto = mapper.toDETAILDTO(cliente);
                    return  ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity <Page<ClienteResponseDTO>> pesquisar (@RequestParam(value = "nome", required = false) String nome,
                                                                @RequestParam(value = "cpf", required = false) String cpf,
                                                                @RequestParam(value = "ano-nascimento", required = false) Integer anoNascimento,
                                                                @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
                                                                @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina) {
        Page<Cliente> paginaResultado = service.pesquisa(nome, cpf, anoNascimento, pagina, tamanhoPagina);
        Page<ClienteResponseDTO> resultado = paginaResultado .map(mapper::toDTO);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar (@PathVariable("id") String id, @RequestBody @Valid ClienteUpdateDTO dto) {

        var idCliente = UUID.fromString(id);
        Optional<Cliente> verificar = service.obeterPorId(idCliente);
        if (verificar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var cliente = verificar.get();

        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());

        service.atualizarCliente(cliente);
        return ResponseEntity.noContent().build();

    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarId (@PathVariable("id") String id) {
        var idCliente = UUID.fromString(id);
        Optional<Cliente> verificarCliente = service.obeterPorId(idCliente);

        if(verificarCliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var cliente = verificarCliente.get();

        service.deletarPorId(cliente.getId());
        return ResponseEntity.noContent().build();
    }
}
