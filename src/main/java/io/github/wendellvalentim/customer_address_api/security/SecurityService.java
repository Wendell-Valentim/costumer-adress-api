package io.github.wendellvalentim.customer_address_api.security;

import io.github.wendellvalentim.customer_address_api.model.Usuario;
import io.github.wendellvalentim.customer_address_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UsuarioService service;

    public Optional<Usuario> obterUsuarioLogado () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        return service.obterPorLogin(login);
    }
}
