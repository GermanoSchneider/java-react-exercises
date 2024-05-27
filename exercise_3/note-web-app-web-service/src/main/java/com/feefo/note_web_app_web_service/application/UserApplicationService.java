package com.feefo.note_web_app_web_service.application;

import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {

    private final UserRepository userRepository;

    public UserApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {

        return userRepository.save(user);
    }

    public User findBy(String name) {

        return userRepository.findByName(name);
    }
}
