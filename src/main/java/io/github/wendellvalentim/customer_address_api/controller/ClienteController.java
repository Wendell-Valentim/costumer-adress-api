package io.github.wendellvalentim.customer_address_api.controller;

import io.github.wendellvalentim.customer_address_api.controller.dto.ApiErrorResponse;
import io.github.wendellvalentim.customer_address_api.controller.dto.ClienteFormDTO;
import io.github.wendellvalentim.customer_address_api.controller.dto.ClienteResponseDTO;
import io.github.wendellvalentim.customer_address_api.exceptions.RegistroDuplicadoException;
import io.github.wendellvalentim.customer_address_api.exceptions.ViolacaoIntegridadeException;
import io.github.wendellvalentim.customer_address_api.model.Cliente;
import io.github.wendellvalentim.customer_address_api.service.ClienteService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<Object> salvar (@RequestBody @Valid ClienteFormDTO clienteDTO) {
        try {
        Cliente clienteEntity = clienteDTO.mapearParaCliente();
        service.salvarCliente(clienteEntity);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(clienteEntity.getId())
                .toUri();
        return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ApiErrorResponse.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);

        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ClienteResponseDTO> obterDetalhes (@PathVariable("id") String id) {
        var idCliente = UUID.fromString(id);
        Optional<Cliente> clienteOptional =service.obeterPorId(idCliente);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            return ResponseEntity.ok(ClienteResponseDTO.fromEntity(cliente));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity <List<ClienteResponseDTO>> pesquisar (@RequestParam(value = "nome", required = false) String nome,
                                                                @RequestParam(value = "cpf", required = false) String cpf) {
        List<Cliente> resultado = service.pesquisar(nome, cpf);
        List<ClienteResponseDTO> lista = resultado.stream().map( cliente -> new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf())).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar (@PathVariable("id") String id, @RequestBody @Valid ClienteFormDTO dto) {
        try{
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
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ApiErrorResponse.repostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarId (@PathVariable("id") String id) {
        try {
        var idCliente = UUID.fromString(id);
        Optional<Cliente> verificarCliente = service.obeterPorId(idCliente);

        if(verificarCliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var cliente = verificarCliente.get();

        service.deletarPorId(cliente.getId());
        return ResponseEntity.noContent().build();
        } catch (ViolacaoIntegridadeException e) {
            var erroDTO = ApiErrorResponse.repostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);

        }
    }
}
