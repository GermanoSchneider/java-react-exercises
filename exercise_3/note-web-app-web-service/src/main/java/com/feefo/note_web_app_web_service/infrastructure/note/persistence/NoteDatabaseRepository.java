package com.feefo.note_web_app_web_service.infrastructure.note.persistence;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.note.NoteRepository;
import com.feefo.note_web_app_web_service.infrastructure.note.NoteMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static com.feefo.note_web_app_web_service.infrastructure.note.NoteMapper.from;
import static java.util.stream.Collectors.toList;

@Repository
class NoteDatabaseRepository implements NoteRepository {

    private final NoteJpaRepository noteJpaRepository;

    NoteDatabaseRepository(NoteJpaRepository noteJpaRepository) {
        this.noteJpaRepository = noteJpaRepository;
    }

    @Override
    public Note save(Note note) {

        NoteEntity noteEntity = from(note).build();

        NoteEntity savedNoteEntity = noteJpaRepository.save(noteEntity);

        return from(savedNoteEntity).build();
    }

    @Override
    public Collection<Note> findAllBy(String owner) {

        return noteJpaRepository
                .findByOwner(owner)
                .stream()
                .map(noteEntity -> from(noteEntity).build())
                .collect(toList());
    }

    @Override
    public Note update(Long id, String text, String owner) {

        NoteEntity noteEntity = noteJpaRepository.findByIdAndOwner(id, owner);

        Note updatedNote = NoteMapper.from(noteEntity)
                .text(text)
                .lastUpdate(LocalDateTime.now())
                .build();

        NoteEntity updatedNoteEntity = noteJpaRepository.save(from(updatedNote).build());

        return from(updatedNoteEntity).build();
    }

    @Override
    public void deleteBy(Long id, String owner) {

        NoteEntity noteEntity = noteJpaRepository.findByIdAndOwner(id, owner);

        noteJpaRepository.deleteById(noteEntity.getId());
    }
}
