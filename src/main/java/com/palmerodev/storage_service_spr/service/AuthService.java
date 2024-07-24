package com.palmerodev.storage_service_spr.service;


import com.palmerodev.storage_service_spr.model.request.AuthenticationRequest;
import com.palmerodev.storage_service_spr.model.response.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);

    AuthenticationResponse register(AuthenticationRequest authenticationRequest);

    AuthenticationResponse registerAdmin(AuthenticationRequest authenticationRequest);

}