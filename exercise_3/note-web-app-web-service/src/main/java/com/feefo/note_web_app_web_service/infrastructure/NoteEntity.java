package com.feefo.note_web_app_web_service.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "note")
class NoteEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    private String text;

    private LocalDateTime creation;

    private LocalDateTime lastUpdate;

    NoteEntity(Long id, String text, LocalDateTime creation, LocalDateTime lastUpdate) {
        this.id = id;
        this.text = text;
        this.creation = creation;
        this.lastUpdate = lastUpdate;
    }

    NoteEntity() {}

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
