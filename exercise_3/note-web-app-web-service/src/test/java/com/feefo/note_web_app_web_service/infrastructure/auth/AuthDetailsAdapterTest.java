package com.feefo.note_web_app_web_service.infrastructure.auth;

import com.feefo.note_web_app_web_service.ModelFixture;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

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
