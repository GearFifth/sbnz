package com.ftn.sbnz.model.dtos.users.responses;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginResponse {
    private String token;
}
