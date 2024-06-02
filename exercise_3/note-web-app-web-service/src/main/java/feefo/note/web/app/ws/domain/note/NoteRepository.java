package feefo.note.web.app.ws.domain.note;

import java.util.Collection;

public interface NoteRepository {

    Note save(Note note);

    Collection<Note> findAllBy(String owner);

    Note update(Long id, String text, String owner);

    void deleteBy(Long id, String owner);
}
