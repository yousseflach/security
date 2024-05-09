package com.lachgar.security.service;

import com.lachgar.security.dto.AuthenticationRequestDTO;
import com.lachgar.security.dto.JwtResponseDTO;
import com.lachgar.security.dto.RegistrationRequestDTO;
import com.lachgar.security.entity.UserEntity;
import com.lachgar.security.repository.RoleRepository;
import com.lachgar.security.repository.TokenRepository;
import com.lachgar.security.repository.UserRepository;
import com.lachgar.security.security.JwtService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    //  private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegistrationRequestDTO request) throws MessagingException {
        var userRole = roleRepository.findByName("ADMIN")
                // todo - better exception handling
                .orElseThrow(() -> new IllegalStateException("Role USER was not initialized"));

        var user = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
        // sendValidationEmail(user);

    }

    public JwtResponseDTO authenticate(AuthenticationRequestDTO request) {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Map<String, Object> claims = new HashMap<>();
        var user = ((UserEntity)auth.getPrincipal());
        claims.put("fullname", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);

        return JwtResponseDTO.builder().token(jwtToken).build();
    }


}
