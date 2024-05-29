package com.feefo.note_web_app_web_service.infrastructure.note.controller;

import java.time.LocalDateTime;

public class NoteResponseDto {

    private Long id;

    private String text;

    private LocalDateTime creation;

    private LocalDateTime lastUpdate;

    public NoteResponseDto() {}

    public NoteResponseDto(
        Long id,
        String text,
        LocalDateTime creation,
        LocalDateTime lastUpdate
    ) {
        this.id = id;
        this.text = text;
        this.creation = creation;
        this.lastUpdate = lastUpdate;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
