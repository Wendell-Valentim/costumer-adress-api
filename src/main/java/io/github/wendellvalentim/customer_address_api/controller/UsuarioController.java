package io.github.wendellvalentim.customer_address_api.controller;

import io.github.wendellvalentim.customer_address_api.controller.dto.usuario.UsuarioFormDTO;
import io.github.wendellvalentim.customer_address_api.controller.mappers.UsuarioMapper;
import io.github.wendellvalentim.customer_address_api.model.Usuario;
import io.github.wendellvalentim.customer_address_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody UsuarioFormDTO dto) {
        Usuario usuario = mapper.toEntity(dto);
        service.salvar(usuario);
        return ResponseEntity.ok().build();

    }
}
