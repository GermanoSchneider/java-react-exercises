package com.feefo.note_web_app_web_service.infrastructure.note.persistence;

import com.feefo.note_web_app_web_service.infrastructure.user.persistence.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "note")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    private String text;

    private LocalDateTime creation;

    private LocalDateTime lastUpdate;

    @ManyToOne(cascade = CascadeType.MERGE)
    private UserEntity user;

    NoteEntity() {}

    NoteEntity(NoteEntityBuilder noteEntityBuilder) {
        this.id = noteEntityBuilder.id;
        this.text = noteEntityBuilder.text;
        this.creation = noteEntityBuilder.creation;
        this.lastUpdate = noteEntityBuilder.lastUpdate;
        this.user = noteEntityBuilder.user;
    }

    public static class NoteEntityBuilder {

        private Long id;

        private String text;

        private LocalDateTime creation;

        private LocalDateTime lastUpdate;

        private UserEntity user;

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

        public NoteEntityBuilder userEntity(UserEntity userEntity) {
            this.user = userEntity;
            return this;
        }

        public NoteEntity build() {
            return new NoteEntity(this);
        }
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

    public UserEntity getUser() {
        return user;
    }
}
