package com.king.deliveryking.user.controller;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.global.exception.ErrorCode;
import com.king.deliveryking.global.security.JwtUtil;
import com.king.deliveryking.user.dto.request.LoginRequestDTO;
import com.king.deliveryking.user.dto.request.SignupRequestDTO;
import com.king.deliveryking.user.dto.response.LoginResponseDTO;
import com.king.deliveryking.user.dto.response.SignupResponseDTO;
import com.king.deliveryking.user.service.AuthService;
import com.king.deliveryking.user.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    public final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtUtil jwtUtil;

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {

        return ResponseEntity.ok(new LoginResponseDTO(

        ));
    }


    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody SignupRequestDTO request) {

        return ResponseEntity.ok(new SignupResponseDTO(

        ));
    }







    // todo: 삭제
    @GetMapping("/create-jwt")
    public String createJwt(HttpServletResponse res) {

        // Jwt 생성
        String token = jwtUtil.createToken("Robbie", UserRole.CUSTOMER);

        // Jwt 쿠키 저장
        jwtUtil.addJwtToCookie(token, res);

        return "createJwt : " + token;
    }

    @GetMapping("/get-jwt")
    public String getJwt(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {

        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);

        // 토큰 검증
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);

        // 사용자 username
        String username = info.getSubject();
        log.debug("username = " + username);

        // 사용자 권한
        String authority = (String) info.get(JwtUtil.AUTHORIZATION_KEY);
        log.debug("authority = " + authority);

        return "getJwt : " + username + ", " + authority;
    }
}