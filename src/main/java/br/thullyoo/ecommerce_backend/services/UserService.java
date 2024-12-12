package br.thullyoo.ecommerce_backend.services;

import br.thullyoo.ecommerce_backend.domain.cart.Cart;
import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.purchase.Purchase;
import br.thullyoo.ecommerce_backend.domain.user.User;
import br.thullyoo.ecommerce_backend.domain.user.UserGetResponse;
import br.thullyoo.ecommerce_backend.domain.user.UserMapper;
import br.thullyoo.ecommerce_backend.domain.user.UserRequest;
import br.thullyoo.ecommerce_backend.repositories.CartRepository;
import br.thullyoo.ecommerce_backend.repositories.UserRepository;
import br.thullyoo.ecommerce_backend.security.AuthService;
import br.thullyoo.ecommerce_backend.security.TokenDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;

    private final CartRepository cartRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthService authService, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public User registerUser(UserRequest userRequest){
        var user = userMapper.toUser(userRequest);
        user.setProducts(new ArrayList<Product>());
        user.setPurchases(new ArrayList<Purchase>());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);
        return userRepository.save(user);
    }

    public UserGetResponse getUserById(Jwt jwt){

        String idString = jwt.getClaim("id").toString();
        UUID id = UUID.fromString(idString);

        var user = userRepository.findById(id);

        if  (user.isEmpty()){
            throw new RuntimeException("User not founded");
        }

        return userMapper.toUserGetResponse(user.get());

    }

    public TokenDTO login(Authentication authentication){
        String token = authService.authenticate(authentication);
        return new TokenDTO(token, 2000L);
    }

}
