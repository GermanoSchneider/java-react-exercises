package com.feefo.note_web_app_web_service.infrastructure.user;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.feefo.note_web_app_web_service.ModelFixture.*;
import static com.feefo.note_web_app_web_service.infrastructure.user.UserFixture.buildFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void shouldFindUserByIdWithSuccess() {

        Note note = noteBuilder().id(null).build();
        User user = userBuilder().id(null).notes(List.of(note)).build();

        UserEntity userEntity = buildFrom(user);
        UserEntity savedUser = testEntityManager.persist(userEntity);

        User findUser = userRepository.findBy(savedUser.getId());

        assertThat(findUser)
                .usingRecursiveComparison()
                .isEqualTo(savedUser);
    }

    @Test
    @DirtiesContext
    void shouldFailWhenTryingToFindANonExistingUser() {

        String expectedErrorMessage = "The user with id 1 was not found";

        Exception exception = assertThrows(
                Exception.class,
                () -> userRepository.findBy(1L)
        );

        assertThat(exception.getMessage()).isEqualTo(expectedErrorMessage);
    }
}
