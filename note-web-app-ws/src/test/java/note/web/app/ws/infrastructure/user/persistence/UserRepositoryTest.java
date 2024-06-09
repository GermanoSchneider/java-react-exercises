package note.web.app.ws.infrastructure.user.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import note.web.app.ws.ModelFixture;
import note.web.app.ws.domain.user.User;
import note.web.app.ws.domain.user.UserRepository;
import note.web.app.ws.infrastructure.note.NoteMapper;
import note.web.app.ws.infrastructure.user.UserFixture;
import note.web.app.ws.infrastructure.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@Import({UserDatabaseRepository.class,
    UserMapper.class, NoteMapper.class})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @SpyBean
    private UserMapper userMapper;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Test
    @DirtiesContext
    void shouldCreateANewUserWithSuccess() {

        User user = ModelFixture.buildUser();

        User savedUser = userRepository.save(user);
        UserEntity userFound = testEntityManager.find(UserEntity.class, 1);

        assertThat(userFound)
                .usingRecursiveComparison()
                .isEqualTo(savedUser);

        verify(userMapper).toEntity(user);
        verify(userMapper).fromEntity(userFound);
    }

    @Test
    @DirtiesContext
    void shouldFindUserByNameWithSuccess() {

        User user = ModelFixture.userBuilder().id(null).build();

        UserEntity savedUser = testEntityManager.persist(UserFixture.buildFrom(user));

        User userFound = userRepository.findByName(savedUser.getName());

        assertThat(savedUser)
            .usingRecursiveComparison()
            .isEqualTo(userFound);

        verify(userMapper).fromEntity(savedUser);
    }
}
