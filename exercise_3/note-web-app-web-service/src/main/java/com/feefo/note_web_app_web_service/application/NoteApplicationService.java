package com.feefo.note_web_app_web_service.application;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.note.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class NoteApplicationService {

    private final NoteRepository noteRepository;

    public NoteApplicationService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note create(Note note) {

        return noteRepository.save(note);
    }

    public Collection<Note> findAllBy(String owner) {

        return noteRepository.findAllBy(owner);
    }

    public Note update(Long id, String text) {

        return noteRepository.update(id, text);
    }

    public void deleteBy(Long id) {

        noteRepository.deleteBy(id);
    }
}
