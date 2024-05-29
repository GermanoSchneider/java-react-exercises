package com.feefo.note_web_app_web_service.infrastructure.user.persistence;

import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import com.feefo.note_web_app_web_service.infrastructure.user.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

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
