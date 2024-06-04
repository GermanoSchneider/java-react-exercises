package note.web.app.ws.application;

import note.web.app.ws.domain.note.Note;
import note.web.app.ws.domain.note.NoteRepository;
import java.util.Collection;
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
