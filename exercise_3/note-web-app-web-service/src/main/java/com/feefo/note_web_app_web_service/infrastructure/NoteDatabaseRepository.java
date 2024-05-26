package com.feefo.note_web_app_web_service.infrastructure;

import com.feefo.note_web_app_web_service.domain.Note;
import com.feefo.note_web_app_web_service.domain.NoteRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static com.feefo.note_web_app_web_service.infrastructure.NoteMapper.from;
import static java.util.stream.Collectors.toList;

@Repository
class NoteDatabaseRepository implements NoteRepository {

    private final NoteJpaRepository noteJpaRepository;

    NoteDatabaseRepository(NoteJpaRepository noteJpaRepository) {
        this.noteJpaRepository = noteJpaRepository;
    }

    @Override
    public Note save(Note note) {

        NoteEntity noteEntity = from(note);

        NoteEntity newedNoteEntity = noteJpaRepository.save(noteEntity);

        return from(newedNoteEntity).build();
    }

    @Override
    public Collection<Note> findAll() {

        return noteJpaRepository
                .findAll()
                .stream()
                .map(noteEntity -> from(noteEntity).build())
                .collect(toList());
    }

    @Override
    public Note update(Long id, String text) {

        Optional<NoteEntity> noteEntity = noteJpaRepository.findById(id);

        if (noteEntity.isEmpty()) throw new RuntimeException("The note with id " + id + " was not found");

        Note updatedNote = NoteMapper.from(noteEntity.get())
                .text(text)
                .lastUpdate(LocalDateTime.now())
                .build();

        NoteEntity updatedNoteEntity = noteJpaRepository.save(from(updatedNote));

        return from(updatedNoteEntity).build();
    }

    @Override
    public void deleteBy(Long id) {

        noteJpaRepository.deleteById(id);
    }
}
