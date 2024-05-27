package com.feefo.note_web_app_web_service.infrastructure.note;

import static com.feefo.note_web_app_web_service.ModelFixture.buildUser;
import static com.feefo.note_web_app_web_service.ModelFixture.noteBuilder;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.feefo.note_web_app_web_service.SpringAuthConfig;
import com.feefo.note_web_app_web_service.application.NoteApplicationService;
import com.feefo.note_web_app_web_service.application.UserApplicationService;
import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.user.User;
import java.text.DateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NoteController.class)
@Import(SpringAuthConfig.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteApplicationService noteApplicationService;

    @MockBean
    private UserApplicationService userApplicationService;

    private final ObjectMapper mapper = new ObjectMapper()
        .setDateFormat(DateFormat.getDateTimeInstance())
        .registerModule(new JavaTimeModule())
        .registerModule(new Jdk8Module());

    @Test
    @DirtiesContext
    void shouldCreateANewNote() throws Exception {

      NoteRequestDto request = new NoteRequestDto("hello world!");

      String json = mapper.writeValueAsString(request);

      User user = buildUser();
      Note note = noteBuilder().user(user).build();

      doReturn(user)
          .when(userApplicationService)
          .findBy(user.getName());

      doReturn(note)
          .when(noteApplicationService)
          .create(note);

      mockMvc.perform(
          post("/notes")
              .contentType(APPLICATION_JSON)
              .content(json)
              .with(user("john").password("1234"))
      ).andExpect(status().isCreated())
          .andExpect(content().string(""))
          .andExpect(header().string("Location", "http://localhost/notes"));
    }

  @Test
  @DirtiesContext
  void shouldUpdateANote() throws Exception {

    NoteRequestDto request = new NoteRequestDto("hello world 123!");

    String json = mapper.writeValueAsString(request);

    User user = buildUser();
    Note note = noteBuilder().user(user).build();

    doReturn(Optional.of(note))
        .when(noteApplicationService)
        .update(note.getId(), request.getText(), user.getName());

    mockMvc.perform(
            post("/notes/" + note.getId())
                .contentType(APPLICATION_JSON)
                .content(json)
                .with(user("john").password("1234"))
        ).andExpect(status().isNoContent())
        .andExpect(content().string(""));
  }

  @Test
  @DirtiesContext
  void shouldNotUpdateANoteWhenItDoesNotExist() throws Exception {

    NoteRequestDto request = new NoteRequestDto("hello world 123!");

    String json = mapper.writeValueAsString(request);

    User user = buildUser();
    Note note = noteBuilder().user(user).build();

    Long invalidId = 2L;

    doReturn(Optional.empty())
        .when(noteApplicationService)
        .update(invalidId, request.getText(), user.getName());

    mockMvc.perform(
            post("/notes/" + invalidId)
                .contentType(APPLICATION_JSON)
                .content(json)
                .with(user("john").password("1234"))
        ).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  @DirtiesContext
  void shouldFindAllNotes() throws Exception {

    User user = buildUser();
    Note note = noteBuilder().user(user).build();

    Collection<Note> notes = List.of(note);

    Collection<NoteResponseDto> expectedResponse = notes
        .stream()
        .map(NoteFixture::toNoteResponseDto)
        .toList();

    doReturn(notes)
        .when(noteApplicationService)
        .findAllBy(user.getName());

    mockMvc.perform(
            get("/notes")
                .contentType(APPLICATION_JSON)
                .with(user("john").password("1234"))
        ).andExpect(status().isOk())
        .andExpect(content().string(mapper.writeValueAsString(expectedResponse)));
  }


  @Test
  @DirtiesContext
  void shouldDeleteANote() throws Exception {

    User user = buildUser();
    Note note = noteBuilder().user(user).build();

    doNothing()
        .when(noteApplicationService)
        .deleteBy(note.getId(), user.getName());

    mockMvc.perform(
            delete("/notes/" + note.getId())
                .contentType(APPLICATION_JSON)
                .with(user("john").password("1234"))
        ).andExpect(status().isNoContent())
        .andExpect(content().string(""));
  }
}
