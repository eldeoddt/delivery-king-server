package com.king.deliveryking.user.controller;

import com.king.deliveryking.global.swagger.ApiErrorCodes;
import com.king.deliveryking.user.dto.request.LoginRequestDTO;
import com.king.deliveryking.user.dto.request.SignupRequestDTO;
import com.king.deliveryking.user.dto.response.LoginResponseDTO;
import com.king.deliveryking.user.dto.response.SignupResponseDTO;
import com.king.deliveryking.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.king.deliveryking.global.exception.ErrorCode.*;

@Tag(name = "Auth", description = "회원가입 / 로그인 API")
@SecurityRequirements
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "응답의 accessToken을 Authorize 버튼에 입력하면 인증이 필요한 API를 테스트할 수 있다.")
    @ApiErrorCodes({INVALID_INPUT, INVALID_AUTH})
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "회원가입", description = "role을 OWNER로 가입하려면 ownerToken이 필요하다.")
    @ApiErrorCodes({INVALID_INPUT, INVALID_OWNER_TOKEN, DUPLICATE_USERNAME, DUPLICATE_EMAIL})
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(request));
    }
}
