package feefo.note.web.app.ws.infrastructure.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feefo.note.web.app.ws.ModelFixture;
import feefo.note.web.app.ws.SecurityConfigTest;
import feefo.note.web.app.ws.application.UserApplicationService;
import feefo.note.web.app.ws.domain.user.User;
import feefo.note.web.app.ws.domain.user.User.UserBuilder;
import feefo.note.web.app.ws.infrastructure.note.NoteMapper;
import feefo.note.web.app.ws.infrastructure.user.UserFixture;
import feefo.note.web.app.ws.infrastructure.user.UserMapper;
import java.text.DateFormat;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(UserController.class)
@Import({SecurityConfigTest.class, UserApplicationService.class, UserMapper.class, NoteMapper.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserApplicationService userApplicationService;

    @SpyBean
    private UserMapper userMapper;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    private final ObjectMapper mapper = new ObjectMapper()
        .setDateFormat(DateFormat.getDateTimeInstance())
        .registerModule(new JavaTimeModule())
        .registerModule(new Jdk8Module());

    @Test
    void shouldRegisterANewUser() throws Exception {

        UserBuilder builder = ModelFixture.userBuilder();
        User user = builder.build();

        UserRequestDto request = UserFixture.toUserRequest(user);
        UserResponseDto response = UserFixture.toUserResponse(user);

        String jsonRequest = mapper.writeValueAsString(request);
        String jsonResponse = mapper.writeValueAsString(response);

        doReturn(user).when(userApplicationService).register(userArgumentCaptor.capture());

        MvcResult result = mockMvc.perform(post("/auth/user")
            .contentType(APPLICATION_JSON).content(jsonRequest)).andReturn();

        int statusCode = result.getResponse().getStatus();
        String resultRequest = result.getRequest().getContentAsString();
        String resultResponse = result.getResponse().getContentAsString();

        assertThat(CREATED.value()).isEqualTo(statusCode);
        assertThat(jsonRequest).isEqualTo(resultRequest);
        assertThat(jsonResponse).isEqualTo(resultResponse);

        assertThat(builder.id(null).build()).usingRecursiveComparison().isEqualTo(userArgumentCaptor.getValue());

        verify(userApplicationService).register(userArgumentCaptor.getValue());
        verify(userMapper).toResponse(user);
    }

    @Test
    void shouldFailWhenTryingToRegisterAInvalidUser() throws Exception {

        UserRequestDto request = new UserRequestDto("", "");
        String[] response = {"password: should not be blank", "name: should not be blank"};

        String jsonRequest = mapper.writeValueAsString(request);
        String jsonResponse = mapper.writeValueAsString(response);

        MvcResult result = mockMvc.perform(post("/auth/user")
            .contentType(APPLICATION_JSON).content(jsonRequest)).andReturn();

        int statusCode = result.getResponse().getStatus();
        String resultRequest = result.getRequest().getContentAsString();
        String resultResponse = result.getResponse().getContentAsString();

        assertThat(BAD_REQUEST.value()).isEqualTo(statusCode);
        assertThat(jsonRequest).isEqualTo(resultRequest);


        Iterable<String> convertedResponse = mapper.readValue(resultResponse, Collection.class);

        assertThat(response).hasSameElementsAs(convertedResponse);

        verify(userMapper).fromRequest(request);
    }

    @Test
    void shouldAuthenticateUser() throws Exception {

        User user = ModelFixture.buildUser();

        UserRequestDto request = UserFixture.toUserRequest(user);

        String jsonRequest = mapper.writeValueAsString(request);

        String token = "auth-token";

        doReturn(token).when(userApplicationService).authenticate(user.getName());

        MvcResult result = mockMvc.perform(post("/auth/login")
            .contentType(APPLICATION_JSON).content(jsonRequest).with(user(user.getName())
                .password(user.getPassword()))).andReturn();

        int statusCode = result.getResponse().getStatus();
        String resultRequest = result.getRequest().getContentAsString();
        String resultResponse = result.getResponse().getContentAsString();

        assertThat(OK.value()).isEqualTo(statusCode);
        assertThat(jsonRequest).isEqualTo(resultRequest);
        assertThat(token).isEqualTo(resultResponse);

        verify(userApplicationService).authenticate(user.getName());
        verifyNoInteractions(userMapper);
    }
}
