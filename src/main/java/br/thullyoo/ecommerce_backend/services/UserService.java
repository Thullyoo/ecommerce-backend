package br.thullyoo.ecommerce_backend.services;

import br.thullyoo.ecommerce_backend.domain.product.Product;
import br.thullyoo.ecommerce_backend.domain.purchase.Purchase;
import br.thullyoo.ecommerce_backend.domain.user.User;
import br.thullyoo.ecommerce_backend.domain.user.UserMapper;
import br.thullyoo.ecommerce_backend.domain.user.UserRequest;
import br.thullyoo.ecommerce_backend.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRequest userRequest){
        var user = userMapper.toUser(userRequest);
        user.setProducts(new ArrayList<Product>());
        user.setPurchases(new ArrayList<Purchase>());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
