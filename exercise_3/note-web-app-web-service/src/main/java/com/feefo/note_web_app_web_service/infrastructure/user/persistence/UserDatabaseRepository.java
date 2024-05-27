package com.feefo.note_web_app_web_service.infrastructure.user.persistence;

import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import static com.feefo.note_web_app_web_service.infrastructure.user.UserMapper.from;

@Repository
class UserDatabaseRepository implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    UserDatabaseRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {

        UserEntity userEntity = from(user)
            .password(PASSWORD_ENCODER.encode(user.getPassword()))
            .build();

        UserEntity savedUser = userJpaRepository.save(userEntity);

        return from(savedUser);
    }

    @Override
    public User findByName(String name) {

        UserEntity user = userJpaRepository.findByName(name);

        return from(user);
    }
}
