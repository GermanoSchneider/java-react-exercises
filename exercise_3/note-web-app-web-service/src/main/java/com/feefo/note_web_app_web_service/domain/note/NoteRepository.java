package com.feefo.note_web_app_web_service.domain.note;

import java.util.Collection;

// here we are inverting the dependencies to decouple our futures implementations
public interface NoteRepository {

    Note save(Note note);

    Collection<Note> findAll();

    Note update(Long id, String text);

    void deleteBy(Long id);
}
