package com.ftn.sbnz.model.dtos.users.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
