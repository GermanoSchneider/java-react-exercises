package com.feefo.note_web_app_web_service.application;

import com.feefo.note_web_app_web_service.domain.TokenService;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    public UserApplicationService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public User register(User user) {

        return userRepository.save(user);
    }

    public String authenticate(String username) {

        return tokenService.generateTokenWith(username);
    }

    public User findBy(String name) {

        return userRepository.findByName(name);
    }
}
