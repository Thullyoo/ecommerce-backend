package br.thullyoo.ecommerce_backend.controllers;

import br.thullyoo.ecommerce_backend.domain.user.User;
import br.thullyoo.ecommerce_backend.domain.user.UserGetResponse;
import br.thullyoo.ecommerce_backend.domain.user.UserRequest;
import br.thullyoo.ecommerce_backend.security.TokenDTO;
import br.thullyoo.ecommerce_backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequest userRequest){
        User user = userService.registerUser(userRequest);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(Authentication authentication){
        return ResponseEntity.ok().body(userService.login(authentication));
    }

    @GetMapping("/infoId")
    public ResponseEntity<UserGetResponse> getUserById(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok().body(userService.getUserById(jwt));
    }
}

