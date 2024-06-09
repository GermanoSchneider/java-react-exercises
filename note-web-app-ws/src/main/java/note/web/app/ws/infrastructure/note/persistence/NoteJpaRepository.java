package note.web.app.ws.infrastructure.note.persistence;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

interface NoteJpaRepository extends JpaRepository<NoteEntity, Long> {

    Collection<NoteEntity> findByOwner(String owner);

    NoteEntity findByIdAndOwner(Long id, String owner);
}
