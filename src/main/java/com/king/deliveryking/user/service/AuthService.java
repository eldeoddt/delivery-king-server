package com.king.deliveryking.user.service;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.global.exception.BusinessException;
import com.king.deliveryking.global.exception.ErrorCode;
import com.king.deliveryking.global.security.JwtUtil;
import com.king.deliveryking.user.dto.request.LoginRequestDTO;
import com.king.deliveryking.user.dto.request.SignupRequestDTO;
import com.king.deliveryking.user.dto.response.LoginResponseDTO;
import com.king.deliveryking.user.dto.response.SignupResponseDTO;
import com.king.deliveryking.user.entity.User;
import com.king.deliveryking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 사장님 토큰 (application.yml)
    @Value("${owner.token}")
    private String ownerToken;

    @Transactional
    public SignupResponseDTO signup(SignupRequestDTO requestDto) {
        String username = requestDto.username();
        String password = passwordEncoder.encode(requestDto.password());

        // 회원 중복 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_USERNAME);
        }

        // email 중복확인
        String email = requestDto.email();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 사용자 ROLE 확인
        UserRole role = UserRole.CUSTOMER;
        if (requestDto.owner()) {
            if (!ownerToken.equals(requestDto.ownerToken())) {
                throw new BusinessException(ErrorCode.INVALID_OWNER_TOKEN);
            }
            role = UserRole.OWNER;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);

        return new SignupResponseDTO(user.getUsername(), user.getEmail(), role == UserRole.OWNER);
    }

    // 로그인
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        String username = requestDTO.username();
        String password = requestDTO.password();

        // 사용자 확인 (사용자 존재 여부 노출 방지를 위해 비밀번호 오류와 같은 에러 사용)
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BusinessException(ErrorCode.INVALID_AUTH)
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_AUTH);
        }

        // jwt 생성
        String token = jwtUtil.createToken(user.getId(), user.getRole());

        return new LoginResponseDTO(user.getUsername(), token);
    }
}
