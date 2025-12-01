package io.github.wendellvalentim.customer_address_api.controller;

import io.github.wendellvalentim.customer_address_api.controller.dto.endereco.EnderecoFormDTO;
import io.github.wendellvalentim.customer_address_api.controller.dto.endereco.EnderecoResponseDTO;
import io.github.wendellvalentim.customer_address_api.controller.mappers.EnderecoMapper;
import io.github.wendellvalentim.customer_address_api.model.Endereco;
import io.github.wendellvalentim.customer_address_api.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController  implements GenericController{

    private final EnderecoService service;
    private final EnderecoMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar (@RequestBody @Valid EnderecoFormDTO dto) {

            Endereco endereco = mapper.toEntity(dto);
            service.salvar(endereco);
            URI location = gerarHeaderLocation(endereco.getId());
        return ResponseEntity.created(location).build();

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar (@PathVariable("id") @Valid String id, @RequestBody EnderecoFormDTO dto){
        return service.obterPorId(UUID.fromString(id))
                .map(endereco -> {
                    Endereco entidadeAux = mapper.toEntity(dto);
                    endereco.setCidade(entidadeAux.getCidade());
                    endereco.setLogradouro(entidadeAux.getLogradouro());
                    endereco.setCep(entidadeAux.getCep());
                    endereco.setCliente(entidadeAux.getCliente());
                    service.atualizar(endereco);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<EnderecoResponseDTO> obterDetalhes (@PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(endereco -> {
                    var dto = mapper.toDTO(endereco);
                    return ResponseEntity.ok(dto);
                }).orElseGet(()-> ResponseEntity.notFound().build());
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar (@PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(endereco -> {
                    service.deletar(endereco);
                    return ResponseEntity.noContent().build();
                }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EnderecoResponseDTO>> pesquisar (@RequestParam(value = "cep", required = false) String cep,
                                           @RequestParam(value = "logradouro", required = false) String logradouro,
                                           @RequestParam(value = "cpf-cliente", required = false) String cpfCliente

    ) {
        var resultado = service.pesquisar(logradouro, cep, cpfCliente);
        var lista = resultado.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);

    }

}
