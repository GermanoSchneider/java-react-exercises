package note.web.app.ws.infrastructure.note;

import static note.web.app.ws.domain.note.Note.builder;

import java.time.LocalDateTime;
import note.web.app.ws.domain.note.Note;
import note.web.app.ws.infrastructure.note.controller.NoteRequestDto;
import note.web.app.ws.infrastructure.note.controller.NoteResponseDto;
import note.web.app.ws.infrastructure.note.persistence.NoteEntity;
import org.springframework.stereotype.Component;

// Note mapper is responsible to convert note objects from the anti-corruption layer
@Component
public class NoteMapper {

    public Note fromEntity(NoteEntity noteEntity) {

        return builder()
            .id(noteEntity.getId())
            .text(noteEntity.getText())
            .creation(noteEntity.getCreation())
            .lastUpdate(noteEntity.getLastUpdate())
            .owner(noteEntity.getOwner())
            .build();
    }

    public NoteEntity toEntity(Note note) {

        return NoteEntity.builder()
                .id(note.getId())
                .text(note.getText())
                .creation(note.getCreation())
                .lastUpdate(note.getLastUpdate())
                .owner(note.getOwner())
                .build();
    }

    public NoteResponseDto toResponse(Note note) {

        return new NoteResponseDto(
                note.getId(),
                note.getText(),
                note.getCreation(),
                note.getLastUpdate()
        );
    }

    public Note from(NoteRequestDto dto, String owner) {

        return builder()
                .text(dto.getText())
                .creation(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .owner(owner)
                .build();
    }
}
