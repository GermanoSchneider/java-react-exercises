package com.feefo.note_web_app_web_service.infrastructure.user.auth;

import com.feefo.note_web_app_web_service.application.UserApplicationService;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
class UserAuthDetailsAdapter implements UserDetailsService {

    private final UserApplicationService applicationService;

    UserAuthDetailsAdapter(UserRepository userRepository, UserApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = applicationService.findBy(username);

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
