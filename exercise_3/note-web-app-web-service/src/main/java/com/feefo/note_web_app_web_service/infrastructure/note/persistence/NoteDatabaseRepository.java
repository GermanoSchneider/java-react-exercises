package com.feefo.note_web_app_web_service.infrastructure.note.persistence;

import static java.util.stream.Collectors.toList;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.note.NoteRepository;
import com.feefo.note_web_app_web_service.infrastructure.note.NoteMapper;
import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.stereotype.Repository;

@Repository
class NoteDatabaseRepository implements NoteRepository {

    private final NoteJpaRepository noteJpaRepository;

    private final NoteMapper mapper;

    NoteDatabaseRepository(NoteJpaRepository noteJpaRepository, NoteMapper mapper) {
        this.noteJpaRepository = noteJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Note save(Note note) {

        NoteEntity noteEntity = mapper.toEntity(note);

        NoteEntity savedNote = noteJpaRepository.save(noteEntity);

        return mapper.fromEntity(savedNote);
    }

    @Override
    public Collection<Note> findAllBy(String owner) {

        return noteJpaRepository
                .findByOwner(owner)
                .stream()
                .map(mapper::fromEntity)
                .collect(toList());
    }

    @Override
    public Note update(Long id, String text, String owner) {

        NoteEntity noteFound = noteJpaRepository.findByIdAndOwner(id, owner);

        noteFound = noteFound
            .toBuilder()
            .text(text)
            .lastUpdate(LocalDateTime.now())
            .build();

        NoteEntity updatedNote = noteJpaRepository.save(noteFound);

        return mapper.fromEntity(updatedNote);
    }

    @Override
    public void deleteBy(Long id, String owner) {

        NoteEntity noteEntity = noteJpaRepository.findByIdAndOwner(id, owner);

        noteJpaRepository.deleteById(noteEntity.getId());
    }
}
