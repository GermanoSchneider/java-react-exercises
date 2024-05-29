package com.feefo.note_web_app_web_service.infrastructure.note.controller;

import static com.feefo.note_web_app_web_service.ModelFixture.buildNote;
import static com.feefo.note_web_app_web_service.ModelFixture.noteBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.feefo.note_web_app_web_service.SecurityConfigTest;
import com.feefo.note_web_app_web_service.application.NoteApplicationService;
import com.feefo.note_web_app_web_service.application.UserApplicationService;
import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.note.Note.NoteBuilder;
import com.feefo.note_web_app_web_service.infrastructure.note.NoteFixture;
import com.feefo.note_web_app_web_service.infrastructure.note.NoteMapper;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(NoteController.class)
@Import({SecurityConfigTest.class, NoteMapper.class})
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteApplicationService noteApplicationService;

    @MockBean
    private UserApplicationService userApplicationService;

    @Captor
    private ArgumentCaptor<Note> noteArgumentCaptor;

    @SpyBean
    private NoteMapper noteMapper;

    private final ObjectMapper mapper = new ObjectMapper()
        .setDateFormat(DateFormat.getDateTimeInstance())
        .registerModule(new JavaTimeModule())
        .registerModule(new Jdk8Module());

    @Test
    @DirtiesContext
    void shouldCreateANewNote() throws Exception {

      NoteRequestDto request = new NoteRequestDto("hello world!");

      NoteBuilder builder = noteBuilder();
      Note note = builder.text(request.getText()).build();

      NoteResponseDto response = NoteFixture.toResponse(note);

      String jsonRequest = mapper.writeValueAsString(request);
      String jsonResponse = mapper.writeValueAsString(response);

      doReturn(note)
          .when(noteApplicationService)
          .create(noteArgumentCaptor.capture());

      MvcResult result = mockMvc.perform(
          post("/notes")
              .contentType(APPLICATION_JSON)
              .content(jsonRequest)
              .with(user("john").password("1234"))
      ).andReturn();

      int statusCode = result.getResponse().getStatus();
      String resultRequest = result.getRequest().getContentAsString();
      String resultResponse = result.getResponse().getContentAsString();
      String location = result.getResponse().getHeader("Location");

      assertThat(CREATED.value()).isEqualTo(statusCode);
      assertThat(jsonRequest).isEqualTo(resultRequest);
      assertThat(jsonResponse).isEqualTo(resultResponse);
      assertThat("http://localhost/notes").isEqualTo(location);

      Note expectedNote = builder.id(null).build();

      assertThat(getWithoutNano(expectedNote.getCreation()))
          .isEqualTo(getWithoutNano(noteArgumentCaptor.getValue().getCreation()));

      assertThat(getWithoutNano(expectedNote.getLastUpdate()))
          .isEqualTo(getWithoutNano(noteArgumentCaptor.getValue().getLastUpdate()));

      assertThat(expectedNote)
          .usingRecursiveComparison()
          .ignoringFields("lastUpdate", "creation")
          .isEqualTo(noteArgumentCaptor.getValue());

      verify(noteMapper).from(request, note.getOwner());
      verify(noteMapper).toResponse(note);
    }

  @Test
  @DirtiesContext
  void shouldUpdateANote() throws Exception {

    NoteRequestDto request = new NoteRequestDto("updated hello world!");

    Note note = buildNote();
    NoteResponseDto response = NoteFixture.toResponse(note);

    String jsonRequest = mapper.writeValueAsString(request);
    String jsonResponse = mapper.writeValueAsString(response);

    doReturn(note)
        .when(noteApplicationService)
        .update(note.getId(), request.getText(), note.getOwner());

    MvcResult result = mockMvc.perform(
            post("/notes/" + note.getId())
                .contentType(APPLICATION_JSON)
                .content(jsonRequest)
                .with(user("john").password("1234"))
        ).andReturn();

    int statusCode = result.getResponse().getStatus();
    String resultRequest = result.getRequest().getContentAsString();
    String resultResponse = result.getResponse().getContentAsString();

    assertThat(OK.value()).isEqualTo(statusCode);
    assertThat(jsonRequest).isEqualTo(resultRequest);
    assertThat(jsonResponse).isEqualTo(resultResponse);

    verify(noteMapper).toResponse(note);
  }

  @Test
  @DirtiesContext
  void shouldFindAllNotes() throws Exception {

    Note note = buildNote();

    Collection<Note> notes = List.of(note);

    Collection<NoteResponseDto> response = notes
        .stream()
        .map(NoteFixture::toResponse)
        .toList();

    String jsonResponse = mapper.writeValueAsString(response);

    doReturn(notes)
        .when(noteApplicationService)
        .findAllBy(note.getOwner());

    MvcResult result = mockMvc.perform(
            get("/notes")
                .contentType(APPLICATION_JSON)
                .with(user("john").password("1234"))
        ).andReturn();

    int statusCode = result.getResponse().getStatus();
    String resultResponse = result.getResponse().getContentAsString();

    assertThat(OK.value()).isEqualTo(statusCode);
    assertThat(jsonResponse).isEqualTo(resultResponse);

    verify(noteMapper).toResponse(note);
  }


  @Test
  @DirtiesContext
  void shouldDeleteANote() throws Exception {

    Note note = buildNote();

    doNothing()
        .when(noteApplicationService)
        .deleteBy(note.getId(), note.getOwner());

    MvcResult result = mockMvc.perform(
            delete("/notes/" + note.getId())
                .contentType(APPLICATION_JSON)
                .with(user("john").password("1234"))
        ).andReturn();

    int statusCode = result.getResponse().getStatus();

    assertThat(NO_CONTENT.value()).isEqualTo(statusCode);
  }

  private LocalDateTime getWithoutNano(LocalDateTime localDateTime) {

      return localDateTime.withNano(0).withSecond(0);
  }
}
