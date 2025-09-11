package com.ftn.sbnz.model.dtos.users.requests;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
}
