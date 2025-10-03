package com.ftn.sbnz.service.services.auth;

import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.exceptions.UserNotFoundException;
import com.ftn.sbnz.service.repositories.IUserRepository;
import com.ftn.sbnz.service.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        return new UserPrincipal(user);
    }
}