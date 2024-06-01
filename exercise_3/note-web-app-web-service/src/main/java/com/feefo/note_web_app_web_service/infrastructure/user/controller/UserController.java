package com.feefo.note_web_app_web_service.infrastructure.user.controller;

import com.feefo.note_web_app_web_service.application.UserApplicationService;
import com.feefo.note_web_app_web_service.domain.user.User;
import com.feefo.note_web_app_web_service.infrastructure.user.UserMapper;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.HttpStatus.*;

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
    ResponseEntity<?> create(@RequestBody UserRequestDto request) {

        try {

            User userRequest = mapper.fromRequest(request);

            User user = userApplicationService.register(userRequest);

            UserResponseDto userResponse = mapper.toResponse(user);

            return ResponseEntity
                    .status(CREATED)
                    .body(userResponse);

        } catch (ConstraintViolationException violationException) {

            return ResponseEntity.status(BAD_REQUEST)
                    .body(violationException.getMessage().split(", "));
        }
    }

    @PostMapping("/login")
    ResponseEntity<String> login(Principal principal) {

        String token = userApplicationService.authenticate(principal.getName());

        return ResponseEntity
                .status(OK)
                .body(token);
    }
}
