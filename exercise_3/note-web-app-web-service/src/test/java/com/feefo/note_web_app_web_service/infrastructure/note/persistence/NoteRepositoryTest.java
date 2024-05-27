package com.feefo.note_web_app_web_service.infrastructure.note.persistence;

import static com.feefo.note_web_app_web_service.ModelFixture.buildNote;
import static com.feefo.note_web_app_web_service.ModelFixture.userBuilder;
import static com.feefo.note_web_app_web_service.infrastructure.note.NoteFixture.buildFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.feefo.note_web_app_web_service.ModelFixture;
import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.note.NoteRepository;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.infrastructure.user.persistence.UserEntity;
import com.feefo.note_web_app_web_service.infrastructure.user.UserFixture;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
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
        NoteEntity findNewNote = testEntityManager.find(NoteEntity.class, 1);

        assertThat(findNewNote)
                .usingRecursiveComparison()
                .isEqualTo(savedNote);
    }

    @Test
    @DirtiesContext
    void shouldFindAllNotesByUserNameWithSuccess() {

        User user = ModelFixture.userBuilder().id(null).build();
        UserEntity savedUserEntity = testEntityManager.persist(UserFixture.buildFrom(user));

        Note note = ModelFixture.noteBuilder()
            .id(null)
            .user(UserFixture.buildFrom(savedUserEntity))
            .build();

        NoteEntity savedNoteEntity = testEntityManager.persist(buildFrom(note));

        Collection<Note> findNewNote = noteRepository.findAllBy(note.getUser().getName());

        findNewNote.forEach(persistedNote ->

            assertThat(savedNoteEntity)
                    .usingRecursiveComparison()
                    .isEqualTo(persistedNote)
        );
    }

    @Test
    @DirtiesContext
    void shouldUpdateANoteWithSuccess() {

        User user = ModelFixture.userBuilder().id(null).build();
        UserEntity savedUserEntity = testEntityManager.persist(UserFixture.buildFrom(user));

        Note note = ModelFixture
            .noteBuilder().id(null)
            .user(UserFixture.buildFrom(savedUserEntity))
            .build();

        NoteEntity noteEntity = testEntityManager.persist(buildFrom(note));

        String newText = "updated dummy text";

        Optional<Note> updatedOptionalNote = noteRepository.update(noteEntity.getId(), newText, user.getName());

        if (updatedOptionalNote.isPresent()) {

            Note updatedNote = updatedOptionalNote.get();

            LocalDateTime lastUpdate = updatedNote.getLastUpdate().withNano(0);

            assertThat(updatedNote)
                .hasFieldOrPropertyWithValue("id", noteEntity.getId())
                .hasFieldOrPropertyWithValue("text", newText)
                .hasFieldOrPropertyWithValue("creation", noteEntity.getCreation());

            assertThat(updatedNote.getUser())
                .usingRecursiveComparison()
                .isEqualTo(UserFixture.buildFrom(noteEntity.getUser()));

            assertThat(lastUpdate).isEqualTo(LocalDateTime.now().withNano(0));
        }

        assertThat(updatedOptionalNote).isNotEmpty();
    }

    @Test
    @DirtiesContext
    void shouldDoNothingWhenTryingToUpdateANonExistingNote() {

        String newText = "updated dummy text";

        Optional<Note> emptyNote = noteRepository.update(2L, newText, "john");

        assertThat(emptyNote).isEmpty();
    }

    @Test
    @DirtiesContext
    void shouldDeleteANoteWithSuccess() {

        User user = userBuilder().id(null).build();
        Note note = ModelFixture.noteBuilder().id(null).user(user).build();

        NoteEntity noteEntity = testEntityManager.persist(buildFrom(note));

        noteRepository.deleteBy(noteEntity.getId(), user.getName());

        NoteEntity findNoteEntity = testEntityManager.find(NoteEntity.class, noteEntity.getId());

        assertThat(findNoteEntity).isNull();
    }

}
