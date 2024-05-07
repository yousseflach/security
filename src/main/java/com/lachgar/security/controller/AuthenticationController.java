package com.lachgar.security.controller;

import com.lachgar.security.dto.AuthenticationRequestDTO;
import com.lachgar.security.dto.JwtResponseDTO;
import com.lachgar.security.dto.RegistrationRequestDTO;
import com.lachgar.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequestDTO request) throws MessagingException {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponseDTO> authenticate(
            @RequestBody @Valid AuthenticationRequestDTO request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
