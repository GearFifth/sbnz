package com.ftn.sbnz.model.dtos.users.responses;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginResponse {
    private UUID id;
    private String email;
    private String token;
}
