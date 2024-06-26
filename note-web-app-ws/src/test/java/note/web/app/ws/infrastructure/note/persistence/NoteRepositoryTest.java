package note.web.app.ws.infrastructure.note.persistence;

import static note.web.app.ws.ModelFixture.buildNote;
import static note.web.app.ws.ModelFixture.noteBuilder;
import static note.web.app.ws.infrastructure.note.NoteFixture.buildFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Collection;
import note.web.app.ws.domain.note.Note;
import note.web.app.ws.domain.note.NoteRepository;
import note.web.app.ws.infrastructure.note.NoteMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@Import({NoteDatabaseRepository.class, NoteMapper.class})
class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @SpyBean
    private NoteMapper noteMapper;

    @Test
    @DirtiesContext
    void shouldCreateANewNoteWithSuccess() {

        Note note = buildNote();

        Note savedNote = noteRepository.save(note);
        NoteEntity noteFound = testEntityManager.find(NoteEntity.class, 1);

        assertThat(noteFound)
                .usingRecursiveComparison()
                .isEqualTo(savedNote);

        verify(noteMapper).toEntity(note);
        verify(noteMapper).fromEntity(noteFound);
    }

    @Test
    @DirtiesContext
    void shouldFindAllNotesByOwnerWithSuccess() {

        Note note = noteBuilder()
            .id(null)
            .build();

        NoteEntity savedNoteEntity = testEntityManager.persist(buildFrom(note));

        Collection<Note> findNewNote = noteRepository.findAllBy(note.getOwner());

        findNewNote.forEach(persistedNote ->

            assertThat(savedNoteEntity)
                    .usingRecursiveComparison()
                    .isEqualTo(persistedNote)
        );

        verify(noteMapper).fromEntity(savedNoteEntity);
    }

    @Test
    @DirtiesContext
    void shouldUpdateANoteWithSuccess() {

        Note note = noteBuilder()
            .id(null)
            .build();

        NoteEntity savedNote = testEntityManager.persist(buildFrom(note));

        String newText = "updated dummy text";

        Note updatedNote = noteRepository.update(savedNote.getId(), newText, savedNote.getOwner());

        LocalDateTime lastUpdate = updatedNote.getLastUpdate().withNano(0).withSecond(0);

        assertThat(lastUpdate)
                .isEqualTo(LocalDateTime.now().withNano(0).withSecond(0));

        assertThat(updatedNote)
            .usingRecursiveComparison()
            .ignoringFields("lastUpdate", "text")
            .isEqualTo(savedNote);

        assertThat(updatedNote.getText()).isEqualTo(newText);

        verify(noteMapper).fromEntity(savedNote);
    }

    @Test
    @DirtiesContext
    void shouldDeleteANoteWithSuccess() {

        Note note = noteBuilder()
            .id(null)
            .build();

        NoteEntity savedNote = testEntityManager.persist(buildFrom(note));

        Long id = savedNote.getId();

        noteRepository.deleteBy(id, savedNote.getOwner());

        NoteEntity noteEntity = testEntityManager.find(NoteEntity.class, id);

        assertThat(noteEntity).isNull();
    }
}
