package br.thullyoo.ecommerce_backend.security;

import br.thullyoo.ecommerce_backend.repositories.UserRepository;
import org.springframework.security.config.core.userdetails.UserDetailsMapFactoryBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UserSecurity(userRepository.findByEmail((email)).orElseThrow(() -> {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }));
    }
}
