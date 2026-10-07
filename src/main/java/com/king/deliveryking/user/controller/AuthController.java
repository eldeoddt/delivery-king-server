package com.king.deliveryking.user.controller;

import com.king.deliveryking.global.exception.BusinessException;
import com.king.deliveryking.global.exception.ErrorCode;
import com.king.deliveryking.user.dto.request.LoginRequestDTO;
import com.king.deliveryking.user.dto.response.LoginResponseDTO;
import com.king.deliveryking.user.entity.User;
import com.king.deliveryking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!user.getPassword().equals(request.password())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }
        return ResponseEntity.ok(new LoginResponseDTO(user.getUsername(), "dummy-token"));
    }
}