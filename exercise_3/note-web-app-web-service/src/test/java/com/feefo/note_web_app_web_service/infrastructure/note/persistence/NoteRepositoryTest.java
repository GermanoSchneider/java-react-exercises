package com.feefo.note_web_app_web_service.infrastructure.note.persistence;

import com.feefo.note_web_app_web_service.ModelFixture;
import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.note.NoteRepository;
import com.feefo.note_web_app_web_service.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static com.feefo.note_web_app_web_service.ModelFixture.*;
import static com.feefo.note_web_app_web_service.infrastructure.note.NoteFixture.buildFrom;
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

        Note savedNote = noteRepository.save(buildNote());
        NoteEntity findNewNote = testEntityManager.find(NoteEntity.class, 1);

        assertThat(findNewNote)
                .usingRecursiveComparison()
                .isEqualTo(savedNote);
    }

    @Test
    @DirtiesContext
    void shouldFindAllNotesByUserNameWithSuccess() {

        Note note = ModelFixture.noteBuilder().id(1L).user(null).build();

        User user = userBuilder().id(null).notes(List.of()).build();


        NoteEntity savedNoteEntity = testEntityManager.persist(buildFrom(note));

        Collection<Note> findNewNote = noteRepository.findAllBy(note.getUser().getName());

        findNewNote.forEach(persistedNote ->
            assertThat(savedNoteEntity)
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(note)
        );
    }

    @Test
    @DirtiesContext
    void shouldUpdateANoteWithSuccess() {

        User user = userBuilder().id(null).build();
        Note note = ModelFixture.noteBuilder().id(null).user(user).build();

        NoteEntity noteEntity = testEntityManager.persist(buildFrom(note));

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

        User user = userBuilder().id(null).build();
        Note note = ModelFixture.noteBuilder().id(null).user(user).build();

        NoteEntity noteEntity = testEntityManager.persist(buildFrom(note));

        noteRepository.deleteBy(noteEntity.getId());

        NoteEntity findNoteEntity = testEntityManager.find(NoteEntity.class, noteEntity.getId());

        assertThat(findNoteEntity).isNull();
    }

}
