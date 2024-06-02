package feefo.note.web.app.ws.application;

import feefo.note.web.app.ws.domain.TokenService;
import feefo.note.web.app.ws.domain.user.User;
import feefo.note.web.app.ws.domain.user.UserRepository;
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
