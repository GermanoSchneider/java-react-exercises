package com.feefo.note_web_app_web_service.infrastructure.user.controller;

import com.feefo.note_web_app_web_service.application.UserApplicationService;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.infrastructure.user.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/auth")
class UserController {

    private final UserApplicationService userApplicationService;

    private final UserMapper mapper;

    UserController(UserApplicationService userApplicationService, UserMapper mapper) {
        this.userApplicationService = userApplicationService;
        this.mapper = mapper;
    }

    @PostMapping("/user")
    ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto request) {

        User userRequest = mapper.fromRequest(request);

        User user = userApplicationService.register(userRequest);

        UserResponseDto userResponse = mapper.toResponse(user);

        return ResponseEntity
            .status(CREATED)
            .body(userResponse);
    }

    @PostMapping("/login")
    ResponseEntity<String> login(Principal principal) {

        String token = userApplicationService.authenticate(principal.getName());

        return ResponseEntity
                .status(OK)
                .body(token);
    }
}
