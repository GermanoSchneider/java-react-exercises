package note.web.app.ws.domain.note;

import java.util.Collection;

// Note repository domain service
public interface NoteRepository {

    Note save(Note note);

    Collection<Note> findAllBy(String owner);

    Note update(Long id, String text, String owner);

    void deleteBy(Long id, String owner);
}
