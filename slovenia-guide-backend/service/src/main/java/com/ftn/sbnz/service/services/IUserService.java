package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.dtos.users.requests.UpdateUserRequest;
import com.ftn.sbnz.model.dtos.users.responses.UserResponse;
import com.ftn.sbnz.model.models.User;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.UUID;

public interface IUserService {
    Collection<UserResponse> getAll();
    UserResponse get(UUID userId) throws ResponseStatusException;
    UserResponse update(UpdateUserRequest request) throws ResponseStatusException;
    UserResponse remove(UUID userId);
    UserResponse getByEmail(String email);
}
