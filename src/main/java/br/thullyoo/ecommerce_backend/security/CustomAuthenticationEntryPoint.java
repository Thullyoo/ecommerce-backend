package br.thullyoo.ecommerce_backend.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String errorMessage = "Unauthorized";

        if (authException instanceof BadCredentialsException) {
            errorMessage = "Email or password incorrect";
        }

        response.getWriter().write(String.format(
                "{\"error\": \"%s\", \"status\": \"401\", \"message\": \"%s\"}",
                errorMessage, "Email or password incorrect"
        ));
    }
}
