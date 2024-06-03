package feefo.note.web.app.ws.infrastructure.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import feefo.note.web.app.ws.ModelFixture;
import feefo.note.web.app.ws.domain.user.User;
import feefo.note.web.app.ws.domain.user.UserRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest(classes = AuthDetailsAdapter.class)
class AuthDetailsAdapterTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldLoadUserByUsernameWithSuccess() {

        User user = ModelFixture.buildUser();
        String username = user.getName();

        doReturn(user)
                .when(userRepository)
                .findByName(username);

        UserDetails expectedUser = new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                new ArrayList<>()
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertThat(expectedUser)
                .usingRecursiveComparison()
                .isEqualTo(userDetails);
    }
}
