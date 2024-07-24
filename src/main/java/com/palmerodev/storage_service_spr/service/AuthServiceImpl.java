package com.palmerodev.storage_service_spr.service;

import com.palmerodev.storage_service_spr.config.JwtService;
import com.palmerodev.storage_service_spr.model.entity.UserEntity;
import com.palmerodev.storage_service_spr.model.enums.Role;
import com.palmerodev.storage_service_spr.model.request.AuthenticationRequest;
import com.palmerodev.storage_service_spr.model.response.AuthenticationResponse;
import com.palmerodev.storage_service_spr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        var FUNCTION_CONTEXT = "login";

        log.info("{} Starting...", FUNCTION_CONTEXT);
        var passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password());
        authenticationManager.authenticate(passwordAuthenticationToken);

        var user = userRepository.findByUsername(authenticationRequest.username());
        log.info("{} User found: {}", FUNCTION_CONTEXT, user);
        return user.map(value -> AuthenticationResponse.builder()
                                                       .token(jwtService.generateToken(value, generateExtraClaims(value)))
                                                       .username(value.getUsername())
                                                       .role(value.getRole().name())
                                                       .build()).orElseThrow(() -> new IllegalArgumentException("User not found"));

    }

    private Map<String, Object> generateExtraClaims(UserEntity user) {
        return new HashMap<>() {{
            put("role", user.getRole().name());
            put("name", user.getUsername());
        }};
    }

    @Override
    public AuthenticationResponse register(AuthenticationRequest authenticationRequest) {
        var user = getUserEntity("register", authenticationRequest, Role.USER);

        return AuthenticationResponse.builder()
                                     .token(jwtService.generateToken(user, generateExtraClaims(user)))
                                     .username(user.getUsername())
                                     .role(user.getRole().name())
                                     .build();
    }

    @Override
    public AuthenticationResponse registerAdmin(AuthenticationRequest authenticationRequest) {
        var user = getUserEntity("registerAdmin", authenticationRequest, Role.ADMIN);

        return AuthenticationResponse.builder()
                                     .token(jwtService.generateToken(user, generateExtraClaims(user)))
                                     .username(user.getUsername())
                                     .role(user.getRole().name())
                                     .build();
    }

    private UserEntity getUserEntity(String functionScope, AuthenticationRequest authenticationRequest, Role role) {
        var user = new UserEntity();
        user.setUsername(authenticationRequest.username());
        user.setPassword(passwordEncoder.encode(authenticationRequest.password()));
        user.setRole(role);

        log.info("{} Saving user...", functionScope);
        userRepository.save(user);
        log.info("{} User saved: {}", functionScope, user);
        return user;
    }

}