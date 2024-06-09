package note.web.app.ws.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.List;
import note.web.app.ws.ModelFixture;
import note.web.app.ws.domain.note.Note;
import note.web.app.ws.domain.note.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class NoteApplicationServiceTest {

    @InjectMocks
    private NoteApplicationService noteService;

    @Mock
    private NoteRepository noteRepository;

    @Test
    void shouldExecuteTheNoteCreationProcessWithSuccess() {

        Note note = ModelFixture.buildNote();

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
        Note note = ModelFixture.noteBuilder().text(newText).build();
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

        Note note = ModelFixture.buildNote();
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

        Note note = ModelFixture.buildNote();
        String owner = note.getOwner();
        Long id = note.getId();

        doNothing()
                .when(noteRepository)
                .deleteBy(id, owner);

        noteService.deleteBy(id, owner);

        verify(noteRepository).deleteBy(id, owner);
    }
}
