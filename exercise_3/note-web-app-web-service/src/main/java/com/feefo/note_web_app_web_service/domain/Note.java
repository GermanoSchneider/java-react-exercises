package com.feefo.note_web_app_web_service.domain;

import java.time.LocalDateTime;

public class Note extends Model {

    private final Long id;

    private final String text;

    private final LocalDateTime creation;

    private final LocalDateTime lastUpdate;

    private Note(NoteBuilder noteBuilder) {
        this.id = validate("id", noteBuilder.id);
        this.text = validate("text", noteBuilder.text);
        this.creation = validate("creation", noteBuilder.creation);
        this.lastUpdate = validate("lastUpdate", noteBuilder.lastUpdate);
    }

    public static class NoteBuilder {

        private Long id;

        private String text;

        private LocalDateTime creation;

        private LocalDateTime lastUpdate;

        public NoteBuilder() {}

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
}
