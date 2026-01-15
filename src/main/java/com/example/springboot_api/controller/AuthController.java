package com.example.springboot_api.controller;

import com.example.springboot_api.Model.RefreshToken;
import com.example.springboot_api.Model.Role;
import com.example.springboot_api.Model.User;
import com.example.springboot_api.dto.AuthRequest;
import com.example.springboot_api.dto.AuthResponse;
import com.example.springboot_api.dto.RefreshTokenRequest;
import com.example.springboot_api.dto.RegisterRequest;
import com.example.springboot_api.exception.BadRequestException;
import com.example.springboot_api.repository.RoleRepository;
import com.example.springboot_api.repository.UserRepository;
import com.example.springboot_api.service.JwtService;
import com.example.springboot_api.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
   private final RefreshTokenService refreshTokenService;

    // ================= REGISTER =================
    @PostMapping("/register")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email already exists"));
        }

        Role role = roleRepository.findByName(
                request.getRole() == null ? "USER" : request.getRole()
        ).orElseThrow(() ->
                new BadRequestException("Role does not exist")
        );

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }

    // ================= LOGIN =================

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateAccessToken(user.getEmail());
        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(
                new AuthResponse(
                        accessToken,
                        refreshToken.getToken(),
                        "Bearer"
                )
        );
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @Valid @RequestBody RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(
                        request.refreshToken()
                );

        String newAccessToken =
                jwtService.generateAccessToken(
                        refreshToken.getUser().getEmail()
                );

        return ResponseEntity.ok(
                new AuthResponse(
                        newAccessToken,
                        refreshToken.getToken(),
                        "Bearer"
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @Valid @RequestBody RefreshTokenRequest request) {

        RefreshToken token =
                refreshTokenService.verifyRefreshToken(
                        request.refreshToken()
                );

        refreshTokenService.revokeToken(token);

        return ResponseEntity.ok(
                Map.of("message", "Logged out successfully")
        );
    }



}
