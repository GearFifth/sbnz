package com.ftn.sbnz.service.services.auth;

import com.ftn.sbnz.model.dtos.users.requests.LoginRequest;
import com.ftn.sbnz.model.dtos.users.requests.RegisterRequest;
import com.ftn.sbnz.model.dtos.users.responses.LoginResponse;
import com.ftn.sbnz.model.dtos.users.responses.UserResponse;
import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.model.enums.UserRole;
import com.ftn.sbnz.service.exceptions.InvalidCredentialsException;
import com.ftn.sbnz.service.repositories.IUserRepository;
import com.ftn.sbnz.service.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Primary
public class AuthService implements IAuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final ModelMapper mapper;

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new InvalidCredentialsException("Username is already taken");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);

        return mapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setId(userPrincipal.getId());
            loginResponse.setEmail(userPrincipal.getUsername());
            loginResponse.setToken(jwtUtils.generateAccessToken(userPrincipal));

            return loginResponse;

        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }

}