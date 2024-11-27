package br.thullyoo.ecommerce_backend.services;

import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.purchase.Purchase;
import br.thullyoo.ecommerce_backend.domain.user.User;
import br.thullyoo.ecommerce_backend.domain.user.UserMapper;
import br.thullyoo.ecommerce_backend.domain.user.UserRequest;
import br.thullyoo.ecommerce_backend.repositories.UserRepository;
import br.thullyoo.ecommerce_backend.security.AuthService;
import br.thullyoo.ecommerce_backend.security.TokenDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthService authService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @Transactional
    public User registerUser(UserRequest userRequest){
        var user = userMapper.toUser(userRequest);
        user.setProducts(new ArrayList<Product>());
        user.setPurchases(new ArrayList<Purchase>());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public TokenDTO login(Authentication authentication){
        String token = authService.authenticate(authentication);
        return new TokenDTO(token, 120L);
    }


}
