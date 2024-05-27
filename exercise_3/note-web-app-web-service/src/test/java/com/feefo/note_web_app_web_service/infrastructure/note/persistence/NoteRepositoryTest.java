package com.feefo.note_web_app_web_service.infrastructure.note.persistence;

import static com.feefo.note_web_app_web_service.ModelFixture.buildNote;
import static com.feefo.note_web_app_web_service.ModelFixture.noteBuilder;
import static com.feefo.note_web_app_web_service.infrastructure.note.NoteFixture.buildFrom;
import static org.assertj.core.api.Assertions.assertThat;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.note.NoteRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@Import({NoteDatabaseRepository.class})
class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DirtiesContext
    void shouldCreateANewNoteWithSuccess() {

        Note savedNote = noteRepository.save(buildNote());
        NoteEntity noteFound = testEntityManager.find(NoteEntity.class, 1);

        assertThat(noteFound)
                .usingRecursiveComparison()
                .isEqualTo(savedNote);
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

        LocalDateTime lastUpdate = updatedNote.getLastUpdate().withNano(0);

        assertThat(lastUpdate).isEqualTo(LocalDateTime.now().withNano(0));

        assertThat(updatedNote)
            .usingRecursiveComparison()
            .ignoringFields("lastUpdate", "text")
            .isEqualTo(savedNote);

        assertThat(updatedNote.getText()).isEqualTo(newText);
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
