package br.thullyoo.ecommerce_backend.security;

import br.thullyoo.ecommerce_backend.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    public String generateToken(Authentication authentication){

        Instant issuedAt = Instant.now();

        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();

        var expirationTime = issuedAt.plusSeconds(120);

        var scopes = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("spring-security")
                .subject(authentication.getName())
                .claim("scope", scopes)
                .claim("id", userSecurity.getId())
                .issuedAt(issuedAt)
                .expiresAt(expirationTime)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

}
