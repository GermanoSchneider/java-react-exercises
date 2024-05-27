package com.feefo.note_web_app_web_service.domain.note;

import java.util.Collection;
import java.util.Optional;

public interface NoteRepository {

    Note save(Note note);

    Collection<Note> findAllBy(String owner);

    Optional<Note> update(Long id, String text, String owner);

    void deleteBy(Long id, String owner);
}
