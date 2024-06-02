package feefo.note.web.app.ws.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import feefo.note.web.app.ws.ModelFixture;
import feefo.note.web.app.ws.domain.user.User;
import feefo.note.web.app.ws.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserApplicationServiceTest {

    @InjectMocks
    private UserApplicationService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldExecuteTheUserCreationProcessWithSuccess() {

        User user = ModelFixture.buildUser();

        doReturn(user)
                .when(userRepository)
                .save(user);

        User registeredUser = userService.register(user);

        assertThat(user).isEqualTo(registeredUser);
        verify(userRepository).save(user);
    }

    @Test
    void shouldExecuteTheUserReadingProcessWithSuccess() {

        User user = ModelFixture.buildUser();

        String username = user.getName();

        doReturn(user)
            .when(userRepository)
            .findByName(username);

        User userFound = userService.findBy(username);

        assertThat(user).isEqualTo(userFound);

        verify(userRepository).findByName(username);
    }
}
