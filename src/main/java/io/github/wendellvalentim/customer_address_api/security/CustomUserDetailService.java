package io.github.wendellvalentim.customer_address_api.security;

import io.github.wendellvalentim.customer_address_api.model.Usuario;
import io.github.wendellvalentim.customer_address_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
            Optional<Usuario> usuarioEncontrado = service.obterPorLogin(login);
            var usuario = usuarioEncontrado.orElseThrow(() ->
                new UsernameNotFoundException("Login não encontrado!"));

            return User.builder()
                    .username(usuario.getLogin())
                    .password(usuario.getSenha())
                    .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                    .build();
    }
}
