package note.web.app.ws.infrastructure.note;

import note.web.app.ws.domain.note.Note;
import note.web.app.ws.infrastructure.note.controller.NoteResponseDto;
import note.web.app.ws.infrastructure.note.persistence.NoteEntity;

public class NoteFixture {

    private NoteFixture() {

    }

    public static NoteEntity buildFrom(Note note) {

        return NoteEntity.builder()
                .id(note.getId())
                .text(note.getText())
                .creation(note.getCreation())
                .lastUpdate(note.getLastUpdate())
                .owner(note.getOwner())
                .build();
    }

    public static Note buildFrom(NoteEntity noteEntity) {

        return Note.builder()
                .id(noteEntity.getId())
                .text(noteEntity.getText())
                .creation(noteEntity.getCreation())
                .lastUpdate(noteEntity.getLastUpdate())
                .owner(noteEntity.getOwner())
                .build();
    }

    public static NoteResponseDto toResponse(Note note) {

        return new NoteResponseDto(
            note.getId(),
            note.getText(),
            note.getCreation(),
            note.getLastUpdate()
        );
    }
}
