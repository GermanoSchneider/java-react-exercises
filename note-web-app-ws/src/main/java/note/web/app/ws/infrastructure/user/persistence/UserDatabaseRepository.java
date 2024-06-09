package note.web.app.ws.infrastructure.user.persistence;

import note.web.app.ws.domain.user.User;
import note.web.app.ws.domain.user.UserRepository;
import note.web.app.ws.infrastructure.user.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

// Implementation of the user database operations
@Repository
class UserDatabaseRepository implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    UserDatabaseRepository(UserJpaRepository userJpaRepository, UserMapper mapper) {
        this.userJpaRepository = userJpaRepository;
        this.mapper = mapper;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User save(User user) {

        UserEntity userEntity = mapper.toEntity(user)
            .toBuilder()
            .password(passwordEncoder.encode(user.getPassword()))
            .build();

        UserEntity savedUser = userJpaRepository.save(userEntity);

        return mapper.fromEntity(savedUser);
    }

    @Override
    public User findByName(String name) {

        UserEntity user = userJpaRepository.findByName(name);

        return mapper.fromEntity(user);
    }
}
