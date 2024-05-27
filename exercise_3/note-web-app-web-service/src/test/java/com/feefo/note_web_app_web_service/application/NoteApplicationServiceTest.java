package com.feefo.note_web_app_web_service.application;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.note.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

import static com.feefo.note_web_app_web_service.ModelFixture.buildNote;
import static com.feefo.note_web_app_web_service.ModelFixture.noteBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class NoteApplicationServiceTest {

    @InjectMocks
    private NoteApplicationService noteService;

    @Mock
    private NoteRepository noteRepository;

    @Test
    void shouldExecuteTheNoteCreationProcessWithSuccess() {

        Note note = buildNote();

        doReturn(note)
                .when(noteRepository)
                .save(note);

        Note savedNote = noteService.create(note);

        assertThat(note).isEqualTo(savedNote);
        verify(noteRepository).save(note);
    }

    @Test
    void shouldExecuteTheNoteUpdateProcessWithSuccess() {

        String newText = "updated dummy text";
        Note note = noteBuilder().text(newText).build();
        Long id = note.getId();

        doReturn(note)
                .when(noteRepository)
                .update(1L, newText);

        Note updatedNote = noteService.update(id, newText);

        assertThat(updatedNote.getText()).isEqualTo(newText);
        verify(noteRepository).update(id, newText);
    }

    @Test
    void shouldExecuteTheNoteReadingProcessWithSuccess() {

        Collection<Note> notes = List.of(buildNote());
        String username = "john";


        doReturn(notes)
                .when(noteRepository)
                .findAllBy(username);

        Collection<Note> notesFound = noteService.findAllBy(username);

        assertThat(notes).hasSameElementsAs(notesFound);
        verify(noteRepository).findAllBy(username);
    }

    @Test
    void shouldExecuteTheNoteDeletionProcessWithSuccess() {

        Note note = buildNote();
        Long id = note.getId();

        doNothing()
                .when(noteRepository)
                .deleteBy(id);

        noteService.deleteBy(id);

        verify(noteRepository).deleteBy(id);
    }
}
