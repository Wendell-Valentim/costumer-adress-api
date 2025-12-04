package io.github.wendellvalentim.customer_address_api.service;


import io.github.wendellvalentim.customer_address_api.model.Usuario;
import io.github.wendellvalentim.customer_address_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void salvar (Usuario usuario) {
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }

    public Optional<Usuario> obterPorLogin (String login) {
        return repository.findByLogin(login);
    }
}
