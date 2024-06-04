package note.web.app.ws.infrastructure.auth;

import note.web.app.ws.domain.user.User;
import note.web.app.ws.domain.user.UserRepository;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

// This component is responsible to check the basic auth
// username during the request managed by Spring Security
@Component
class AuthDetailsAdapter implements UserDetailsService {

    private final UserRepository userRepository;

    AuthDetailsAdapter(final UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        User user = userRepository.findByName(username);

        return new org.springframework.security.core.userdetails.User(
            user.getName(),
            user.getPassword(),
            new ArrayList<>()
        );
    }
}
