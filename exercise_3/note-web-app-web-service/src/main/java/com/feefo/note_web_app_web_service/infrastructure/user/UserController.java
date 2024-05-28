package com.feefo.note_web_app_web_service.infrastructure.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

    @GetMapping
    void login() {
        System.out.println("Login!");
    }
}
