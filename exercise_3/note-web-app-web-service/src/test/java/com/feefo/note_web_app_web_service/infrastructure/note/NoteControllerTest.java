package com.feefo.note_web_app_web_service.infrastructure.note;

import static com.feefo.note_web_app_web_service.ModelFixture.buildNote;
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
import com.feefo.note_web_app_web_service.SecurityConfigTest;
import com.feefo.note_web_app_web_service.application.NoteApplicationService;
import com.feefo.note_web_app_web_service.application.UserApplicationService;
import com.feefo.note_web_app_web_service.domain.note.Note;
import java.text.DateFormat;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NoteController.class)
@Import(SecurityConfigTest.class)
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

      Note note = buildNote();

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

    Note note = buildNote();

    Long id = note.getId();

    doReturn(note)
        .when(noteApplicationService)
        .update(id, request.getText(), note.getOwner());

    mockMvc.perform(
            post("/notes/" + id)
                .contentType(APPLICATION_JSON)
                .content(json)
                .with(user("john").password("1234"))
        ).andExpect(status().isNoContent())
        .andExpect(content().string(""));
  }

  @Test
  @DirtiesContext
  void shouldFindAllNotes() throws Exception {

    Note note = buildNote();

    Collection<Note> notes = List.of(note);

    Collection<NoteResponseDto> expectedResponse = notes
        .stream()
        .map(NoteFixture::toNoteResponseDto)
        .toList();

    doReturn(notes)
        .when(noteApplicationService)
        .findAllBy(note.getOwner());

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

    Note note = buildNote();

    Long id = note.getId();

    doNothing()
        .when(noteApplicationService)
        .deleteBy(id, note.getOwner());

    mockMvc.perform(
            delete("/notes/" + id)
                .contentType(APPLICATION_JSON)
                .with(user("john").password("1234"))
        ).andExpect(status().isNoContent())
        .andExpect(content().string(""));
  }
}
