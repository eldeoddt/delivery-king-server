package com.king.deliveryking.user.controller;

import com.king.deliveryking.user.dto.request.LoginRequestDTO;
import com.king.deliveryking.user.dto.request.SignupRequestDTO;
import com.king.deliveryking.user.dto.response.LoginResponseDTO;
import com.king.deliveryking.user.dto.response.SignupResponseDTO;
import com.king.deliveryking.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }
}
