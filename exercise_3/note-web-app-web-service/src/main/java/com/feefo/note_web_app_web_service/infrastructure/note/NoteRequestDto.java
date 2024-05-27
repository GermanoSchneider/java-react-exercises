package com.feefo.note_web_app_web_service.infrastructure.note;

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
}
