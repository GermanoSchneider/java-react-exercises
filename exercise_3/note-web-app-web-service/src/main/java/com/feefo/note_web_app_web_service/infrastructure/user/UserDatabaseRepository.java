package com.feefo.note_web_app_web_service.infrastructure.user;

import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.feefo.note_web_app_web_service.infrastructure.user.UserMapper.from;

@Repository
class UserDatabaseRepository implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    UserDatabaseRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {

        UserEntity userEntity = from(user).build();

        UserEntity savedUser = userJpaRepository.save(userEntity);

        return from(savedUser).build();
    }

    @Override
    public User findBy(Long id) {

        Optional<UserEntity> user = userJpaRepository.findById(id);

        if (user.isEmpty()) throw new RuntimeException("The user with id " + id + " was not found");

        return from(user.get()).build();
    }
}
