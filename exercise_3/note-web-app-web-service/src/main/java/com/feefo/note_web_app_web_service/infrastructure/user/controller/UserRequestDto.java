package com.feefo.note_web_app_web_service.infrastructure.user.controller;

public class UserRequestDto {

    private String username;

    private String password;

    public UserRequestDto() {}

    public UserRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}