package com.feefo.note_web_app_web_service.infrastructure.note.controller;

public class NoteRequestDto {

    private String text;

    public NoteRequestDto(String text) {
        this.text = text;
    }

    public NoteRequestDto() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoteRequestDto that)) {
            return false;
        }

        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }
}
