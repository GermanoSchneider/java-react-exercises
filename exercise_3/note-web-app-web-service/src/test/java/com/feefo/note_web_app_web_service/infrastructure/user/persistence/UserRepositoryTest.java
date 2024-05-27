package com.feefo.note_web_app_web_service.infrastructure.user.persistence;

import static com.feefo.note_web_app_web_service.ModelFixture.buildUser;
import static com.feefo.note_web_app_web_service.ModelFixture.userBuilder;
import static com.feefo.note_web_app_web_service.infrastructure.user.UserFixture.buildFrom;
import static org.assertj.core.api.Assertions.assertThat;

import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@Import({UserDatabaseRepository.class})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DirtiesContext
    void shouldCreateANewUserWithSuccess() {

        User savedUser = userRepository.save(buildUser());
        UserEntity findNewUser = testEntityManager.find(UserEntity.class, 1);

        assertThat(findNewUser)
                .usingRecursiveComparison()
                .isEqualTo(savedUser);
    }

    @Test
    @DirtiesContext
    void shouldFindUserByNameWithSuccess() {

        User user = userBuilder().id(null).build();

        UserEntity savedUser = testEntityManager.persist(buildFrom(user));

        User userFound = userRepository.findByName(savedUser.getName());

        assertThat(savedUser)
            .usingRecursiveComparison()
            .isEqualTo(userFound);
    }
}
