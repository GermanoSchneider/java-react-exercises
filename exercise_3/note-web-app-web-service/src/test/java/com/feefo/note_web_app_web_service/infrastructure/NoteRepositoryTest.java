package com.feefo.note_web_app_web_service.infrastructure;

import com.feefo.note_web_app_web_service.domain.Note;
import com.feefo.note_web_app_web_service.domain.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Collection;

import static com.feefo.note_web_app_web_service.ModelFixture.buildNote;
import static com.feefo.note_web_app_web_service.infrastructure.EntityFixture.buildFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        Note newedNote = noteRepository.save(buildNote());
        NoteEntity findNewNote = testEntityManager.find(NoteEntity.class, 1);

        assertThat(findNewNote)
                .usingRecursiveComparison()
                .isEqualTo(newedNote);
    }

    @Test
    @DirtiesContext
    void shouldReadNoteWithSuccess() {

        NoteEntity newedNoteEntity = testEntityManager.persist(buildFrom(buildNote()));

        Collection<Note> findNewNote = noteRepository.findAll();

        findNewNote.forEach(note ->
            assertThat(newedNoteEntity)
                    .usingRecursiveComparison()
                    .isEqualTo(note)
        );
    }

    @Test
    @DirtiesContext
    void shouldUpdateANoteWithSuccess() {

        NoteEntity noteEntity = testEntityManager.persist(buildFrom(buildNote()));

        String newText = "updated dummy text";

        Note updatedNote = noteRepository.update(noteEntity.getId(), newText);

        LocalDateTime lastUpdate = updatedNote.getLastUpdate().withNano(0);

        assertThat(updatedNote)
                .hasFieldOrPropertyWithValue("id", noteEntity.getId())
                .hasFieldOrPropertyWithValue("text", newText)
                .hasFieldOrPropertyWithValue("creation", noteEntity.getCreation());

        assertThat(lastUpdate).isEqualTo(LocalDateTime.now().withNano(0));
    }

    @Test
    @DirtiesContext
    void shouldFailWhenTryingToUpdateANonExistingNote() {

        String expectedErrorMessage = "The note with id 1 was not found";

        Exception exception = assertThrows(
                Exception.class,
                () -> noteRepository.update(1L, "updated dummy text")
        );

        assertThat(exception.getMessage()).isEqualTo(expectedErrorMessage);
    }

    @Test
    @DirtiesContext
    void shouldDeleteANoteWithSuccess() {

        NoteEntity noteEntity = testEntityManager.persist(buildFrom(buildNote()));

        noteRepository.deleteBy(noteEntity.getId());

        NoteEntity findNoteEntity = testEntityManager.find(NoteEntity.class, noteEntity.getId());

        assertThat(findNoteEntity).isNull();
    }

}
