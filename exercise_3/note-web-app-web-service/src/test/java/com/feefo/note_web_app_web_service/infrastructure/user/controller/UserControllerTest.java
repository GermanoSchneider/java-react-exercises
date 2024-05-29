package com.feefo.note_web_app_web_service.infrastructure.user.controller;

import static com.feefo.note_web_app_web_service.ModelFixture.userBuilder;
import static com.feefo.note_web_app_web_service.infrastructure.user.UserFixture.toUserRequest;
import static com.feefo.note_web_app_web_service.infrastructure.user.UserFixture.toUserResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.feefo.note_web_app_web_service.SecurityConfigTest;
import com.feefo.note_web_app_web_service.application.UserApplicationService;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.User.UserBuilder;
import com.feefo.note_web_app_web_service.infrastructure.note.NoteMapper;
import com.feefo.note_web_app_web_service.infrastructure.user.UserMapper;
import java.text.DateFormat;
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

    UserBuilder builder = userBuilder();
    User user = builder.build();

    UserRequestDto request = toUserRequest(user);
    UserResponseDto response = toUserResponse(user);

    String jsonRequest = mapper.writeValueAsString(request);
    String jsonResponse = mapper.writeValueAsString(response);

    doReturn(user)
        .when(userApplicationService)
        .register(userArgumentCaptor.capture());

    MvcResult result = mockMvc.perform(
        post("/auth/user")
            .contentType(APPLICATION_JSON)
            .content(jsonRequest)
    ).andReturn();

    int statusCode = result.getResponse().getStatus();
    String resultRequest = result.getRequest().getContentAsString();
    String resultResponse = result.getResponse().getContentAsString();

    assertThat(CREATED.value()).isEqualTo(statusCode);
    assertThat(jsonRequest).isEqualTo(resultRequest);
    assertThat(jsonResponse).isEqualTo(resultResponse);

    assertThat(builder.id(null).build())
        .usingRecursiveComparison()
        .isEqualTo(userArgumentCaptor.getValue());

    verify(userApplicationService).register(userArgumentCaptor.getValue());
    verify(userMapper).toResponse(user);
  }
}
