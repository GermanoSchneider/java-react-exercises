package com.feefo.note_web_app_web_service.infrastructure.auth;

import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
class AuthDetailsAdapter implements UserDetailsService {

    private final UserRepository userRepository;

    AuthDetailsAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByName(username);

        return new org.springframework.security.core.userdetails.User(
            user.getName(),
            user.getPassword(),
            new ArrayList<>()
        );
    }
}
