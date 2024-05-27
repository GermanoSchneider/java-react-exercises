package com.feefo.note_web_app_web_service.application;

import com.feefo.note_web_app_web_service.domain.note.Note;
import com.feefo.note_web_app_web_service.domain.note.NoteRepository;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.domain.user.UserRepository;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;

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

    public Note update(Long id, String text, String owner) {

        return noteRepository.update(id, text, owner);
    }

    public void deleteBy(Long id, String owner) {

        noteRepository.deleteBy(id, owner);
    }
}
