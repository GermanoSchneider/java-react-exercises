package com.feefo.note_web_app_web_service.application;

import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.feefo.note_web_app_web_service.ModelFixture.buildUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class UserApplicationServiceTest {

    @InjectMocks
    private UserApplicationService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldExecuteTheUserCreationProcessWithSuccess() {

        User user = buildUser();

        doReturn(user)
                .when(userRepository)
                .save(user);

        User registeredUser = userService.register(user);

        assertThat(user).isEqualTo(registeredUser);
        verify(userRepository).save(user);
    }
}
