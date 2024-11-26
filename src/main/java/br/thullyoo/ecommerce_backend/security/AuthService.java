package br.thullyoo.ecommerce_backend.security;

import org.antlr.v4.runtime.Token;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenService tokenService;

    public AuthService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String authenticate(Authentication authentication){

    }
}
