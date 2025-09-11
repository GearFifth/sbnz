package com.ftn.sbnz.service.controllers;
import com.ftn.sbnz.model.dtos.users.responses.UserResponse;
import com.ftn.sbnz.service.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("api/v1/users")
public class UserController {
    private final IUserService service;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Collection<UserResponse>> getUsers() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
