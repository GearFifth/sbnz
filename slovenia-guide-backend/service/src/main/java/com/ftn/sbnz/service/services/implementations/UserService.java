package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.dtos.users.requests.UpdateUserRequest;
import com.ftn.sbnz.model.dtos.users.responses.UserResponse;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.exceptions.UserNotFoundException;
import com.ftn.sbnz.service.repositories.IUserRepository;
import com.ftn.sbnz.service.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Primary
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public Collection<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse get(UUID userId) {
        return mapper.map(getById(userId), UserResponse.class);
    }

    @Override
    public UserResponse update(UpdateUserRequest request) {
        User user = getById(request.getId());
        return mapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public UserResponse remove(UUID userId) {
        User user = getById(userId);
        userRepository.delete(user);
        return mapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        return mapper.map(user, UserResponse.class);
    }

    private User getById(UUID userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }
}
