package com.feefo.note_web_app_web_service.infrastructure.note.persistence;

import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "note")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    private String text;

    private LocalDateTime creation;

    private LocalDateTime lastUpdate;

    private String owner;

    NoteEntity() {}

    NoteEntity(NoteEntityBuilder noteEntityBuilder) {
        this.id = noteEntityBuilder.id;
        this.text = noteEntityBuilder.text;
        this.creation = noteEntityBuilder.creation;
        this.lastUpdate = noteEntityBuilder.lastUpdate;
        this.owner = noteEntityBuilder.owner;
    }

    public static class NoteEntityBuilder {

        private Long id;

        private String text;

        private LocalDateTime creation;

        private LocalDateTime lastUpdate;

        private String owner;

        public NoteEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public NoteEntityBuilder text(String text) {
            this.text = text;
            return this;
        }

        public NoteEntityBuilder creation(LocalDateTime creation) {
            this.creation = creation;
            return this;
        }

        public NoteEntityBuilder lastUpdate(LocalDateTime lastUpdate) {
            this.lastUpdate = lastUpdate;
            return this;
        }

        public NoteEntityBuilder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public NoteEntity build() {
            return new NoteEntity(this);
        }
    }

    public NoteEntityBuilder toBuilder() {

        return builder()
            .id(id)
            .text(text)
            .creation(creation)
            .lastUpdate(lastUpdate)
            .owner(owner);
    }

    public static NoteEntityBuilder builder() {
        return new NoteEntityBuilder();
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
