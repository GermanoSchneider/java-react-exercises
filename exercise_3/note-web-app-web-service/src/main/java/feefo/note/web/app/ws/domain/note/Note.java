package feefo.note.web.app.ws.domain.note;

import feefo.note.web.app.ws.domain.ConstraintValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

// The Note model represents a note that the user creates
public class Note extends ConstraintValidator {

    private final Long id;

    @NotNull(message = "should not be null")
    private final String text;

    @NotNull(message = "should not be null")
    private final LocalDateTime creation;

    @NotNull(message = "should not be null")
    private final LocalDateTime lastUpdate;

    @NotBlank(message = "should not be blank")
    private final String owner;

    private Note(NoteBuilder noteBuilder) {
        this.id = noteBuilder.id;
        this.text = noteBuilder.text;
        this.creation = noteBuilder.creation;
        this.lastUpdate = noteBuilder.lastUpdate;
        this.owner = noteBuilder.owner;
        validate(this);
    }

    public static class NoteBuilder {

        private Long id;

        private String text;

        private LocalDateTime creation;

        private LocalDateTime lastUpdate;

        private String owner;

        public NoteBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public NoteBuilder text(String text) {
            this.text = text;
            return this;
        }

        public NoteBuilder creation(LocalDateTime creation) {
            this.creation = creation;
            return this;
        }

        public NoteBuilder lastUpdate(LocalDateTime lastUpdate) {
            this.lastUpdate = lastUpdate;
            return this;
        }

        public NoteBuilder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public Note build() {
            return new Note(this);
        }
    }

    public static NoteBuilder builder() {
        return new NoteBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreation() {
        return creation;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getOwner() {
        return owner;
    }
}
