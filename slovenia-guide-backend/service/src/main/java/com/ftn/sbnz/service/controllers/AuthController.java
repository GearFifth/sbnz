package com.ftn.sbnz.service.controllers;

import com.ftn.sbnz.model.dtos.users.requests.LoginRequest;
import com.ftn.sbnz.model.dtos.users.requests.RegisterRequest;
import com.ftn.sbnz.model.dtos.users.responses.LoginResponse;
import com.ftn.sbnz.model.dtos.users.responses.UserResponse;
import com.ftn.sbnz.service.services.auth.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("api/v1/auth")
public class AuthController {
    private final IAuthService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(service.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        service.logout();
        return ResponseEntity.noContent().build();
    }
}