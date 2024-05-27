package com.feefo.note_web_app_web_service.application;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.note.NoteRepository;
import java.util.Optional;
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
        String owner = note.getOwner();
        Long id = note.getId();

        doReturn(note)
                .when(noteRepository)
                .update(id, newText, owner);

        Note updatedNote = noteService.update(id, newText, owner);

        assertThat(note).isEqualTo(updatedNote);
        verify(noteRepository).update(id, newText, owner);
    }

    @Test
    void shouldExecuteTheNoteReadingProcessWithSuccess() {

        Note note = buildNote();
        String owner = note.getOwner();
        Collection<Note> notes = List.of(note);

        doReturn(notes)
                .when(noteRepository)
                .findAllBy(owner);

        Collection<Note> notesFound = noteService.findAllBy(owner);

        assertThat(notes).hasSameElementsAs(notesFound);
        verify(noteRepository).findAllBy(owner);
    }

    @Test
    void shouldExecuteTheNoteDeletionProcessWithSuccess() {

        Note note = buildNote();
        String owner = note.getOwner();
        Long id = note.getId();

        doNothing()
                .when(noteRepository)
                .deleteBy(id, owner);

        noteService.deleteBy(id, owner);

        verify(noteRepository).deleteBy(id, owner);
    }
}
