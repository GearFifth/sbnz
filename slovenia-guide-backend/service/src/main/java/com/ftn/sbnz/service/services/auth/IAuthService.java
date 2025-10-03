package com.ftn.sbnz.service.services.auth;
import com.ftn.sbnz.model.dtos.users.requests.LoginRequest;
import com.ftn.sbnz.model.dtos.users.requests.RegisterRequest;
import com.ftn.sbnz.model.dtos.users.responses.LoginResponse;
import com.ftn.sbnz.model.dtos.users.responses.UserResponse;
import com.ftn.sbnz.model.models.User;


public interface IAuthService {
    UserResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void logout();
}
