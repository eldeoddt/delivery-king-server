package com.king.deliveryking.user.service;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.global.security.JwtUtil;
import com.king.deliveryking.user.dto.request.LoginRequestDTO;
import com.king.deliveryking.user.dto.request.SignupRequestDTO;
import com.king.deliveryking.user.entity.User;
import com.king.deliveryking.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 사장님 토큰
    private final String OWNER_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDTO requestDto) {
        String username = requestDto.username();
        String password = passwordEncoder.encode(requestDto.password());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.email();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRole role = UserRole.CUSTOMER;
        if (requestDto.owner()) {
            if (!OWNER_TOKEN.equals(requestDto.ownerToken())) {
                throw new IllegalArgumentException("사장님 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.OWNER;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }

    // 로그인
    public void login(LoginRequestDTO requestDTO, HttpServletResponse res) {
        String username = requestDTO.username();
        String password = requestDTO.password();
        String userRole = requestDTO.userRole();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if(passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // jwt 생성 및 쿠키에 저장 후 response 객체에 추가
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);
    }
}
