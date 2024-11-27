package br.thullyoo.ecommerce_backend.security;

public record TokenDTO(String token, Long expiresAt) {
}
