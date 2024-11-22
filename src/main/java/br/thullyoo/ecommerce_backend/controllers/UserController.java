package br.thullyoo.ecommerce_backend.controllers;

import br.thullyoo.ecommerce_backend.domain.user.User;
import br.thullyoo.ecommerce_backend.domain.user.UserRequest;
import br.thullyoo.ecommerce_backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody UserRequest userRequest){
        User user = userService.registerUser(userRequest);
        return ResponseEntity.ok().body(user);
    }
}

